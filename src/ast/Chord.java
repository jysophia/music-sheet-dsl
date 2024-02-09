package ast;

import java.util.ArrayList;

public class Chord extends Node{

    private ArrayList<Note> notes;

    public Chord(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public <C,T> T accept(C context, MusicSheetVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
