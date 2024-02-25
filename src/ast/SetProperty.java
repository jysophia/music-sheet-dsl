package ast;

public class SetProperty extends Statement {
  private final Name name;
  private final Property property;
  private final Operation operation;

  public SetProperty(Name name, Property property, Operation operation) {
    this.name = name;
    this.property = property;
    this.operation = operation;
  }

  public Name getName() {
    return name;
  }

  public Property getProperty() {
    return property;
  }

  public Operation getOperation() {
    return operation;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
