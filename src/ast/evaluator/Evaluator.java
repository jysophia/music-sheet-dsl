package ast.evaluator;

import ast.*;
import ast.Set;

import java.io.PrintWriter;
import java.util.*;

public class Evaluator implements MusicSheetVisitor<PrintWriter, Void> {

    // Direct mapping of String to Node since we dont support variable pointing
    public Map<String, Node> symbolTable = new HashMap<>();

    private Map<Integer, String> indexToNoteMap = null;

    private Map<String, Integer> noteToIndexMap = null;

    public Evaluator() {
        this.indexToNoteMap = new HashMap<>() {{
            put(0, "c");
            put(1, "d");
            put(2, "e");
            put(3, "f");
            put(4, "g");
            put(5, "a");
            put(6, "b");
        }};

        this.noteToIndexMap = new HashMap<>() {{
            put("c", 0);
            put("d", 1);
            put("e", 2);
            put("f", 3);
            put("g", 4);
            put("a", 5);
            put("b", 6);
        }};
    }

    public Void visit(Declare d, PrintWriter writer) {
        Name name = d.getName();

        symbolTable.put(name.getName(), null);

        return null;
    }

    public Void visit(Set s, PrintWriter printWriter) {
        Property p = s.getProperty();
        Name n = s.getName();
        Operation o = s.getOperation();

        if (p == null) {
            symbolTable.put(n.getName(), o);
        } else {
            // Should only fall into this for Note properties being set
            Note note;
            if (symbolTable.get(n.getName()) != null) {
                note = (Note) symbolTable.get(n.getName());
            } else {
                note = new Note(null, null, null, null);
            }
            NoteProperty np = (NoteProperty) o;
            String prop = np.getProperty();

            if (p.getProperty().equals("SET_KEY")) {
                note.setKey(prop);
            } else if (p.getProperty().equals("SET_BEAT")) {
                note.setBeat(prop);
            } else if (p.getProperty().equals("SET_PITCH")) {
                note.setPitch(prop);
            } else if (p.getProperty().equals("SET_OCTAVE")) {
                note.setOctave(prop);
            }

            symbolTable.put(n.getName(), note);
        }

        return null;
    }

    public Void visit(Display d, PrintWriter printWriter) {
        Name sequenceName = d.getName();
        Sequence displaySequence = (Sequence) symbolTable.get(sequenceName.getName());
        displaySequence.accept(this, printWriter);
        return null;
    }

    public Void visit(Name n, PrintWriter printWriter) {
        System.out.println("Name");
        return null;
    }

    public Void visit(NoteProperty n, PrintWriter printWriter) {
        System.out.println("NoteProperty");
        return null;
    }

    public Void visit(MutateStmt m, PrintWriter printWriter) {
        Name varName = m.getVarname();
        Mutation mut = m.getMutation();
        String type = m.getType();

        Note note = (Note) symbolTable.get(varName.getName());

        // Only Notes can be mutated
        if (mut instanceof MutateKey){
            double mutKey = Double.parseDouble(((MutateKey) mut).getNewkey());
            String noteKey = note.getKey().substring(0, 1);
            double noteIndex = (double) this.noteToIndexMap.get(noteKey.toLowerCase());

            String pitch = note.getPitch();

            if (pitch != null) {
                if (pitch.equals("b")) {
                    noteIndex -= 0.5;
                } else if (pitch.equals("#")) {
                    noteIndex += 0.5;
                }
            }

            if (type.equals("Add")) {

                noteIndex = (noteIndex + mutKey);

                if (noteIndex % 1.0 == 0.5) {
                    note.setPitch("#");
                    noteIndex -= 0.5;
                }

            } else if (type.equals("Sub")) {

                noteIndex = (noteIndex - mutKey);

                if (noteIndex % 1.0 == 0.5) {
                    note.setPitch("b");
                    noteIndex += 0.5;
                }

            } else {
                System.out.println("Unsupported modification");
            }

            while (noteIndex < 0 || noteIndex > 6) {
                int octave = Integer.parseInt(note.getOctave().substring(1,2));
                if (noteIndex > 6) {
                    note.setOctave("_" + Integer.toString(octave + 1));
                    noteIndex -= 7;
                } else {
                    note.setOctave("_" + Integer.toString(octave - 1));
                    noteIndex += 7;
                }
            }

            noteIndex = noteIndex % 7.0;
            note.setKey(this.indexToNoteMap.get((int) noteIndex));

        } else if (mut instanceof MutateBeat) {
            double mutBeat = ((MutateBeat) mut).getNewbeat();
            double noteBeat = Double.parseDouble(note.getBeat());

            if (type.equals("Add")) {
                noteBeat = noteBeat + mutBeat;
            } else if (type.equals("Sub")) {
                noteBeat = noteBeat - mutBeat;
            } else {
                System.out.println("Unsupported modification");
            }

            note.setBeat(Double.toString(noteBeat));
        }

        return null;
    }

    public Void visit(MutateBeat mb, PrintWriter printWriter) {
        System.out.println("MutateBeat");
        return null;
    }

    public Void visit(MutateKey mk, PrintWriter printWriter) {
        System.out.println("MutateKey");
        return null;
    }

    public Void visit(Property p, PrintWriter printWriter) {
        System.out.println("Property");
        return null;
    }

    public Void visit(Program p, PrintWriter writer) {
        writer.println("\\version \"2.24.3\"\n");
        writer.println("{\n \\clef treble\n");

        MusicSheet m = p.getMusicSheet();
        m.accept(this, writer);

        writer.println("\n}");

        return null;
    }

    @Override
    public Void visit(Repeat r, PrintWriter printWriter) {

        for (MusicSheet m: r.getMusicSheets()) {
            m.accept(this, printWriter);
        }
        return null;
    }

    public Void visit(MusicSheet m, PrintWriter writer) {

        for (Statement st : m.getStatements()) {
            st.accept(this, writer);
        }

        return null;
    }

    public Void visit(Sequence s, PrintWriter writer) {

        writer.print("    ");

        // This should only be Chord or Note
        for (Name name : s.getChordAndNoteSequence()) {
            Node n = symbolTable.get(name.getName());
            n.accept(this, writer);

            double beat = 0;

            if (n instanceof Note nt) {
                beat = Double.parseDouble(nt.getBeat());
            } else if (n instanceof Chord c) {
                String firstNoteName = c.getNotes().get(0).getName();
                Note firstNote = (Note) symbolTable.get(firstNoteName);
                beat = Double.parseDouble(firstNote.getBeat());
            }

            beat = 1 / beat;
            writer.print((int) beat);
            writer.print(" ");
        }

        return null;
    }

    public Void visit(Chord c, PrintWriter writer) {
        writer.print("<");

        for (Name noteName : c.getNotes()) {
            String n = noteName.getName();
            Note note = (Note) symbolTable.get(n);
            note.accept(this, writer);
        }

        writer.print(">");
        return null;
    }

    public Void visit(Note n, PrintWriter writer) {
        String pitch = n.getPitch();
        String key = n.getKey().toLowerCase();
        int octave = Integer.parseInt(n.getOctave().substring(1,2));

        String mod = "";
        if (pitch != null) {
            if (pitch.equals("b")) {
                mod = "es";
            } else if (pitch.equals("#")) {
                mod = "is";
            }

        }

        String octaveMod = "";
        while (octave != 0) {
            if (octave < 0) {
                octaveMod = octaveMod.concat(",");
                octave++;
            } else {
                octaveMod = octaveMod.concat("'");
                octave--;
            }
        }

        writer.print(key + mod + octaveMod + " ");
        return null;
    }
}
