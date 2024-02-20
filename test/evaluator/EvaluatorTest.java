//package evaluator;
//
//import ast.*;
//import ast.evaluator.Evaluator;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class EvaluatorTest {
//
//    @Test
//    public void evaluatorTest() {
//        //TODO
//
//        // Note input = new Note("c", "sharp", "4");
//        // File expected_out = new File("text_output.ly");
//
//        // MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));
//        // lexer.reset();
//        // MusicLanguageParser parser = new MusicLanguageParser(lexer);
//
//        // visitor
//        // Node parsedProgram = parser.program().accept(visitor);
//
//        // StringWriter swOut = new StringWriter();
//        // PrintWriter pwOut = new PrintWriter(swOut);
//        // Evaluator e = new Evaluator();
//        // parsedProgram.accept(e, pwOut);
//        // pwOut.close();
//        // String out = swOut.toString();
//        // assertEquals(expected_out, out);
//    }
//
//    @Test
//    public void simpleCase() throws IOException {
//        Note note1 = new Note("c", "2", "flat");
//        Note note2 = new Note("e", "2", "");
//        Note note3 = new Note("g", "2", "sharp");
//        Note note4 = new Note("b", "2", "");
//        ArrayList<Note> n = new ArrayList<>(Arrays.asList(note1, note2, note3, note4));
//        Chord c = new Chord(n, note4.getBeat());
//        ArrayList<Node> nc = new ArrayList<>(List.of(c));
//        Sequence s = new Sequence(nc);
//
//        MusicSheet parsedSheet = new MusicSheet(new ArrayList<>(List.of(s)));
//
//        Evaluator e = new Evaluator();
//        PrintWriter out = new PrintWriter(new FileWriter("./testScore.ly"));
//
//        parsedSheet.accept(e, out);
//
//        out.close();
//    }
//
//
//    @Test
//    public void evaluatorErrorTest() {
//        //TODO
//
//        // Note input = new Note("c", "sharp", "4");
//        // File expected_out = new File("text_output.ly");
//
//        // MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));
//        // lexer.reset();
//        // MusicLanguageParser parser = new MusicLanguageParser(lexer);
//
//        // visitor
//        // Node parsedProgram = parser.program().accept(visitor);
//
//        // StringWriter swOut = new StringWriter();
//        // PrintWriter pwOut = new PrintWriter(swOut);
//        // Evaluator e = new Evaluator();
//        // parsedProgram.accept(e, pwOut);
//        // pwOut.close();
//        // String out = swOut.toString();
//        // assertEquals(expected_out, out);
//    }
//}
