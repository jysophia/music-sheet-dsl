import sys
from antlr4 import *
from parser.MusicLanguageLexer import MusicLanguageLexer
from parser.MusicLanguageParser import MusicLanguageParser
from VisitorInterp import VisitorInterp


def main(argv):
    input_stream = FileStream(argv[1])
    lexer = MusicLanguageLexer(input_stream)
    stream = CommonTokenStream(lexer)
    parser = MusicLanguageParser(stream)
    tree = parser.program()
    if parser.getNumberOfSyntaxErrors() > 0:
        print("syntax errors")
    else:
        vinterp = VisitorInterp()
        vinterp.visit(tree)

if __name__ == '__main__':
    main(sys.argv)
