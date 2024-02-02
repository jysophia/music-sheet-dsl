import sys
from antlr4 import *

from Note import Note
from MusicPrinter import MusicPrinter
from parser.MusicLanguageParser import MusicLanguageParser
from parser.MusicLanguageParserVisitor import MusicLanguageParserVisitor
import subprocess
import lilypond


class VisitorInterp(MusicLanguageParserVisitor):
    def __init__(self):
        self.mp = MusicPrinter()

    def visitNote(self, ctx: MusicLanguageParser.NoteContext):
        key = ctx.KEY()
        beat = ctx.BEAT()
        pitch = ctx.PITCH()
        note = Note(key, beat, pitch)
        self.mp.addNote(note)

    def visitProgram(self, ctx: MusicLanguageParser.ProgramContext):
        print("Program starting... ")
        for i in range(0, ctx.getChildCount()):
            self.visit(ctx.getChild(i))
        self.mp.printNotes()
        return 0
