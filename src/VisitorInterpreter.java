import ast.MusicSheet;
import ast.Node;
import ast.Note;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.MusicLanguageParser;
import parser.MusicLanguageParserBaseVisitor;

// TODO: This `ast.Note` may need to change to `Node` when we implement the AST.
public class VisitorInterpreter extends MusicLanguageParserBaseVisitor<Node> {
    private MusicPrinter mp;
    public VisitorInterpreter() {
        this.mp = new MusicPrinter();
    }

    public Note visitNote(MusicLanguageParser.NoteContext ctx) {
        TerminalNode key = ctx.KEY();
        TerminalNode beat = ctx.BEAT();
        TerminalNode pitch = ctx.PITCH();
        Note note = new Note(key, beat, pitch);
        this.mp.addNote(note);

        return note;
    }

    @Override
    public MusicSheet visitProgram(MusicLanguageParser.ProgramContext ctx) {
        System.out.println("Program starting...");
        for (int i = 0; i < ctx.getChildCount(); i++) {
            this.visit(ctx.getChild(i));
        }
        this.mp.printNotes();
        return new MusicSheet();
    }
}
