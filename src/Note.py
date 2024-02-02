class Note:
    def __init__(self, key, pitch, beat):
        self.key = key
        self.pitch = pitch
        self.beat = beat

    def printNote(self):
        txt = str(self.key) + str(self.pitch)
        return txt
