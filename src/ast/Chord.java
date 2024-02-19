package ast;

import java.util.ArrayList;

public class Chord extends Node{

    private final ArrayList<String> notes;

    public Chord(ArrayList<String> notes) {
        this.notes = notes;
    }

    public ArrayList<String> getNotes() {
        return this.notes;
    }

    @Override
    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
