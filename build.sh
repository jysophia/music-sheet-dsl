pip install -r requirements.txt
antlr4 -v 4.13.0 -Dlanguage=Python3 -visitor src/parser/MusicLanguageLexer.g4
antlr4 -v 4.13.0 -Dlanguage=Python3 -visitor src/parser/MusicLanguageParser.g4