package ast;

public class MusicSheet extends Node{

    @Override
    public <C,T> T accept(C context, MusicSheetVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
