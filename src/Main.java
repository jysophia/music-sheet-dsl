

import ast.MusicSheet;
// import ast.evaluator.Evaluator;
import ast.Program;
import ast.checkers.VariableChecker;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.MusicLanguageLexer;
import parser.MusicLanguageParser;
import parser.ParseTreeToAST;

import java.io.IOException;

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

        /*
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.out.println("syntax errors");
        } else {
            VisitorInterpreter vinterp = new VisitorInterpreter();
            MusicSheet parsedSheet = vinterp.visitMusicsheet(tree);

            File newFile = new File("./score.ly");

            try {
                // creates file if one does not exist already.
                Boolean fileCreated = newFile.createNewFile();

                if (fileCreated) {
                    System.out.println("New file created");
                } else {
                    System.out.println("File already exists");
                }
            } catch (IOException e) {
                // TODO: handle this exception
                System.out.println("Something went wrong");
                e.printStackTrace();;
            }

            Evaluator e = new Evaluator();
            PrintWriter out = new PrintWriter(new FileWriter("./score.ly"));

            parsedSheet.accept(e, out);

            out.close();
        }

         */
    }
}
