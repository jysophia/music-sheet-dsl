

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.MusicLanguageLexer;
import parser.MusicLanguageParser;

import java.io.IOException;

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
            vinterp.visit(tree);
        }
    }
}
