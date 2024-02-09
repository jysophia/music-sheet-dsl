package evaluator;

import ast.*;
import ast.evaluator.Evaluator;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;
import parser.MusicLanguageLexer;
import parser.MusicLanguageParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluatorTest {

    @Test
    public void evaluatorTest() {
        //TODO

        // Note input = new Note("c", "sharp", "4");
        // File expected_out = new File("text_output.ly");

        // MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));
        // lexer.reset();
        // MusicLanguageParser parser = new MusicLanguageParser(lexer);

        // visitor
        // Node parsedProgram = parser.program().accept(visitor);

        // StringWriter swOut = new StringWriter();
        // PrintWriter pwOut = new PrintWriter(swOut);
        // Evaluator e = new Evaluator();
        // parsedProgram.accept(e, pwOut);
        // pwOut.close();
        // String out = swOut.toString();
        // assertEquals(expected_out, out);
    }

    @Test
    public void simpleCase() throws IOException {
        Note note1 = new Note("a", "4", "");
        Note note2 = new Note("b", "4", "");
        Note note3 = new Note("c", "4", "");
        Note note4 = new Note("d", "4", "");
        ArrayList<Note> n = new ArrayList<Note>(Arrays.asList(note1, note2, note3, note4));
        Chord c = new Chord(n);
        ArrayList<Node> nc = new ArrayList<Node>(List.of(c));
        Sequence s = new Sequence(nc);

        MusicSheet parsedSheet = new MusicSheet(new ArrayList<Sequence>(List.of(s)));

        Evaluator e = new Evaluator();
        PrintWriter out = new PrintWriter(new FileWriter("./testScore.ly"));

        parsedSheet.accept(e, out);

        out.close();
    }


    @Test
    public void evaluatorErrorTest() {
        //TODO

        // Note input = new Note("c", "sharp", "4");
        // File expected_out = new File("text_output.ly");

        // MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));
        // lexer.reset();
        // MusicLanguageParser parser = new MusicLanguageParser(lexer);

        // visitor
        // Node parsedProgram = parser.program().accept(visitor);

        // StringWriter swOut = new StringWriter();
        // PrintWriter pwOut = new PrintWriter(swOut);
        // Evaluator e = new Evaluator();
        // parsedProgram.accept(e, pwOut);
        // pwOut.close();
        // String out = swOut.toString();
        // assertEquals(expected_out, out);
    }
}
