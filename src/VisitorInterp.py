import sys
from antlr4 import *
from parser.MusicLanguageParser import MusicLanguageParser
from parser.MusicLanguageParserVisitor import MusicLanguageParserVisitor


class VisitorInterp(MusicLanguageParserVisitor):
    def visitNote(self, ctx: MusicLanguageParser.NoteContext):
        return ctx.getText()

    def visitProgram(self, ctx: MusicLanguageParser.ProgramContext):
        print("Program starting... ")
        for i in range(0, ctx.getChildCount()):
            print(self.visit(ctx.getChild(i)))
        return 0
