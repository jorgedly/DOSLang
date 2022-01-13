package servidor.analizadores;

import java_cup.runtime.*;
import java.io.Reader;
import servidor.compilador.Constantes;
import servidor.compilador.Control;
import servidor.compilador.Ubicacion;

%%

%{
	StringBuilder string = new StringBuilder();
	public String archivo;
	public Scanner(Reader in, String archivo) {
        this.zzReader = in;
        this.archivo = archivo;
    }
%}

%public
%class Scanner
%cupsym Simbolos
%cup
%char
%column
%full
%line
%unicode
%ignorecase

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]
comment = "{" [^}]* "}"

Identificador = ([A-Za-z]|\_[A-Za-z])[A-Za-z0-9\_]*
Decimal = [0-9]+ \. [0-9]+
Entero = [0-9]+
Oct = [0-7]

StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state STRING, CHARLITERAL, MLC

%%

<YYINITIAL> {
"else if"               { return new Symbol(Simbolos.tElseif, yycolumn, yyline, yytext()); }
"integer"               { return new Symbol(Simbolos.tInteger, yycolumn, yyline, yytext()); }
"real"              	{ return new Symbol(Simbolos.tReal, yycolumn, yyline, yytext()); }
"char"              	{ return new Symbol(Simbolos.tChar, yycolumn, yyline, yytext()); }
"boolean"               { return new Symbol(Simbolos.tBoolean, yycolumn, yyline, yytext()); }
"word"              	{ return new Symbol(Simbolos.tWord, yycolumn, yyline, yytext()); }
"string"                { return new Symbol(Simbolos.tString, yycolumn, yyline, yytext()); }
"nil"             		{ return new Symbol(Simbolos.tNil, yycolumn, yyline, yytext()); }
"type"              	{ return new Symbol(Simbolos.tType, yycolumn, yyline, yytext()); }
"var"             		{ return new Symbol(Simbolos.tVar, yycolumn, yyline, yytext()); }
"const"             	{ return new Symbol(Simbolos.tConst, yycolumn, yyline, yytext()); }
"array"             	{ return new Symbol(Simbolos.tArray, yycolumn, yyline, yytext()); }
"of"              		{ return new Symbol(Simbolos.tOf, yycolumn, yyline, yytext()); }
"begin"             	{ return new Symbol(Simbolos.tBegin, yycolumn, yyline, yytext()); }
"end"             		{ return new Symbol(Simbolos.tEnd, yycolumn, yyline, yytext()); }
"for"             		{ return new Symbol(Simbolos.tFor, yycolumn, yyline, yytext()); }
"to"              		{ return new Symbol(Simbolos.tTo, yycolumn, yyline, yytext()); }
"do"              		{ return new Symbol(Simbolos.tDo, yycolumn, yyline, yytext()); }
"program"               { return new Symbol(Simbolos.tProgram, yycolumn, yyline, yytext()); }
"write"             	{ return new Symbol(Simbolos.tWrite, yycolumn, yyline, yytext()); }
"writeln"               { return new Symbol(Simbolos.tWriteln, yycolumn, yyline, yytext()); }
"record"                { return new Symbol(Simbolos.tRecord, yycolumn, yyline, yytext()); }
"with"              	{ return new Symbol(Simbolos.tWith, yycolumn, yyline, yytext()); }
"malloc"                { return new Symbol(Simbolos.tMalloc, yycolumn, yyline, yytext()); }
"sizeof"                { return new Symbol(Simbolos.tSizeof, yycolumn, yyline, yytext()); }
"free"              	{ return new Symbol(Simbolos.tFree, yycolumn, yyline, yytext()); }
"true"              	{ return new Symbol(Simbolos.tTrue, yycolumn, yyline, yytext()); }
"false"             	{ return new Symbol(Simbolos.tFalse, yycolumn, yyline, yytext()); }
"and"              		{ return new Symbol(Simbolos.tAnd, yycolumn, yyline, yytext()); }
"or"              		{ return new Symbol(Simbolos.tOr, yycolumn, yyline, yytext()); }
"repeat"                { return new Symbol(Simbolos.tRepeat, yycolumn, yyline, yytext()); }
"until"             	{ return new Symbol(Simbolos.tUntil, yycolumn, yyline, yytext()); }
"downto"                { return new Symbol(Simbolos.tDownto, yycolumn, yyline, yytext()); }
"function"              { return new Symbol(Simbolos.tFunction, yycolumn, yyline, yytext()); }
"var"             		{ return new Symbol(Simbolos.tVar, yycolumn, yyline, yytext()); }
"case"              	{ return new Symbol(Simbolos.tCase, yycolumn, yyline, yytext()); }
"default"               { return new Symbol(Simbolos.tDefault, yycolumn, yyline, yytext()); }
"else"              	{ return new Symbol(Simbolos.tElse, yycolumn, yyline, yytext()); }
"procedure"             { return new Symbol(Simbolos.tProcedure, yycolumn, yyline, yytext()); }
"then"              	{ return new Symbol(Simbolos.tThen, yycolumn, yyline, yytext()); }
"while"             	{ return new Symbol(Simbolos.tWhile, yycolumn, yyline, yytext()); }
"if"              		{ return new Symbol(Simbolos.tIf, yycolumn, yyline, yytext()); }
"uses"              	{ return new Symbol(Simbolos.tUses, yycolumn, yyline, yytext()); }
"nand"              	{ return new Symbol(Simbolos.tNand, yycolumn, yyline, yytext()); }
"not"             		{ return new Symbol(Simbolos.tNot, yycolumn, yyline, yytext()); }
"nor"             		{ return new Symbol(Simbolos.tNor, yycolumn, yyline, yytext()); }
"charat"                { return new Symbol(Simbolos.tCharat, yycolumn, yyline, yytext()); }
"length"                { return new Symbol(Simbolos.tLength, yycolumn, yyline, yytext()); }
"replace"               { return new Symbol(Simbolos.tReplace, yycolumn, yyline, yytext()); }
"tochararray"           { return new Symbol(Simbolos.tTochararray, yycolumn, yyline, yytext()); }
"tolowercase"           { return new Symbol(Simbolos.tTolowercase, yycolumn, yyline, yytext()); }
"touppercase"           { return new Symbol(Simbolos.tTouppercase, yycolumn, yyline, yytext()); }
"equals"                { return new Symbol(Simbolos.tEquals, yycolumn, yyline, yytext()); }
"trunk"             	{ return new Symbol(Simbolos.tTrunk, yycolumn, yyline, yytext()); }
"round"             	{ return new Symbol(Simbolos.tRound, yycolumn, yyline, yytext()); }
"break"             	{ return new Symbol(Simbolos.tBreak, yycolumn, yyline, yytext()); }
"continue"              { return new Symbol(Simbolos.tContinue, yycolumn, yyline, yytext()); }
"exit"              	{ return new Symbol(Simbolos.tExit, yycolumn, yyline, yytext()); }
"read"              	{ return new Symbol(Simbolos.tRead, yycolumn, yyline, yytext()); }

":="					{ return new Symbol(Simbolos.tAsignacion); }
"="						{ return new Symbol(Simbolos.tIgual); }
"("						{ return new Symbol(Simbolos.tIpar); }
")"						{ return new Symbol(Simbolos.tDpar); }
"["						{ return new Symbol(Simbolos.tIcor); }
"]"						{ return new Symbol(Simbolos.tDcor); }
"..."					{ return new Symbol(Simbolos.tTresp); }
".."					{ return new Symbol(Simbolos.tDosp); }
","						{ return new Symbol(Simbolos.tComa); }
"."						{ return new Symbol(Simbolos.tPunto); }
";"						{ return new Symbol(Simbolos.tPuntocoma); }
":"						{ return new Symbol(Simbolos.tDospuntos); }
"+"						{ return new Symbol(Simbolos.tSuma); }
"-"						{ return new Symbol(Simbolos.tResta); }
"*"						{ return new Symbol(Simbolos.tMultiplicacion); }
"/"						{ return new Symbol(Simbolos.tDivision); }
"^"						{ return new Symbol(Simbolos.tPotencia); }
"%"						{ return new Symbol(Simbolos.tModulo); }
"<>"					{ return new Symbol(Simbolos.tDistinto); }
">="					{ return new Symbol(Simbolos.tMayorigual); }
"<="					{ return new Symbol(Simbolos.tMenorigual); }
">"						{ return new Symbol(Simbolos.tMayor); }
"<"						{ return new Symbol(Simbolos.tMenor); }

\"                 		{ yybegin(STRING); string.setLength(0); }
\'                  	{ yybegin(CHARLITERAL); }
"(*"                    { yybegin(MLC); }
{comment}               {  }

{Decimal}         		{ return new Symbol(Simbolos.nDecimal, yycolumn, yyline, yytext()); }
{Entero}           	    { return new Symbol(Simbolos.nEntero, yycolumn, yyline, yytext()); }

{WhiteSpace}       		{ /* ignore */ }

{Identificador}       	{ return new Symbol(Simbolos.nIdentificador, yycolumn, yyline, yytext()); }
"¼"                 	{ return new Symbol(Simbolos.tToken); }

}

<STRING> {
	\"                             { yybegin(YYINITIAL); return new Symbol(Simbolos.nCadena, yycolumn, yyline, string.toString()); }

	{StringCharacter}+             { string.append( yytext() ); }

	/* escape sequences */
	"\\b"                          { string.append( '\b' ); }
	"\\t"                          { string.append( '\t' ); }
	"\\n"                          { string.append( '\n' ); }
	"\\f"                          { string.append( '\f' ); }
	"\\r"                          { string.append( '\r' ); }
	"\\\""                         { string.append( '\"' ); }
	"\\'"                          { string.append( '\'' ); }
	"\\\\"                         { string.append( '\\' ); }
	\\[0-3]?{Oct}?{Oct}  		   { char val = (char) Integer.parseInt(yytext().substring(1),8); string.append( val ); }

	\\.                            { Control.error(Constantes.LEXICO, new Ubicacion(yyline+1,yycolumn+1,archivo), "Secuencia ilegal de escape: " + yytext()); }
    {LineTerminator}               { Control.error(Constantes.LEXICO, new Ubicacion(yyline+1,yycolumn+1,archivo), "Literal cadena sin finalizar: " + yytext()); }
}

<CHARLITERAL> {
    {SingleCharacter}\'            { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, yytext().charAt(0)); }

    /* escape sequences */
    "\\b"\'                        { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\b');}
    "\\t"\'                        { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\t');}
    "\\n"\'                        { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\n');}
    "\\f"\'                        { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\f');}
    "\\r"\'                        { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\r');}
    "\\\""\'                       { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\"');}
    "\\'"\'                        { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\'');}
    "\\\\"\'                       { yybegin(YYINITIAL); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, '\\'); }
    \\[0-3]?{Oct}?{Oct}\' 		   { yybegin(YYINITIAL); int val = Integer.parseInt(yytext().substring(1,yylength()-1),8); return new Symbol(Simbolos.nCaracter, yycolumn, yyline, (char)val + ""); }

    \\.                            { Control.error(Constantes.LEXICO, new Ubicacion(yyline+1,yycolumn+1,archivo), "Secuencia ilegal de escape: " + yytext()); }
    {LineTerminator}               { Control.error(Constantes.LEXICO, new Ubicacion(yyline+1,yycolumn+1,archivo), "Literal caracter sin finalizar: " + yytext()); }
}

<MLC> {
    "*)"                { yybegin(YYINITIAL); }
    [^*\n]+             {  }
    "*"                 {  }
    \n                  {  }
}

.  { Control.error(Constantes.LEXICO, new Ubicacion(yyline+1,yycolumn+1,archivo), "Caracter inválido: " + yytext()); }
