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
statement: declare | set | display;
declare: DECLARATION varname;
set: SET varname DOT? PROPERTY? EQUALS note | chord | sequence | value;
note: NOTE KEY BEAT PITCH OCTAVE;
chord: CHORD varname CHORD_END;
sequence: SEQUENCE SEQUENCE_ENTRY SEQUENCE_END;
value: KEY | BEAT | PITCH | OCTAVE;
display: DISPLAY varname;

varname: NAME;