import ast.*;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.MusicLanguageParser;
import parser.MusicLanguageParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeToAST extends MusicLanguageParserBaseVisitor<Node> {
  @Override
  public MusicSheet visitMusicsheet(MusicLanguageParser.MusicsheetContext ctx) {
    List<Statement> statements = new ArrayList<>();
    // visit every statement in the music sheet context statements and add to list
    for (MusicLanguageParser.StatementContext s : ctx.statement()) {
      statements.add((Statement) s.accept(this));
    }
    return new MusicSheet(statements);
  }

  @Override
  public Statement visitStatement(MusicLanguageParser.StatementContext ctx) {
    return (Statement) visitChildren(ctx);
  }

  @Override
  public Declare visitDeclare(MusicLanguageParser.DeclareContext ctx) {
    return new Declare(ctx.DECLARATION().getText(), (Name) ctx.varname().accept(this));
  }

  @Override
  public Set visitSet(MusicLanguageParser.SetContext ctx) {
    Name name = (Name) ctx.varname().accept(this);
    NoteProperty np = null;
    if (ctx.property() != null) {
      np = (NoteProperty) ctx.property().accept(this); // this might not work
    }
    Operation op = (Operation) ctx.operation().accept(this);

    return new Set(name, np, op);
  }

  @Override
  public Display visitDisplay(MusicLanguageParser.DisplayContext ctx) {
    return new Display((Name) ctx.varname().accept(this));
  }

  @Override
  public Chord visitChord(MusicLanguageParser.ChordContext ctx) {
    List<Name> notes = new ArrayList<>();
    // visit the chord entries of the chord context to build the note names
    for (TerminalNode ce : ctx.CHORD_ENTRY()) {
      notes.add(new Name(ce.getText()));
    }
    return new Chord(notes);
  }

  @Override
  public Note visitNote(MusicLanguageParser.NoteContext ctx) {
    String key = ctx.KEY().getText();
    String beat = ctx.BEAT().getText();
    String pitch = null;
    if (ctx.PITCH() != null) {
      pitch = ctx.PITCH().getText();
    }
    String octave = ctx.OCTAVE().getText();
    return new Note(key, beat, pitch, octave);
  }

  @Override
  public Sequence visitSequence(MusicLanguageParser.SequenceContext ctx) {
    List<Name> chordsAndNotes = new ArrayList<>();

    for (TerminalNode nc : ctx.SEQUENCE_ENTRY()) {
      chordsAndNotes.add(new Name(nc.getText()));
    }

    return new Sequence(chordsAndNotes);
  }

  @Override
  public NoteProperty visitNote_property(MusicLanguageParser.Note_propertyContext ctx) {
    return new NoteProperty(ctx.getChild(0).getText());
  }

  @Override
  public Name visitVarname(MusicLanguageParser.VarnameContext ctx) {
    return new Name(ctx.getText());
  }
}
