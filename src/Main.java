

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

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Code adapted from TinyVars-main in-class example.
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromFileName("TestMusicLanguage/decrSeq.txt"));

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

        // with assistance from AI tools
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("lilypond", "score.ly");
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new File(System.getProperty("user.dir")));
            Process process = processBuilder.start();

            // Read the output (combined output and error streams)
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("LilyPond command executed successfully");
            } else {
                System.err.println("Error executing LilyPond command. Exit code: " + exitCode);
            }
            System.out.println(exitCode);
        } catch (IOException | InterruptedException error) {
            error.printStackTrace();
        }

    }
}
