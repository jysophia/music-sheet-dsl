package ast;

public class DeclareAndSet extends Statement {
  private final String declType;
  private final Name name;
  private final Operation operation;

  public DeclareAndSet(String declType, Name name, Operation operation) {
    this.declType = declType;
    this.name = name;
    this.operation = operation;
  }

  public String getDeclType() {
    return declType;
  }

  public Name getName() {
    return name;
  }
  public Operation getOperation() {
    return operation;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
