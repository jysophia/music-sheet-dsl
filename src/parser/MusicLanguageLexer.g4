lexer grammar MusicLanguageLexer;

DECLARATION: ('Note' | 'Chord' | 'Sequence') -> mode(NAME_MODE);
SET: 'Set' WS* -> mode(NAME_MODE);
DISPLAY: 'Display' WS* -> mode(NAME_MODE);

WS : [\t ] -> channel(HIDDEN);

mode NAME_MODE;
NAME: ~[[|\]\r\n,.=]+;
NEWLINE : [\r\n]+ -> mode(DEFAULT_MODE); // Should only happen on DECLARATION/DISPLAY

// For non DECLARATION/DISPLAY, we want to move it out of NAME_MODE.
EQUALS: '=' -> mode(SET_VAR_MODE);
DOT: '.' -> mode(SET_PROPERTY_MODE);

mode SET_VAR_MODE;
NOTE: '$' WS* -> mode(NOTE_MODE);
CHORD: 'chord(' WS* -> mode(CHORD_MODE);
SEQUENCE: 'sequence(' WS* -> mode(SEQUENCE_MODE);

mode SET_PROPERTY_MODE;
//PROPERTY: 'key ='|'beat ='|'pitch ='|'octave =' WS* -> mode(NOTE_MODE);

mode NOTE_MODE;
KEY: ('C' | 'D' | 'E' | 'F' | 'G' | 'A' | 'B') WS*;
BEAT: ('0.125' | '0.25' | '0.5' | '1.0') WS*;
PITCH: '#' | 'b' WS*;
OCTAVE: '_' ('-1' | '0' | '1') WS*;
RETURN: NEWLINE -> mode(DEFAULT_MODE);

mode CHORD_MODE;
CHORD_ENTRY: NAME WS* COMMA WS* NAME (WS* COMMA WS* NAME)? (WS* COMMA WS* NAME)? WS* CHORD_END;
COMMA: ',';
CHORD_END: ')' WS* -> mode(DEFAULT_MODE);

mode SEQUENCE_MODE;
SEQUENCE_ENTRY: ITEM+ WS*;
ITEM: NOTE | CHORD;
SEQUENCE_END: ')' WS* -> mode(DEFAULT_MODE);