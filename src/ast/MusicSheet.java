package ast;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

public class MusicSheet extends Node{

    private ArrayList<Sequence> sequences;

    public MusicSheet(ArrayList<Sequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public <C,T> T accept(C context, MusicSheetVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
