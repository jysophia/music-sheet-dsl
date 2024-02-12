package ast;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Note extends Node{
    private String key;
    private String pitch;
    private String beat;

    public Note(String key, String beat, String pitch) {
        this.key = key;
        this.pitch = pitch;
        this.beat = beat;
    }

    public String getKey() {
        return this.key;
    }

    public String getPitch() {
        return this.pitch;
    }

    public String getBeat() {
        return this.beat;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }


    // This needs to be deleted later
    public String printNote() {
        return key + pitch;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
