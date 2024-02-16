parser grammar MusicLanguageParser;
options { tokenVocab=MusicLanguageLexer; }

// Grammar
//program : title table* EOF;
//title : TITLE_START TEXT;
//table: TABLE_START boldrow row*;
//boldrow: row;
//row: ROW_START (item (SEP item)*)? ROW_END;
//item: TEXT;

program: statement* EOF;
statement: declare | set | display | mutate STMT_NEWLINE?;
declare: DECLARATION varname NAME_RETURN;
set: SET varname (DOT property)? EQUALS (note | chord | sequence | note_property);
display: DISPLAY varname;
mutate: MUTATE varname DOT (KEY | BEAT) MUT_KEY_NUMBER;

note: NOTE KEY BEAT PITCH OCTAVE NOTE_RETURN;
chord: CHORD varname varname+ CHORD_END;
sequence: SEQUENCE SEQUENCE_ENTRY SEQUENCE_END;
note_property: KEY | BEAT | PITCH | OCTAVE;
property: SET_KEY | SET_BEAT | SET_PITCH | SET_OCTAVE;

varname: NAME;