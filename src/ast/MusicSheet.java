package ast;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

public class MusicSheet extends Node{

    private ArrayList<Sequence> sequences;

    public MusicSheet(ArrayList<Sequence> sequences) {
        this.sequences = sequences;
    }

    public ArrayList<Sequence> getSequences() {
        return this.sequences;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
