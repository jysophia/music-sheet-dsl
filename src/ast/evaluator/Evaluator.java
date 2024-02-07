package ast.evaluator;

import ast.MusicSheet;
import ast.MusicSheetVisitor;
import ast.Note;
import ast.Sequence;

public class Evaluator implements MusicSheetVisitor<StringBuilder, Integer> {

    public Evaluator() {

    }

    @Override
    public Integer visit(StringBuilder errors, MusicSheet m) {
        return 0;
    }

    @Override
    public Integer visit(StringBuilder errors, Note n) {
        return 0;
    }

    @Override
    public Integer visit(StringBuilder errors, Sequence s) {
        return 0;
    }
}
