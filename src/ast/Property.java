package ast;

public class Property extends Node {
  private final String settingProperty;

  public Property(String settingProperty) {
    this.settingProperty = settingProperty;
  }

  public String getProperty() {
    return settingProperty;
  }

  @Override
  public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
    return v.visit(this, t);
  }
}
