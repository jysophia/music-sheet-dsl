package ast;

public class Display extends Statement {
  private final Name name;

  public Display(Name name) {
    this.name = name;
  }

  public Name getName() {
    return name;
  }
  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
