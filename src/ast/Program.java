package ast;

public class Program extends Node {
  private final MusicSheet musicSheet;

  public Program(MusicSheet musicSheet) {
    this.musicSheet = musicSheet;
  }

    public MusicSheet getMusicSheet() {
        return musicSheet;
    }

    @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
