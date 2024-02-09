package ast;

public interface MusicSheetVisitor<T, U> {

    U visit(MusicSheet p, T t);

    U visit(Note n, T t);

    U visit(Sequence s, T t);

    U visit(Chord c, T t);
}
