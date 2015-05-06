package parserlexer;

%%

%public
%byaccj

%{
private Parser yyParser;

public Yylex(java.io.Reader r, Parser yyParser){
  this(r);
  this.yyParser = yyParser;
}
%}

/* Espace de declaration */

NL = \n | \r | \r\n

%%

{NL} {System.out.println("NL");}

"\\^" | "\\$" | "\\(" | "\\)" | "\\|" | "\\[" | "\\]" | "\\{" | "\\}" | "\\*" | "\\+" | "\\?" | "\\." | "\\\\" {

    yyParser.yylval = new ParserVal(yytext());
    return Parser.QUOTED_CHAR;}

"^"            {
                return Parser.FIRST;}

"$"            {
                return Parser.DOLLAR;}

"("            {
                return Parser.LPAREN;}

")"            {
                return Parser.RPAREN;}

"|"            {
                return Parser.PIPE;}

"["            {
                return Parser.LBRACKET;}

"]"            {
                return Parser.RBRACKET;}

"{"            {
                return Parser.LBRACES;}

"}"            {
                return Parser.RBRACES;}

"."            {
                return Parser.POINT;}

"*"            {
                return Parser.STAR;}

"+"            {
                return Parser.PLUS;}

"?"            {
                return Parser.QUESTIONMARK;}

"\\"           {
                return Parser.BACKSLASH;}

"-"	| ","	   { return (int) yycharat(0); }



[[:digit:]]+   {
				yyParser.yylval = new ParserVal(Integer.parseInt(yytext()));
                return Parser.DIGIT;}

[a-zA-Z0-9_=]  {
                yyParser.yylval = new ParserVal(yytext());
                return Parser.CHAR;}
