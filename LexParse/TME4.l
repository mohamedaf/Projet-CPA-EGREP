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

"^"            {System.out.println("FIRST");
                return Parser.FIRST;}

"$"            {System.out.println("DOLLAR");
                return Parser.DOLLAR;}

"("            {System.out.println("LPAREN");
                return Parser.LPAREN;}

")"            {System.out.println("RPAREN");
                return Parser.RPAREN;}

"|"            {System.out.println("PIPE");
                return Parser.PIPE;}

"\\"           {System.out.println("BACKSLASH");
                return Parser.BACKSLASH;}

"["            {System.out.println("LBRACKET");
                return Parser.LBRACKET;}

"]"            {System.out.println("RBRACKET");
                return Parser.RBRACKET;}

"{"            {System.out.println("LBRACES");
                return Parser.LBRACES;}

"}"            {System.out.println("RBRACES");
                return Parser.RBRACES;}

"."            {System.out.println("POINT");
                return Parser.POINT;}

"*"            {System.out.println("STAR");
                return Parser.STAR;}

"+"            {System.out.println("PLUS");
                return Parser.PLUS;}

"?"            {System.out.println("QUESTIONMARK");
                return Parser.QUESTIONMARK;}

"\\^" | "\\$" | "\\(" | "\\)" | "\\|" | "\\\\" | "\\[" | "\\]" | "\\{" | "\\}" | "\\*" | "\\+" | "\\?" {
    yyparser.yylval = new ParserVal(yytext());
    return Parser.QUOTED_CHAR;}

[[:digit:]]+   {System.out.println("DIGIT");
				yyParser.yylval = new ParserVal(Integer.parseInt(yytext()));
                return Parser.DIGIT;}

[a-zA-Z0-9_=]  {System.out.println("CHAR");
                yyParser.yylval = new ParserVal(yytext());
                return Parser.CHAR;}