package ast.evaluator;

import ast.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Evaluator implements MusicSheetVisitor<PrintWriter, Void> {

    public Evaluator() {

    }

    public Void visit(MusicSheet m, PrintWriter writer) {
//        writer.println("{\n    \\clef bass\n    \\version \"2.25.11\"\n    ");
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
        writer.print(n.getKey() + n.getPitch() + "' ");
        return null;
    }
}
