import ast.Node;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.MusicLanguageParser.NoteContext;
import parser.MusicLanguageParserBaseVisitor;

import java.io.IOException;

// TODO: This `Note` may need to change to `Node` when we implement the AST.
public class VisitorInterpreter extends MusicLanguageParserBaseVisitor<Note> {
    private MusicPrinter mp;
    public VisitorInterpreter() {
        this.mp = new MusicPrinter();
    }

    public Note visitNote(NoteContext ctx) {
        TerminalNode key = ctx.KEY();
        TerminalNode beat = ctx.BEAT();
        TerminalNode pitch = ctx.PITCH();
        Note note = new Note(key, beat, pitch);
        this.mp.addNote(note);

        return note;
    }

    public int visitProgram(NoteContext ctx) throws IOException {
        System.out.println("Program starting...");
        for (int i = 0; i < ctx.getChildCount(); i++) {
            this.visit(ctx.getChild(i));
        }
        this.mp.printNotes();
        return 0;
    }
}
