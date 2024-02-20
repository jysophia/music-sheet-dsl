package ast;

import java.util.List;

public class Sequence extends Operation {

    private final List<Name> chordsAndNotes;

    public Sequence(List<Name> chordsAndNotes) {
        this.chordsAndNotes = chordsAndNotes;
    }

    public List<Name> getChordAndNoteSequence() {
        return this.chordsAndNotes;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
