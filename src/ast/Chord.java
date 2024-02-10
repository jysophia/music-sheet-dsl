package ast;

import java.util.ArrayList;

public class Chord extends Node{

    private final ArrayList<Note> notes;
    private final String beat;

    public Chord(ArrayList<Note> notes, String beat) {
        this.notes = notes;
        this.beat = beat;
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    public String getBeat() {
        return this.beat;
    }

    @Override
    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
