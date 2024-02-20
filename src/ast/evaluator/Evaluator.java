package ast.evaluator;

import ast.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator implements MusicSheetVisitor<PrintWriter, Void> {

    // Direct mapping of String to Note since we dont support variable pointing
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
            if (symbolTable.get(n) != null) {
                note = (Note) symbolTable.get(n);
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
        }

        return null;
    }

    public Void visit(Display d, PrintWriter printWriter) {
        System.out.println("Display");
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
        if (type == "Add") {

        } else if (type == "Sub") {

        } else {
            System.out.println("Unrecognized type.");
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
//        for (Node n : s.getChordAndNoteSequence()) {
//            n.accept(this, writer);
//
//            if (n instanceof Note nt) {
//                writer.print(nt.getBeat());
//            } else if (n instanceof Chord c) {
//                String firstNote = c.getNotes().get(0);
//                String beat = noteSymbolTable.get(firstNote).getBeat();
//                writer.print(beat);
//            }
//        }

        return null;
    }

    public Void visit(Chord c, PrintWriter writer) {
        writer.print("<");

//        for (String n : c.getNotes()) {
//            Note note = noteSymbolTable.get(n);
//            note.accept(this, writer);
//        }

        writer.print(">");
        return null;
    }

    public Void visit(Note n, PrintWriter writer) {
        String pitch = n.getPitch();
        String mod = "";
        if (pitch.equals("flat")) {
            mod = "es";
        } else if (pitch.equals("sharp")) {
            mod = "is";
        }
        writer.print(n.getKey() + mod + "'" + " ");
        return null;
    }
}
