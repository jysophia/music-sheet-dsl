package ast;

public class MutateKey extends Mutation {
  private final String newkey;

  public MutateKey(String newkey) {
    this.newkey = newkey;
  }

  public String getNewkey() {
    return newkey;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
