lexer grammar MusicLanguageLexer;

PROGRAM_START: 'Start' WS*;
NOTE_START: 'Note' WS* -> mode(NOTE_MODE);

// Line breaks are ignored during tokenization (note that this rule only applies in DEFAULT_MODE, not IDENT_MODE)
WS : [\r\n\t ] -> channel(HIDDEN);

// Mode specifically for tokenizing the arbitrary text inside the title and in table cells
mode NOTE_MODE;
TEXT: ~[[|\]\r\n,.=]+;
NOTE_EQ: '=' WS* 'note(' WS* -> mode(NOTE_ENTRY);

mode NOTE_ENTRY;
NOTE_DIV: ',' WS*;
KEY: ('c' | 'd' | 'e' | 'f' | 'g' | 'a' | 'b' | 'C' | 'D' | 'E' | 'F' | 'G' | 'A' | 'B') WS*;
BEAT: ('0.25' | '0.5' | '1' | '2' | '4') WS*;
PITCH: ('sharp' | 'flat' | 'none') WS*;
NOTE_END: ')' WS* -> mode(DEFAULT_MODE);


