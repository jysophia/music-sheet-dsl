package ast;

import java.util.List;

public class Chord extends Operation {

    private final List<Name> notes;

    public Chord(List<Name> notes) {
        this.notes = notes;
    }

    public List<Name> getNotes() {
        return this.notes;
    }

    @Override
    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
