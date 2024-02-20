package ast;

public class Declare extends Statement {
  private final String declType;
  private final Name name;

  public Declare(String declType, Name name) {
    this.declType = declType;
    this.name = name;
  }

  public String getDeclType() {
    return declType;
  }

  public Name getName() {
    return name;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
