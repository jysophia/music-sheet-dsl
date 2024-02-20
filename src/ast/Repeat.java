package ast;

import java.util.List;

public class Repeat extends Statement {
  private final List<MusicSheet> musicSheets;

  public Repeat(List<MusicSheet> musicSheets) {
    this.musicSheets = musicSheets;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
