package ast;

import java.util.ArrayList;
import java.util.List;

public class MusicSheet extends Node{
    private final List<Statement> statements;

    public MusicSheet(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public <T, U> U accept(MusicSheetVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
