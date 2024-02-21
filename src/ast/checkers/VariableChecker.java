package ast.checkers;

import ast.*;
import java.util.Set;
import java.util.HashSet;

public class VariableChecker implements MusicSheetVisitor<Set<String>, String> {
    //Adapted from the lecture for static checking

    public String checkProgram(Program p) {
        Set<String> vars = new HashSet<>();
        return p.accept(this, vars);
    }
    @Override
    public String visit(MusicSheet m, Set<String> strings) {
        StringBuilder errors = new StringBuilder();
        for (Statement s : m.getStatements()) {
            errors.append(s.accept(this, strings));
        }
        return errors.toString();
    }

    @Override
    public String visit(Note n, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(Sequence s, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(Chord c, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(Declare d, Set<String> strings) {
        strings.add(d.getName().getName());
        return "";
    }

    @Override
    public String visit(Display d, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(Name n, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(NoteProperty n, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(ast.Set s, Set<String> strings) {
        String name = s.getName().getName();
        if(!strings.contains(name)) {
            return "Error: variable " + name + "assigned but not declared.\n";
        }
        return "";
    }

    @Override
    public String visit(MutateStmt m, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(MutateBeat mb, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(MutateKey mk, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(Property p, Set<String> strings) {
        return "";
    }

    @Override
    public String visit(Program p, Set<String> strings) {
        StringBuilder errors = new StringBuilder();
        MusicSheet m = p.getMusicSheet();
        errors.append(m.accept(this, strings));
        return errors.toString();
    }

    @Override
    public String visit(Repeat r, Set<String> strings) {
        StringBuilder errors = new StringBuilder();
        for (MusicSheet m : r.getMusicSheets()) {
            errors.append(m.accept(this, strings));
        }
        return errors.toString();
    }
}
