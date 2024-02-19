package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.Assert;
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
    void testLexerDeclareNoteSuccess() {
        // Setup
        String testInput = "Note testNote\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(3, tokens.size());
        assertEquals(MusicLanguageLexer.DECLARATION, tokens.getFirst().getType());
        assertEquals("Note ", tokens.getFirst().getText());
        assertEquals(MusicLanguageLexer.NAME, tokens.get(1).getType());
        assertEquals("testNote", tokens.get(1).getText());
//        assertEquals(MusicLanguageLexer.NEWLINE, tokens.get(2).getType());
    }

    @Test
    void testLexerDeclareChordSuccess() {
        // Setup
        String testInput = "Chord testChord\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(3, tokens.size());
        assertEquals(MusicLanguageLexer.DECLARATION, tokens.getFirst().getType());
        assertEquals("Chord ", tokens.getFirst().getText());
        assertEquals(MusicLanguageLexer.NAME, tokens.get(1).getType());
        assertEquals("testChord", tokens.get(1).getText());
//        assertEquals(MusicLanguageLexer.NEWLINE, tokens.get(2).getType());
    }

    @Test
    void testLexerDeclareSequenceSuccess() {
        // Setup
        String testInput = "Sequence testSequence\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(3, tokens.size());
        assertEquals(MusicLanguageLexer.DECLARATION, tokens.getFirst().getType());
        assertEquals("Sequence ", tokens.getFirst().getText());
        assertEquals(MusicLanguageLexer.NAME, tokens.get(1).getType());
        assertEquals("testSequence", tokens.get(1).getText());
//        assertEquals(MusicLanguageLexer.NEWLINE, tokens.get(2).getType());
    }

    @Test
    void testLexerSetNoteAllPropertiesSuccess() {
        String testInput = "Set testNote = $C#1.0_0";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(8, tokens.size());
        assertEquals(MusicLanguageLexer.SET, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.NAME, tokens.get(1).getType());
        assertEquals("testNote", tokens.get(1).getText());
        assertEquals(MusicLanguageLexer.EQUALS, tokens.get(2).getType());
        assertEquals(MusicLanguageLexer.NOTE, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.get(4).getType());
        assertEquals("C", tokens.get(4).getText());
        assertEquals(MusicLanguageLexer.PITCH, tokens.get(5).getType());
        assertEquals("#", tokens.get(5).getText());
        assertEquals(MusicLanguageLexer.BEAT, tokens.get(6).getType());
        assertEquals("1.0", tokens.get(6).getText());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(7).getType());
        assertEquals("_0", tokens.get(7).getText());
    }

    @Test
    void testLexerSetNoteAllPropertiesWithNewLineSuccess() {
        String testInput = "Set testNote = $C#1.0_0";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(7).getType());
        assertEquals("_0", tokens.get(7).getText());
//        assertEquals(MusicLanguageLexer.RETURN, tokens.getLast().getType());
    }

    @Test
    void testLexerSetNoteAllPropertiesHigherOctaveSuccess() {
        String testInput = "Set testNote = $C#1.0_1\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(tokens.size() - 2).getType());
        assertEquals("_1", tokens.get(tokens.size() - 2).getText());
    }

    @Test
    void testLexerSetNoteAllPropertiesLowerOctaveSuccess() {
        String testInput = "Set testNote = $C#1.0_-1\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(MusicLanguageLexer.OCTAVE, tokens.get(tokens.size() - 2).getType());
        assertEquals("_-1", tokens.get(tokens.size() - 2).getText());
    }


    @Test
    void testLexerSetNoteAllPropertiesInvalidKeyFail() {
        String testInput = "Set testNote = $c#1.0_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        // TODO: How to test for an expected token recognition error.
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.KEY, token.getType());
        });
    }

    @Test
    void testLexerSetNoteAllPropertiesInvalidBeatFail() {
        String testInput = "Set testNote = $C#2.0_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        // TODO: How to test for an expected token recognition error.
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.BEAT, token.getType());
        });
    }

    @Test
    void testLexerSetNoteAllPropertiesNoPitchSuccess() {
        String testInput = "Set testNote = $C1.0_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        tokens.forEach(token -> {
           assertNotEquals(MusicLanguageLexer.PITCH, token.getType());
        });
    }

    @Test
    void testLexerSetNoteAllPropertiesInvalidPitchFail() {
        String testInput = "Set testNote = $C@2.0_0\n";
        List<? extends Token> tokens = testTokenization(testInput);

        // TODO: How to test for an expected token recognition error.
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.PITCH, token.getType());
        });
    }

    @Test
    void testLexerSetNoteAllPropertiesInvalidOctaveFail() {
        String testInput = "Set testNote = $C#1.0_2\n";
        List<? extends Token> tokens = testTokenization(testInput);

        // TODO: How to test for an expected token recognition error.
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.OCTAVE, token.getType());
        });
    }

    @Test
    void testLexerSetNoteKeySuccess() {
        String testInput = "Set testNote.key = C";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(5, tokens.size());
        assertEquals(MusicLanguageLexer.SET_KEY, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.KEY, tokens.getLast().getType());
    }

    @Test
    void testLexerSetNoteBeatSuccess() {
        String testInput = "Set testNote.beat = 0.25";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(5, tokens.size());
        assertEquals(MusicLanguageLexer.SET_BEAT, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.BEAT, tokens.getLast().getType());
    }

    @Test
    void testLexerSetNotePitchSuccess() {
        String testInput = "Set testNote.pitch = b";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(5, tokens.size());
        assertEquals(MusicLanguageLexer.SET_PITCH, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.PITCH, tokens.getLast().getType());
    }

    @Test
    void testLexerSetNoteOctaveSuccess() {
        String testInput = "Set testNote.octave = _-1";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(5, tokens.size());
        assertEquals(MusicLanguageLexer.SET_OCTAVE, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.OCTAVE, tokens.getLast().getType());
    }

    @Test
    void testLexerSetChordSuccess() {
        // Setup
        String testInput = "Set testChord = chord(note1, note2)\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(7, tokens.size());
        assertEquals(MusicLanguageLexer.SET, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.EQUALS, tokens.get(2).getType());
        assertEquals(MusicLanguageLexer.CHORD, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.CHORD_ENTRY, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.CHORD_ENTRY, tokens.get(5).getType());
    }

    @Test
    void testLexerInputChordMissingParenthesesNotesFail() {
        // Setup
        String testInput = "Set testChord = chord note1, note2";
        List<? extends Token> tokens = testTokenization(testInput);

        //TODO: currently this works because parentheses are separate tokens and hidden.
        fail("Confirm if this should work or not");
        tokens.forEach(token -> {
            assertNotEquals(MusicLanguageLexer.CHORD, token.getType());
        });
    }


    @Test
    void testLexerSetSequenceSuccess() {
        // Setup
        String testInput = "Set testSequence = sequence(testNote, testChord)";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(7, tokens.size());
        assertEquals(MusicLanguageLexer.SET, tokens.getFirst().getType());
        assertEquals(MusicLanguageLexer.EQUALS, tokens.get(2).getType());
        assertEquals(MusicLanguageLexer.SEQUENCE, tokens.get(3).getType());
        assertEquals(MusicLanguageLexer.SEQUENCE_ENTRY, tokens.get(4).getType());
        assertEquals(MusicLanguageLexer.SEQUENCE_ENTRY, tokens.get(5).getType());
    }

    @Test
    void testLexerMultipleStatementsSuccess() {
        // Setup
        String testInput = """
                Note testNote
                Set testNote = $C#1.0_0
                Set testNote.key = C
                """;
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(18, tokens.size());
    }

    @Test
    void testLexerAddStatementSuccess() {
        // Setup
        String testInput = "Add n1.key 1";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(5, tokens.size());
    }

    @Test
    void testLexerSubStatementSuccess() {
        // Setup
        String testInput = "Sub n1.key 1";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(5, tokens.size());
    }


    @Test
    void testLexerDisplayStatementSuccess() {
        // Setup
        String testInput = "Display s1";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(2, tokens.size());
    }

    @Test
    void testLexerRepeatStatementSuccess() {
        String testInput = "Repeat (5) {\nAdd n1.key 1\n}\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(11, tokens.size());
    }

    @Test
    void testLexerRepeatMultipleStatementsSuccess() {
        String testInput = "Sequence s1\nRepeat (5) {\nAdd n1.key 1\nSet s1 = sequence(n1)\nDisplay s1\n}\n";
        List<? extends Token> tokens = testTokenization(testInput);

        assertEquals(23, tokens.size());
    }
}
