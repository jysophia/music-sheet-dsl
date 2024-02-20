parser grammar MusicLanguageParser;
options { tokenVocab=MusicLanguageLexer; }

// Grammar
//program : title table* EOF;
//title : TITLE_START TEXT;
//table: TABLE_START boldrow row*;
//boldrow: row;
//row: ROW_START (item (SEP item)*)? ROW_END;
//item: TEXT;

program: musicsheet EOF;
musicsheet: statement*;
statement: declare | set | display | mutate | repeat STMT_NEWLINE?;
declare: DECLARATION varname NAME_RETURN;
set: SET varname ((DOT property) | EQUALS) operation;
operation: note | chord | sequence | note_property;
display: DISPLAY varname;

note: NOTE KEY PITCH? BEAT OCTAVE NOTE_RETURN;
chord: CHORD CHORD_ENTRY CHORD_ENTRY CHORD_ENTRY? CHORD_ENTRY? CHORD_END;
sequence: SEQUENCE SEQUENCE_ENTRY+ SEQUENCE_END;
note_property: (KEY | BEAT | PITCH | OCTAVE) NOTE_RETURN+;
property: SET_KEY | SET_BEAT | SET_PITCH | SET_OCTAVE;

mutate: MUTATE varname DOT mutation_type;
mutation_type: mutate_key | mutate_beat;
mutate_key: MUT_KEY MUT_KEY_NUMBER;
mutate_beat: MUT_BEAT MUT_BEAT_NUMBER;

repeat: REPEAT OPEN_PAREN REP_NUMBER CLOSE_PAREN OPEN_BRACE STMT_NEWLINE* musicsheet CLOSE_BRACE STMT_NEWLINE?;
// repeat: REPEAT OPEN_PAREN REP_NUMBER CLOSE_PAREN OPEN_BRACE STMT_NEWLINE* repeat_cmds CLOSE_BRACE STMT_NEWLINE?;
// repeat_cmds: ((mutate | repeat_display) STMT_NEWLINE)*;
// repeat_display: REPEAT_DISPLAY;

varname: NAME;