%{
import java.io.*;
import java.util.ArrayList;
%}

%token LPAREN RPAREN PIPE LBRACES RBRACES LBRACKET RBRACKET
%token POINT STAR PLUS QUESTIONMARK CHAR DOLLAR FIRST BACKSLASH DIGIT
%start  extended_reg_exp

%type <sval> one_char_or_coll_elem_ERE QUOTED_CHAR
%type <obj> bracket_expression matching_list nonmatching_list bracket_list
%type <obj> follow_list extended_reg_exp ERE_branch ERE_expression ERE_dupl_symbol
%%

/* --------------------------------------------
   Bracket Expression
   -------------------------------------------
*/
bracket_expression : LBRACKET matching_list RBRACKET    {}
               | LBRACKET nonmatching_list RBRACKET    {}
               ;
matching_list  : bracket_list    {}
               ;
nonmatching_list : FIRST bracket_list    {}
               ;
bracket_list   : follow_list    {}
               | follow_list '-'    {}
               ;
follow_list    :             CHAR    {}
               | follow_list CHAR    {}
               ;

/* --------------------------------------------
   Extended Regular Expression
   --------------------------------------------
*/

extended_reg_exp   :                       ERE_branch    {}
                   | extended_reg_exp PIPE ERE_branch    {}
                   ;
ERE_branch         :            ERE_expression    {}
                   | ERE_branch ERE_expression    {}
                   ;
ERE_expression     : one_char_or_coll_elem_ERE    {}
                   | FIRST    {}
                   | DOLLAR    {}
                   | RPAREN extended_reg_exp LPAREN    {}
                   | ERE_expression ERE_dupl_symbol    {}
                   ;
one_char_or_coll_elem_ERE  : CHAR    {}
                   | QUOTED_CHAR    {}
                   | POINT    {}
                   | bracket_expression    {}
                   ;
QUOTED_CHAR        : BACKSLASH FIRST    {}
                   | BACKSLASH POINT    {}
                   | BACKSLASH STAR    {}
                   | BACKSLASH LBRACKET    {}
                   | BACKSLASH DOLLAR    {}
                   | BACKSLASH LPAREN    {}
                   | BACKSLASH RPAREN    {}
                   | BACKSLASH PIPE    {}
                   | BACKSLASH PLUS    {}
                   | BACKSLASH QUESTIONMARK    {}
                   | BACKSLASH LBRACES    {}
                   | BACKSLASH BACKSLASH    {}
                   ;
ERE_dupl_symbol    : STAR    { $$ = (String) new String("*"); }
                   | PLUS    { $$ = (String) new String("+"); }
                   | QUESTIONMARK    { $$ = (String) new String("?"); }
                   | LBRACES DIGIT           RBRACES    {}
                   | LBRACES DIGIT ','       RBRACES    {}
                   | LBRACES DIGIT ',' DIGIT RBRACES    {}
                   ;

%%

private Yylex lexer;


private int yylex () {
    int yyl_return = -1;
    try {
	yylval = new ParserVal(0);
	yyl_return = lexer.yylex();
    }
    catch (IOException e) {
	System.err.println("IO error :"+e);
    }
    return yyl_return;
}


public void yyerror (String error) {
    System.err.println ("Error: " + error);
}


public Parser(Reader r) {
    lexer = new Yylex(r, this);
}


static boolean interactive;

public static void main(String args[]) throws IOException {
    System.out.println("Traducteur BOPL Prolog");

    Parser yyparser = null;
    if ( args.length > 0 ) {
	// parse a file
	for(int i=0; i<args.length; i++){
            yyparser = new Parser(new FileReader(args[i]));
            yyparser.yyparse();
      }
    }
    else {
	// interactive mode
	System.out.println("No input file");
    }

    System.out.println();
    System.out.println("....... Fin .......");
}
