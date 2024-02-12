package ast;

import parser.MusicLanguageParserVisitor;

public abstract class Node {
    abstract public <T, U> U accept(MusicSheetVisitor<T, U> v, T t); // so that we remember to define this in all subclasses
}
