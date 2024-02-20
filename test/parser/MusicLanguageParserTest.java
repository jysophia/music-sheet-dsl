package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MusicLanguageParserTest {
    @Test
    public void testParserChordSuccessTest() throws IOException {
        String input = """
                Note n1
                Note n2
                Note n3
                
                Chord c1

                Set n1 = $D#1.0_0
                Set n2 = $G1.0_0
                Set n3 = $B1.0_0
                
                Set c1 = chord(n1, n2, n3)""";
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser = new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext program = parser.program();
        assertEquals(2, program.children.size()); // music sheet and EOF

        MusicLanguageParser.MusicsheetContext musicSheet = program.musicsheet();
        assertEquals(8, musicSheet.children.size());
        assertInstanceOf(MusicLanguageParser.StatementContext.class, musicSheet.children.getFirst());

        // test that the first three statements are declarations
        for (int i = 0; i < 4; i++) {
            MusicLanguageParser.StatementContext stmt = (MusicLanguageParser.StatementContext) musicSheet.children.get(i);
            assertInstanceOf(MusicLanguageParser.DeclareContext.class, stmt.children.getFirst());
        }

        // test that the remaining statements are "set"
        for (int i = 4; i < musicSheet.getChildCount(); i++) {
            MusicLanguageParser.StatementContext stmt = (MusicLanguageParser.StatementContext) musicSheet.children.get(i);
            assertInstanceOf(MusicLanguageParser.SetContext.class, stmt.children.getFirst());
        }
    }

    @Test
    public void testParserNoteMutateSuccessTest() throws IOException {
        String input = """
                Note n1
                Set n1 = $D#1.0_0
                Add n1.key 1""";
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser = new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext program = parser.program();
        assertEquals(2, program.children.size()); // music sheet and EOF

        MusicLanguageParser.MusicsheetContext musicSheet = program.musicsheet();
        assertEquals(3, musicSheet.children.size());

        // test that the third statement "Add n1.key 1" is a mutation
        MusicLanguageParser.StatementContext thirdStmt = (MusicLanguageParser.StatementContext) musicSheet.children.get(2);
        assertInstanceOf(MusicLanguageParser.MutateContext.class, thirdStmt.children.getFirst());

    }
    @Test
    public void testParserChordMutateSuccessTest() throws IOException {
        String input = """
                Chord c1
                Set c1 = chord(n1, n2, n3)
                Add c1.key 1""";
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }

        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser =  new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext program = parser.program();
        assertEquals(2, program.children.size()); // music sheet and EOF

        MusicLanguageParser.MusicsheetContext musicSheet = program.musicsheet();
        assertEquals(3, musicSheet.children.size());

        // test that third statement "Add c1.key 1" is a mutation
        MusicLanguageParser.StatementContext thirdStmt = (MusicLanguageParser.StatementContext) musicSheet.children.get(2);
        assertInstanceOf(MusicLanguageParser.MutateContext.class, thirdStmt.children.getFirst());
    }

    @Test
    public void testParserRepeatSuccessTest() throws IOException {
        String input = """
                Repeat (5) {
                Add n1.key 1
                Display this
                }
                """;
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }

        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser =  new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext program = parser.program();
        assertEquals(2, program.children.size()); // 2 because Repeat is its own child and EOF count as child

        MusicLanguageParser.MusicsheetContext musicSheet = program.musicsheet();
        MusicLanguageParser.StatementContext stmt = (MusicLanguageParser.StatementContext) musicSheet.children.getFirst();

        // test that first statement "Repeat (5) {" is repeat
        assertInstanceOf(MusicLanguageParser.RepeatContext.class, stmt.children.getFirst());

        MusicLanguageParser.RepeatContext repeatStmt = (MusicLanguageParser.RepeatContext) stmt.getChild(0);
        // test that repeat statement has children of size 8
        assertEquals(8, repeatStmt.getChildCount());

        // test that the first statement in repeat is a mutation
        MusicLanguageParser.MusicsheetContext mutationStatement = (MusicLanguageParser.MusicsheetContext) repeatStmt.children.get(5);
        assertInstanceOf(MusicLanguageParser.MutateContext.class, mutationStatement.children.getFirst().getChild(0));

        // test that the second statement in repeat is a REPEAT_DISPLAY
//        assertInstanceOf(MusicLanguageParser.RepeatContext.class, repeat_ms.children.get(2));
    }
    @Test
    void testParserProgramNoStatementSuccess() {
        fail("Not Implemented Yet");
    }

    @Test
    void testParserStatementNoteSuccess() {
        fail("Not Implemented Yet");
    }

    @Test
    void testParserStatementNoteChordSuccess() {
        fail("Not Implemented Yet");
    }

    @Test
    void testParserStatementNoteChordSequenceSuccess() {
        fail("Not Implemented Yet");
    }

    @Test
    void testParserStatementChordNoteSuccess() {
        fail("Not Implemented Yet");
    }

    @Test
    void testParserStatementSequenceChordNoteSuccess() {
        fail("Not Implemented Yet");
    }
}
