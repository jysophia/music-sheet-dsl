package ast;

public class MutateBeat extends Mutation {
  private final Double newbeat;

  public MutateBeat(Double newbeat) {
    this.newbeat = newbeat;
  }

  public Double getNewbeat() {
    return newbeat;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
