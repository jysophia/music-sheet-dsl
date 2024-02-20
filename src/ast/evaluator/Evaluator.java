/*
package ast.evaluator;

import ast.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator implements MusicSheetVisitor<PrintWriter, Void> {

    // Direct mapping of String to Note since we dont support variable pointing
    public Map<String, Note> noteSymbolTable = new HashMap<>();

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

            if (n instanceof Note nt) {
                writer.print(nt.getBeat());
            } else if (n instanceof Chord c) {
                String firstNote = c.getNotes().get(0);
                String beat = noteSymbolTable.get(firstNote).getBeat();
                writer.print(beat);
            }
        }

        return null;
    }

    public Void visit(Chord c, PrintWriter writer) {
        writer.print("<");

        for (String n : c.getNotes()) {
            Note note = noteSymbolTable.get(n);
            note.accept(this, writer);
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
*/
