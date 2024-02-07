import org.antlr.v4.runtime.tree.TerminalNode;

public class Note {
    private TerminalNode key;
    private TerminalNode pitch;
    private TerminalNode beat;

    public Note(TerminalNode key, TerminalNode pitch, TerminalNode beat) {
        this.key = key;
        this.pitch = pitch;
        this.beat = beat;
    }

    public String printNote() {
        return key.toString() + pitch.toString();
    }
}
