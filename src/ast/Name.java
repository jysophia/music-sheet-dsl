package ast;

public class Name extends Node {
  private final String text;

  public Name(String text) {
    this.text = text;
  }

  public String getName() {
    return text;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return null;
  }
}
