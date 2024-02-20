package ast;

import org.antlr.v4.runtime.tree.TerminalNode;

public class Note extends Operation {
    private final String key;
    private final String pitch;
    private final String beat;
    private final String octave;

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

    // This needs to be deleted later
    public String printNote() {
        return key + pitch;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
