package ast.evaluator;

import ast.*;

import java.io.PrintWriter;

public class Evaluator implements MusicSheetVisitor<PrintWriter, Void> {

    public Evaluator() {

    }

    public Void visit(MusicSheet m, PrintWriter writer) {
        writer.println("{\n    \\clef treble\n");

        for (Sequence seq : m.getSequences()) {
            seq.accept(this, writer);
        }

        writer.println("\n}");

        return null;
    }

    public Void visit(Sequence s, PrintWriter writer) {

        writer.print("    ");

        // This should only be Chord or Note
        for (Node n : s.getChordAndNoteSequence()) {
            n.accept(this, writer);

            if (n instanceof Note) {
                writer.print(((Note) n).getBeat());
            } else if (n instanceof Chord) {
                writer.print(((Chord) n).getBeat());
            }
        }

        return null;
    }

    public Void visit(Chord c, PrintWriter writer) {
        writer.print("<");

        for (Note n : c.getNotes()) {
            n.accept(this, writer);
        }

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
