package ast;

import java.util.ArrayList;

public class Chord extends Node{

    private final ArrayList<Note> notes;

    public Chord(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    @Override
    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
