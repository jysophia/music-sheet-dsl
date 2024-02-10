package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MusicLanguageLexerTest {
    private List<? extends Token> testTokenization(String input) {
        MusicLanguageLexer lexer = new MusicLanguageLexer(CharStreams.fromString(input));
        List<? extends Token> tokens = lexer.getAllTokens();
        tokens.removeIf(t -> t.getChannel() != 0);
        return tokens;
    }

    @Test
    void testLexerInputNoteSuccess() {
        // Setup
        String testInput = "testNote = $d1.0#_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(7, tokens.size());
        assertEquals(MusicLanguageLexer.TEXT, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.EQ, tokens.get(1).getType());
        assertEquals(MusicLanguageLexer.NOTE, tokens.get(2).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.BEAT, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.PITCH, tokens.get(5).getType());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(6).getType());
    }

    @Test
    void testLexerInputNoteHigherOctaveSuccess() {
        String testInput = "testNote = $d1.0#_1\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(MusicLanguageLexer.OCTAVE, tokens.getLast().getType());
        assertEquals("_1", tokens.getLast().toString());
    }

    @Test
    void testLexerInputNoteLowerOctaveSuccess() {
        String testInput = "testNote = $d1.0#_-1\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(MusicLanguageLexer.OCTAVE, tokens.getLast().getType());
        assertEquals("_-1", tokens.getLast().toString());
    }

    @Test
    void testLexerInputNoteInvalidKeyFail() {
        String testInput = "testNote = $x1.0#_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertNotEquals(MusicLanguageLexer.KEY, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.TEXT, tokens.get(4).getType());
        assertEquals("x", tokens.get(4).toString());
    }

    @Test
    void testLexerInputNoteInvalidBeatFail() {
        String testInput = "testNote = $x2.0#_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertNotEquals(MusicLanguageLexer.PITCH, tokens.get(5).getType());
        assertEquals(MusicLanguageLexer.TEXT, tokens.get(5).getType());
        assertEquals("2.0", tokens.get(5).toString());
    }

    @Test
    void testLexerInputNoteNoPitchSuccess() {
        String testInput = "testNote = $x1.0_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        tokens.forEach(token -> {
           assertNotEquals(MusicLanguageLexer.PITCH, token.getType());
        });
    }

    @Test
    void testLexerInputNoteInvalidPitchFail() {
        String testInput = "testNote = $x1.0@_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertNotEquals(MusicLanguageLexer.PITCH, tokens.get(5).getType());
        assertEquals(MusicLanguageLexer.TEXT, tokens.get(5).getType());
        assertEquals("@", tokens.get(5).toString());
    }

    @Test
    void testLexerInputNoteInvalidOctaveFail() {
        String testInput = "testNote = $x1.0#_2\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertNotEquals(MusicLanguageLexer.OCTAVE, tokens.getLast().getType());
        assertEquals(MusicLanguageLexer.TEXT, tokens.getLast().getType());
        assertEquals("2", tokens.get(5).toString());
    }

    @Test
    void testLexerInputChordSuccess() {
        // Setup
        String testInput = "testChord = chord($c0.25#_0, $e0.25_0, $g0.25_0)\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(21, tokens.size());
        assertEquals(MusicLanguageLexer.TEXT, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.CHORD, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.CHORD_ENTRY, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.CHORD_NOTE, tokens.get(5).getType());
        assertEquals(MusicLanguageLexer.C_KEY, tokens.get(6).getType());
        assertEquals(MusicLanguageLexer.C_BEAT, tokens.get(7).getType());
        assertEquals(MusicLanguageLexer.C_PITCH, tokens.get(8).getType());
        assertEquals(MusicLanguageLexer.C_OCTAVE, tokens.get(9).getType());
    }

    @Test
    void testLexerInputChordTooManyNotesFail() {
        // Setup
        String testInput = "testChord = chord($a0.25#_0, $b0.25_0, $c0.25_0, $d0.25_0, $e0.25_0)\n";
        List<? extends Token> tokens = testTokenization(testInput);

        // Checks that none of the tokens are chords.
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.CHORD, token.getType());
        });
    }

    @Test
    void testLexerInputChordMissingParenthesesNotesFail() {
        // Setup
        String testInput = "testChord = chord $a0.25#_0, $b0.25_0, $c0.25_0, $d0.25_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        // Checks that none of the tokens are chords.
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.CHORD, token.getType());
        });
    }

    @Test
    void testLexerInputChordMultipleParenthesesNotesFail() {
        fail("Not Implemented Yet");
    }

    @Test
    void testLexerInputSequenceSuccess() {
        // Setup
        String testInput = "testNote = $d1.0#_0\n" +
                "testChord = chord($c0.25#_0, $e0.25_0, $g0.25_0)\n" +
                "testSequence = sequence(testNote, testChord)";
        List<? extends Token> tokens = testTokenization(testInput);

        fail("Not Implemented Yet");
    }

    // TODO: add some incorrect sequences.
    @Test
    void testLexerInputWhitespaceAgnosticSuccess() {
        // Setup
        String testInput = "testNote=$d1.0#_0testNote2=$e1.0#_0";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(14, tokens.size());

        assertEquals(MusicLanguageLexer.TEXT, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.EQ, tokens.get(1).getType());
        assertEquals(MusicLanguageLexer.NOTE, tokens.get(2).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.BEAT, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.PITCH, tokens.get(5).getType());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(6).getType());

        assertEquals(MusicLanguageLexer.TEXT, tokens.get(7).getType());
        assertEquals(MusicLanguageLexer.EQ, tokens.get(8).getType());
        assertEquals(MusicLanguageLexer.NOTE, tokens.get(9).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.get(10).getType());
        assertEquals(MusicLanguageLexer.BEAT, tokens.get(11).getType());
        assertEquals(MusicLanguageLexer.PITCH, tokens.get(12).getType());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(13).getType());
    }

    @Test
    void testLexerInputNewlineAgnosticSuccess() {
        // Setup
        String testInput = "testNote\n" +
                "=\n" +
                "$d1.0#_0\n\n\n" +
                "testNote2 = $e1.0\n#_0";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(14, tokens.size());

        assertEquals(MusicLanguageLexer.TEXT, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.EQ, tokens.get(1).getType());
        assertEquals(MusicLanguageLexer.NOTE, tokens.get(2).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.BEAT, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.PITCH, tokens.get(5).getType());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(6).getType());

        assertEquals(MusicLanguageLexer.TEXT, tokens.get(7).getType());
        assertEquals(MusicLanguageLexer.EQ, tokens.get(8).getType());
        assertEquals(MusicLanguageLexer.NOTE, tokens.get(9).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.get(10).getType());
        assertEquals(MusicLanguageLexer.BEAT, tokens.get(11).getType());
        assertEquals(MusicLanguageLexer.PITCH, tokens.get(12).getType());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(13).getType());
    }

    @Test
    void testLexerInputMissingEqualsFail() {
        // Setup
        String testInput = "testNote $d1.0#_0";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(6, tokens.size());

        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.EQ, token.getType());
        });
    }
}
