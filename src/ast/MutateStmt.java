package ast;

public class MutateStmt extends Statement {
  private final Name varname;
  private final Mutation mutation;
  public MutateStmt(Name varname, Mutation mutation) {
    this.varname = varname;
    this.mutation = mutation;
  }

  public Name getVarname() {
    return varname;
  }

  public Mutation getMutation() {
    return mutation;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
