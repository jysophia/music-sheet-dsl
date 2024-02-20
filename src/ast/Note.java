package ast;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Note extends Operation {
    private String key;
    private String pitch;
    private String beat;
    private String octave;

    public Note(String key, String beat, String pitch, String octave) {
        this.key = key;
        this.pitch = pitch;
        this.beat = beat;
        this.octave = octave;
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

    public String getOctave() {
        return octave;
    }

    public Void setKey(String key) {
        this.key = key;
        return null;
    }

    public Void setPitch(String pitch) {
        this.pitch = pitch;
        return null;
    }

    public Void setBeat(String beat) {
        this.beat = beat;
        return null;
    }

    public Void setOctave(String octave) {
        this.octave = octave;
        return null;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
