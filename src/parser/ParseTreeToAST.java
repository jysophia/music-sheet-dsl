package parser;

import ast.*;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.MusicLanguageParser;
import parser.MusicLanguageParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeToAST extends MusicLanguageParserBaseVisitor<Node> {
  @Override
  public Program visitProgram(MusicLanguageParser.ProgramContext ctx) {
    return new Program((MusicSheet) ctx.musicsheet().accept(this));
  }

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
    Property p = null;
    if (ctx.property() != null) {
      p = (Property) ctx.property().accept(this); // this might not work
    }
    Operation op = (Operation) ctx.operation().accept(this);

    return new Set(name, p, op);
  }

  @Override
  public Display visitDisplay(MusicLanguageParser.DisplayContext ctx) {
    return new Display((Name) ctx.varname().accept(this));
  }

  @Override
  public MutateStmt visitMutate(MusicLanguageParser.MutateContext ctx) {
    return new MutateStmt((Name) ctx.varname().accept(this), (Mutation) ctx.mutation_type().accept(this));
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

  @Override
  public MutateBeat visitMutate_beat(MusicLanguageParser.Mutate_beatContext ctx) {
    return new MutateBeat(Double.parseDouble(ctx.MUT_BEAT_NUMBER().getText()));
  }

  @Override
  public MutateKey visitMutate_key(MusicLanguageParser.Mutate_keyContext ctx) {
    return new MutateKey(ctx.MUT_KEY_NUMBER().getText());
  }

  @Override
  public Property visitProperty(MusicLanguageParser.PropertyContext ctx) {
    if (ctx.SET_KEY() != null) {
      return new Property("SET_KEY");
    }
    if (ctx.SET_BEAT() != null) {
      return new Property("SET_BEAT");
    }
    if (ctx.SET_OCTAVE() != null) {
      return new Property("SET_OCTAVE");
    }
    if (ctx.SET_PITCH() != null) {
      return new Property("SET_PITCH");
    }
    return null;
  }

  @Override
  public Repeat visitRepeat(MusicLanguageParser.RepeatContext ctx) {
    List<MusicSheet> musicSheets = new ArrayList<>();
    int iterations = Integer.parseInt(ctx.REP_NUMBER().getText());

    for (int i = 0; i < iterations; i++) {
      MusicSheet ms = (MusicSheet) ctx.musicsheet().accept(this);
      musicSheets.add(ms);
    }

    return new Repeat(musicSheets);
  }
}
