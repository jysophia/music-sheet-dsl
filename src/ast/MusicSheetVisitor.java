package ast;

public interface MusicSheetVisitor<T, U> {

    U visit(MusicSheet m, T t);

    U visit(Note n, T t);

    U visit(Sequence s, T t);

    U visit(Chord c, T t);

    U visit(DeclareAndSet d, T t);

    U visit(Display d, T t);

    U visit(Name n, T t);

    U visit(NoteProperty n, T t);

    U visit(SetProperty s, T t);

    U visit(MutateStmt m, T t);

    U visit(MutateBeat mb, T t);

    U visit(MutateKey mk, T t);

    U visit(Property p, T t);

    U visit(Program p, T t);

    U visit(Repeat r, T t);
}
