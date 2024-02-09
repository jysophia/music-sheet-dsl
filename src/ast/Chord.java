package ast;

import java.util.ArrayList;

public class Chord extends Node{

    private ArrayList<Note> notes;

    public Chord(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
