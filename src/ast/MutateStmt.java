package ast;

public class MutateStmt extends Statement {
  private final Name varname;
  private final Mutation mutation;
  private final String type;
  public MutateStmt(Name varname, Mutation mutation, String type) {
    this.varname = varname;
    this.mutation = mutation;
    this.type = type;
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
