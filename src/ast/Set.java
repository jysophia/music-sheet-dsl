package ast;

import ast.Statement;

public class Set extends Statement {
  private final Name name;
  private final NoteProperty noteProperty;
  private final Operation operation;

  public Set(Name name, NoteProperty noteProperty, Operation operation) {
    this.name = name;
    this.noteProperty = noteProperty;
    this.operation = operation;
  }

  public Name getName() {
    return name;
  }

  public NoteProperty getNoteProperty() {
    return noteProperty;
  }

  public Operation getOperation() {
    return operation;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
