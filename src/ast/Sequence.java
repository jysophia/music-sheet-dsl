package ast;

import java.util.ArrayList;

public class Sequence extends Operation {

    private final ArrayList<Node> chordsAndNotes;

    public Sequence(ArrayList<Node> chordsAndNotes) {
        this.chordsAndNotes = chordsAndNotes;
    }

    public ArrayList<Node> getChordAndNoteSequence() {
        return this.chordsAndNotes;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
