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

  @Override Set visitSet(MusicLanguageParser.SetContext ctx) {
    Name name = (Name) ctx.varname().accept(this);
    // return new Set()
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


}
