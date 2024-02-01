parser grammar MusicLanguageParser;
options { tokenVocab=MusicLanguageLexer; }

// Grammar
//program : title table* EOF;
//title : TITLE_START TEXT;
//table: TABLE_START boldrow row*;
//boldrow: row;
//row: ROW_START (item (SEP item)*)? ROW_END;
//item: TEXT;

program: note* EOF;
note: NOTE_START TEXT NOTE_EQ KEY NOTE_DIV BEAT NOTE_DIV PITCH NOTE_END;