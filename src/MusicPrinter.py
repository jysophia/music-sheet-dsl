import subprocess
import lilypond

class MusicPrinter:
    def __init__(self):
        self.notes = list()

    def addNote(self, note):
        self.notes.append(note)


    def printNotes(self):
        score = "{\n    \\clef base\n    "
        for note in self.notes:
            score += note.printNote() + " "

        score += "\n}"
        open("../score.ly", "w").write(score)
        subprocess.run([lilypond.executable(), "../score.ly"])
