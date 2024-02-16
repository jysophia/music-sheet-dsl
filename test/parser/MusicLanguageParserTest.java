package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MusicLanguageParserTest {

    MusicLanguageParser.ChordContext chordExample() {
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
//        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);
        MusicLanguageParser parser = new MusicLanguageParser(tokens);
        return parser.chord();
    }
    @Test
    public void chordInitialTest() throws IOException {
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromFileName("TestMusicLanguage/chords.txt"));

        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        MusicLanguageParser parser = new MusicLanguageParser(tokens);

        MusicLanguageParser.ProgramContext tree = parser.program();
        MusicLanguageParser.ChordContext context = chordExample();
//        assertEquals(5, context.children.size());

//        assertInstanceOf(MusicLanguageParser.ChordContext.class, context.children.get(1));
        MusicLanguageParser.ChordContext firstChord = (MusicLanguageParser.ChordContext) context.children.get(1);

        assertEquals(3, firstChord.children.size());

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
