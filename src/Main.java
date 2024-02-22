

import ast.MusicSheet;
// import ast.evaluator.Evaluator;
import ast.Program;
import ast.evaluator.Evaluator;
import ast.checkers.VariableChecker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.MusicLanguageLexer;
import parser.MusicLanguageParser;
import parser.ParseTreeToAST;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        // Code adapted from TinyVars-main in-class example.
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromFileName("TestMusicLanguage/chords.txt"));

        // Uncomment to test invalid file
        //MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromFileName("TestMusicLanguage/staticcheck.txt"));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();

        TokenStream tokens = new CommonTokenStream(lexer);
        MusicLanguageParser parser = new MusicLanguageParser(tokens);
        ParseTreeToAST visitor = new ParseTreeToAST();

        Program program = visitor.visitProgram(parser.program());

        // static checking
        VariableChecker v = new VariableChecker();
        String errors = v.checkProgram(program);
        if (!errors.isEmpty()) {
            System.out.println(errors);
        }

        System.out.println("Parsing to AST complete");

        Evaluator e = new Evaluator();
        PrintWriter out = new PrintWriter(new FileWriter("./score.ly"));

        program.accept(e, out);

        out.close();

    }
}
