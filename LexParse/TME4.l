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

%%

"^"            {System.out.println("FIRST\n");
                return Parser.FIRST;}

"$"            {System.out.println("DOLLAR\n");
                return Parser.DOLLAR;}

"("            {System.out.println("LPAREN\n");
                return Parser.LPAREN;}

")"            {System.out.println("RPAREN\n");
                return Parser.RPAREN;}

"|"            {System.out.println("PIPE\n");
                return Parser.PIPE;}

"\\"           {System.out.println("BACKSLASH\n");
                return Parser.BACKSLASH;}

"["            {System.out.println("LBRACKET\n");
                return Parser.LBRACKET;}

"]"            {System.out.println("RBRACKET\n");
                return Parser.RBRACKET;}

"{"            {System.out.println("LBRACES\n");
                return Parser.LBRACES;}

"}"            {System.out.println("RBRACES\n");
                return Parser.RBRACES;}

"."            {System.out.println("POINT\n");
                return Parser.POINT;}

"*"            {System.out.println("STAR\n");
                return Parser.STAR;}

"+"            {System.out.println("PLUS\n");
                return Parser.PLUS;}

"?"            {System.out.println("QUESTIONMARK\n");
                return Parser.QUESTIONMARK;}

[[:digit:]]+   {yyParser.yylval = new ParserVal(Integer.parseInt(yytext()));
                return Parser.DIGIT;}

[a-zA-Z0-9_=]  {System.out.println("CHAR\n");
                yyParser.yylval = new ParserVal(yytext());
                return Parser.CHAR;}
