package ast;

public interface MusicSheetVisitor<C, T> {

    T visit(C context, MusicSheet p);

    T visit(C context, Note n);

    T visit(C context, Sequence s);

    T visit(C context, Chord c);
}
