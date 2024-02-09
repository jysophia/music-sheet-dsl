package ast;

import java.util.ArrayList;

public class Sequence extends Node{

    private ArrayList<Object> chordsAndNotes;

    public Sequence(ArrayList<Object> chordsAndNotes) {
        this.chordsAndNotes = chordsAndNotes;
    }

    @Override
    public <C,T> T accept(C context, MusicSheetVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
