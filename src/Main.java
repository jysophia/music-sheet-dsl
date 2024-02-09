

import ast.MusicSheet;
import ast.evaluator.Evaluator;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.MusicLanguageLexer;
import parser.MusicLanguageParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        // Code adapted from TinyVars-main in-class example.
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromFileName("TestMusicLanguage/notes.txt"));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser = new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext tree = parser.program();

        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.out.println("syntax errors");
        } else {
            VisitorInterpreter vinterp = new VisitorInterpreter();
            MusicSheet parsedSheet = vinterp.visitProgram(tree);

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
    }
}
