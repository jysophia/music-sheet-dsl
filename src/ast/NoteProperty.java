package ast;

public class NoteProperty extends Operation {
  private final String property;

  public NoteProperty(String property) {
    this.property = property;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
