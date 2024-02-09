import ast.MusicSheet;
import ast.Node;
import ast.Note;
import ast.Sequence;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.MusicLanguageParser;
import parser.MusicLanguageParserBaseVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Note note = new Note(key.toString(), beat.toString(), pitch.toString());
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

        // Everything below this is just for pseudo testing the evaluator. Can overwrite.
        Note note1 = new Note("a", "4", "");
        Note note2 = new Note("b", "4", "");
        Note note3 = new Note("c", "4", "");
        Note note4 = new Note("d", "4", "");
        ArrayList<Node> n = new ArrayList<Node>(Arrays.asList(note1, note2, note3, note4));
        Sequence s = new Sequence(n);
        return new MusicSheet(new ArrayList<Sequence>(List.of(s)));
    }
}
