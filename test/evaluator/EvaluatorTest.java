package evaluator;

import ast.Note;
import ast.evaluator.Evaluator;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;
import parser.MusicLanguageLexer;
import parser.MusicLanguageParser;

import java.io.PrintWriter;
import java.io.StringWriter;

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
