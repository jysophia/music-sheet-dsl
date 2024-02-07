package ast;

import parser.MusicLanguageParserVisitor;

public abstract class Node {
    abstract public <C,T> T accept(C context, MusicLanguageParserVisitor<T> v); // so that we remember to define this in all subclasses
}
