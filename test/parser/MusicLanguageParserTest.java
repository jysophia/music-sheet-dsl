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

                Set n1 = $D#1.0_0
                Set n2 = $G1.0_0
                Set n3 = $B1.0_0

                Chord c1
                Set c1 = chord(n1, n2, n3)""";
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser = new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext program = parser.program();
        assertEquals(9,program.children.size()); // 9 because EOF counts as a token
        assertInstanceOf(MusicLanguageParser.StatementContext.class, program.children.get(0));
        // test that the first statement "Note n1" is a declaration
        MusicLanguageParser.StatementContext firstStmt = (MusicLanguageParser.StatementContext) program.children.get(0);
        assertInstanceOf(MusicLanguageParser.DeclareContext.class, firstStmt.children.get(0));

        // test that the second statement "Note n2" is a declaration
        MusicLanguageParser.StatementContext secondStmt = (MusicLanguageParser.StatementContext) program.children.get(1);
        assertInstanceOf(MusicLanguageParser.DeclareContext.class, secondStmt.children.get(0));

        // test that the third statement "Note n3" is a declaration
        MusicLanguageParser.StatementContext thirdStmt = (MusicLanguageParser.StatementContext) program.children.get(2);
        assertInstanceOf(MusicLanguageParser.DeclareContext.class, thirdStmt.children.get(0));

        // test that the fourth statement "Set n1 = $D#1.0_0" is a setting
        MusicLanguageParser.StatementContext fourthStmt = (MusicLanguageParser.StatementContext) program.children.get(3);
        assertInstanceOf(MusicLanguageParser.SetContext.class, fourthStmt.children.get(0));

        // test that the fifth statement "Set n2 = $G1.0_0" is a setting
        MusicLanguageParser.StatementContext fifthStmt = (MusicLanguageParser.StatementContext) program.children.get(4);
        assertInstanceOf(MusicLanguageParser.SetContext.class, fifthStmt.children.get(0));

        // test that the sixth statement "Set n3 = $B1.0_0" is a setting
        MusicLanguageParser.StatementContext sixthStmt = (MusicLanguageParser.StatementContext) program.children.get(5);
        assertInstanceOf(MusicLanguageParser.SetContext.class, sixthStmt.children.get(0));

        // test that the seventh statement "Chord c1" is a setting
        MusicLanguageParser.StatementContext seventhStmt = (MusicLanguageParser.StatementContext) program.children.get(6);
        assertInstanceOf(MusicLanguageParser.DeclareContext.class, seventhStmt.children.get(0));

        // test that the eighth statement "Set c1 = chord(n1, n2, n3)" is a setting
        MusicLanguageParser.StatementContext eighthStmt = (MusicLanguageParser.StatementContext) program.children.get(7);
        assertInstanceOf(MusicLanguageParser.SetContext.class, eighthStmt.children.get(0));

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
        assertEquals(4,program.children.size()); // 4 because EOF counts as a token

        // test that the third statement "Add n1.key 1" is a mutation
        MusicLanguageParser.StatementContext thirdStmt = (MusicLanguageParser.StatementContext) program.children.get(2);
        assertInstanceOf(MusicLanguageParser.MutateContext.class, thirdStmt.children.get(0));

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
        assertEquals(4, program.children.size()); // 4 because EOF counts as a token

        // test that third statement "Add c1.key 1" is a mutation
        MusicLanguageParser.StatementContext thirdStmt = (MusicLanguageParser.StatementContext) program.children.get(2);
        assertInstanceOf(MusicLanguageParser.MutateContext.class, thirdStmt.children.get(0));
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

        // test that first statement "Repeat (5) {" is repeat
        MusicLanguageParser.StatementContext firstStmt = (MusicLanguageParser.StatementContext) program.children.get(0);
        assertInstanceOf(MusicLanguageParser.RepeatContext.class, firstStmt.children.get(0));

        // test that repeat statement has children of size 7
        MusicLanguageParser.RepeatContext repeatStmt = (MusicLanguageParser.RepeatContext) program.children.get(0).getChild(0);
        assertEquals(7, repeatStmt.getChildCount());

        // test that the first statement in repeat is a mutation
        MusicLanguageParser.Repeat_cmdsContext repeat_cmds = (MusicLanguageParser.Repeat_cmdsContext) repeatStmt.children.get(5);
        assertInstanceOf(MusicLanguageParser.MutateContext.class, repeat_cmds.children.get(0));

        // test that the second statement in repeat is a REPEAT_DISPLAY
        assertInstanceOf(MusicLanguageParser.Repeat_displayContext.class, repeat_cmds.children.get(2));
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
