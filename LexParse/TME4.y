%{
package parserlexer;

import java.io.*;
import java.util.ArrayList;

import Automate.Automate;
import Automate.Etat;
import Automate.Factory;
%}

%token LPAREN RPAREN PIPE LBRACES RBRACES LBRACKET RBRACKET QUOTED_CHAR
%token POINT STAR PLUS QUESTIONMARK CHAR DOLLAR FIRST BACKSLASH DIGIT
%start  extended_reg_exp

%type <sval> CHAR QUOTED_CHAR ERE_dupl_symbol
%type <ival> DIGIT
%type <obj> bracket_expression matching_list nonmatching_list bracket_list
%type <obj> follow_list extended_reg_exp ERE_branch ERE_expression
%type <obj> one_char_or_coll_elem_ERE expression_term single_expression
%type <obj> range_expression start_range end_range
%%

/* --------------------------------------------
   Bracket Expression
   -------------------------------------------
*/
bracket_expression : LBRACKET matching_list RBRACKET   {
                        $$ = (ArrayList<Automate>) Factory.creerAutomatesMatchingList((ArrayList<String) $2); }
               | LBRACKET nonmatching_list RBRACKET    { $$ = null; }
               ;
matching_list  : bracket_list    { $$ = (ArrayList<String>) $1; }
               ;
nonmatching_list : FIRST bracket_list    { $$ = (ArrayList<String>) $2; }
               ;
bracket_list   : follow_list    {}
               | follow_list '-'    {}
               ;
follow_list    :             expression_term { $$ = (ArrayList<String>) $1; }
               | follow_list expression_term { $$ = ((ArrayList<String>) $1).addAll((ArrayList<String>) $2); }
               ;
expression_term : single_expression { $$ = (ArrayList<String>) $1; }
               | range_expression { $$ = (ArrayList<String>) $1; }
               ;
single_expression : end_range { $$ = (ArrayList<String>) $1; }
               ;
range_expression : start_range end_range {}
               | start_range '-' {}
               ;
start_range    : end_range '-' { $$ = (ArrayList<String>) $1; }
               ;
end_range      : CHAR { ArrayList<String> ls = new ArrayList<String>();
                        ls.add((String) $1);
                        $$ = (ArrayList<String>) ls;
               }
               ;

/* --------------------------------------------
   Extended Regular Expression
   --------------------------------------------
*/

extended_reg_exp   :                       ERE_branch    { auto = (Automate) $1; }
                   | extended_reg_exp PIPE ERE_branch    {
		     auto = (Automate) Factory.union((Automate) $1,
						     (Automate) $3);
                   }
                   ;
ERE_branch         :            ERE_expression    { $$ = (Automate) $1; }
                   | ERE_branch ERE_expression    {
		     $$ = (Automate) Factory.concatenation((Automate) $1,
							   (Automate) $2);
                   }
                   ;
ERE_expression     : one_char_or_coll_elem_ERE    { $$ = (Automate) $1; }
                   | FIRST                        { $$ = (Automate) Factory.creerAutomate(); }
                   | DOLLAR                       { $$ = (Automate) Factory.creerAutomate(); }
                   | RPAREN extended_reg_exp LPAREN    { $$ = (Automate) $2; }
                   | ERE_expression ERE_dupl_symbol    { $$ = (Automate) Factory.ERE_dupl_symbol((Automate) $1,
                                                                                                 (String) $2); }
                   ;
one_char_or_coll_elem_ERE  : CHAR          { $$ = (Automate) Factory.creerAutomate(new String($1)); }
                   | QUOTED_CHAR           {
                       if(((String) $1).equals("\\."))
                           $$ = (Automate) Factory.creerAutomate((String) $1);
                       else
                           $$ = (Automate) Factory.creerAutomate(new String($1.charAt(1)));
                   }
                   | POINT                 { $$ = (Automate) Factory.creerAutomate(new String(".")); }
                   | bracket_expression    { $$ = (Automate) $1; }
                   ;
ERE_dupl_symbol    : STAR                               { $$ = (String) new String("*"); }
                   | PLUS                               { $$ = (String) new String("+"); }
                   | QUESTIONMARK                       { $$ = (String) new String("?"); }
                   | LBRACES DIGIT           RBRACES    { $$ = (String) new String(""+$2); }
                   | LBRACES DIGIT ','       RBRACES    { $$ = (String) new String($2+",hk"); }
                   | LBRACES DIGIT ',' DIGIT RBRACES    { $$ = (String) new String($2+","+$4); }
                   ;

%%

private Yylex lexer;

static Automate auto;

public Automate getAutomate(){
	return auto;
}

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
  System.out.println("Automate");
  
  String txt = args[args.length - 1];
  
  Parser yyparser = null;
  if (args.length > 0) {
    // parse a file
    for (int i = 0; i < args.length - 1; i++) {
      
      System.out.println("Fichier " + args[i] + " : ");
      
      auto = null;
      
      yyparser = new Parser(new FileReader(args[i]));
      yyparser.yyparse();
      
      boolean result;
      
      result = Automate.accept(auto.getInitial(), txt, auto);
      
      System.out.println("R�sultat : " + result);
      
    }
  } else {
    // interactive mode
    System.out.println("No input file");
  }
  
  System.out.println();
  System.out.println("....... Fin .......");
}
