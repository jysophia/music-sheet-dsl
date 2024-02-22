lexer grammar MusicLanguageLexer;

DECLARATION: ('Note' | 'Chord' | 'Sequence') WS*-> mode(NAME_MODE);
SET: 'Set' WS* -> mode(NAME_MODE);
DISPLAY: 'Display' WS* -> mode(NAME_MODE);
MUTATE: ('Add' | 'Sub') WS* -> mode(NAME_MODE);
REPEAT: 'Repeat' WS* -> mode(REPEAT_MODE);
REPEAT_DISPLAY: 'Display this';

WS : [\t ] -> channel(HIDDEN);
STMT_NEWLINE: [\r\n] -> channel(HIDDEN);
COMMA: ',' -> channel(HIDDEN);
CLOSE_BRACE: '}';

mode REPEAT_MODE;
REP_NUMBER: [0-9]+;
OPEN_PAREN: '(' WS*;
OPEN_BRACE: '{' WS* -> mode(DEFAULT_MODE);
CLOSE_PAREN: ')' WS*;

mode NAME_MODE;
NAME: ~[[|\]\r\n,.=\t ()]+;
NAME_WS: [\t ] -> channel(HIDDEN);
NAME_RETURN: [\r\n]+ -> mode(DEFAULT_MODE); // Should only happen on DECLARATION/DISPLAY

// For non DECLARATION/DISPLAY, we want to move it out of NAME_MODE.
EQUALS: '=' -> mode(SET_VAR_MODE);
DOT: '.' -> mode(SET_PROPERTY_MODE);

mode SET_VAR_MODE;
NOTE: '$' -> mode(NOTE_MODE);
CHORD: 'chord(' -> mode(CHORD_MODE);
SEQUENCE: 'sequence(' -> mode(SEQUENCE_MODE);
SET_VAR_WS: WS -> channel(HIDDEN);

mode SET_PROPERTY_MODE;
SET_KEY: 'key' WS* '=' WS* -> mode(NOTE_MODE);
SET_BEAT: 'beat' WS* '=' WS* -> mode(NOTE_MODE);
SET_PITCH: 'pitch' WS* '=' WS* -> mode(NOTE_MODE);
SET_OCTAVE: 'octave' WS* '=' WS* -> mode(NOTE_MODE);
MUT_KEY: 'key' -> mode(MUT_KEY_MODE);
MUT_BEAT: 'beat' -> mode(MUT_BEAT_MODE);
SET_MUT_WS: WS -> channel(HIDDEN);

mode MUT_KEY_MODE;
MUT_KEY_NUMBER: ('0.5' | '1.0' | '1' | '1.5' | '2.0' | '2' | '2.5' | '3.0' | '3' | '3.5' |
                '4.0' | '4' | '4.5' | '5.0' | '5' | '5.5' | '6.0' | '6' | '6.5' | '7.0' |
                '7' | '7.5' | '8.0' | '8') -> mode(DEFAULT_MODE);
MUT_KEY_WS: [\t ]+ -> channel(HIDDEN);

mode MUT_BEAT_MODE;
MUT_BEAT_NUMBER: ('1' | '2' | '3') -> mode(DEFAULT_MODE);
MUT_BEAT_WS: [\t ]+ -> channel(HIDDEN);

mode NOTE_MODE;
KEY: ('C' | 'D' | 'E' | 'F' | 'G' | 'A' | 'B') WS*;
BEAT: ('0.125' | '0.25' | '0.5' | '1.0') WS*;
PITCH: '#' | 'b' WS*;
OCTAVE: '_' ('-1' | '0' | '1') WS*;
NOTE_RETURN: [\r\n]+ -> mode(DEFAULT_MODE);

mode CHORD_MODE;
CHORD_ENTRY: NAME WS*;
CHORD_END: ')' WS* -> mode(DEFAULT_MODE);
CHORD_SEPARATOR: WS* COMMA WS* -> channel(HIDDEN);

mode SEQUENCE_MODE;
SEQUENCE_ENTRY: NAME WS*;
SEQUENCE_END: ')' WS* -> mode(DEFAULT_MODE);
SEQUENCE_SEPARATOR: WS* COMMA WS* -> channel(HIDDEN);