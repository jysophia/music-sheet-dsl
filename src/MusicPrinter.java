import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPrinter {
    ArrayList<Note> notes;
    public MusicPrinter() {
        this.notes = new ArrayList<Note>();
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public void printNotes() {
        StringBuilder score = new StringBuilder("{\n    \\clef bass\n    \\version \"2.25.11\"\n    ");

        for (int i = 0; i < this.notes.size(); i++) {
            score.append(this.notes.get(i).printNote());
            score.append(" ");
        }
        score.append("\n}");

        File newFile = new File("./score.ly");

        try {
            // creates file if one does not exist already.
            Boolean fileCreated = newFile.createNewFile();

            if (fileCreated) {
                System.out.println("New file created");
            } else {
                System.out.println("File already exists");
            }

            FileWriter writer = new FileWriter("./score.ly");
            writer.write(score.toString());
            writer.close();
        } catch (IOException e) {
            // TODO: handle this exception
            System.out.println("Something went wrong");
            e.printStackTrace();;
        }

        // TODO: execute script
//        Runtime runtime = Runtime.getRuntime();
//        Process process = runtime.exec();

    }
}
