package ast.evaluator;

import ast.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator implements MusicSheetVisitor<PrintWriter, Void> {

    // Direct mapping of String to Node since we dont support variable pointing
    public Map<String, Node> symbolTable = new HashMap<>();

    public Evaluator() {

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

            if (p.getProperty() == "SET_KEY") {
                note.setKey(prop);
            } else if (p.getProperty() == "SET_BEAT") {
                note.setBeat(prop);
            } else if (p.getProperty() == "SET_PITCH") {
                note.setPitch(prop);
            } else if (p.getProperty() == "SET_OCTAVE") {
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
            String key = note.getKey();
        } else if (mut instanceof MutateBeat) {
            String beat = note.getBeat();
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
        MusicSheet m = p.getMusicSheet();
        m.accept(this, writer);

        return null;
    }

    @Override
    public Void visit(Repeat r, PrintWriter printWriter) {
        System.out.println("Repeat");
        return null;
    }

    public Void visit(MusicSheet m, PrintWriter writer) {
        writer.println("{\n    \\clef treble\n");

        for (Statement st : m.getStatements()) {
            st.accept(this, writer);
        }

        writer.println("\n}");

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

            beat = 4 / beat;
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

        String mod = "";
        if (pitch != null) {
            if (pitch.equals("b")) {
                mod = "es";
            } else if (pitch.equals("#")) {
                mod = "is";
            }

        }

        writer.print(key + mod + "'" + " ");
        return null;
    }
}
