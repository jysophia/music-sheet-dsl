package ast;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Note extends Node{
    private String key;
    private String pitch;
    private String beat;

    public Note(String key, String pitch, String beat) {
        this.key = key;
        this.pitch = pitch;
        this.beat = beat;
    }

    public String printNote() {
        return key + pitch;
    }

    @Override
    public <C,T> T accept(C context, MusicSheetVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
