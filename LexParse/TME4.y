%{
package parserlexer;

import java.io.*;
import java.util.ArrayList;

import Automate.Automate;
import Automate.Etat;
import Automate.Factory;
%}

%token LPAREN RPAREN PIPE LBRACES RBRACES LBRACKET RBRACKET
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
bracket_expression : LBRACKET matching_list RBRACKET   { $$ = null; }
               | LBRACKET nonmatching_list RBRACKET    { $$ = null; }
               ;
matching_list  : bracket_list    { $$ = null; }
               ;
nonmatching_list : FIRST bracket_list    {}
               ;
bracket_list   : follow_list    {}
               | follow_list '-'    {}
               ;
follow_list    :             expression_term {}
               | follow_list expression_term {}
               ;
expression_term : single_expression {}
               | range_expression {}
               ;
single_expression : end_range {}
               ;
range_expression : start_range end_range {}
               | start_range '-' {}
               ;
start_range    : end_range '-' {}
               ;
end_range      : CHAR {}
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
                   | ERE_expression ERE_dupl_symbol    {
		     String s = (String)$2;
		     Automate tmp = (Automate) $1;
		     Automate tmp2 = tmp.clone();
		     switch(s){
		     case "*": $$ = (Automate) Factory.Star((Automate) $1);
		       break;
		     case "+": $$ = (Automate) Factory.Plus((Automate) $1);
		       break;
		     case "?": $$ = (Automate) Factory.questionMark((Automate) $1);
		       break;
		     default:
		       String tab[] = s.split(",");
		       if(tab.length == 1){
			   System.out.println("Repetitions : "+tab[0]);
			 /* Exactement n repetitions */
			 for(int i=1; i<Integer.parseInt(tab[0]); i++){
			   tmp = Factory.concatenation(tmp, tmp2);
			 }
			 $$ = tmp;
		       }
		       else if(tab[1].equals("hk")){
			 /* Au moins n repetitions, donc n-1 concatenation et
			    transformation Plus pour dernier automate pour
			    pouvoir repeter */
			 for(int i=1; i<Integer.parseInt(tab[0]); i++){
			   if(i == (Integer.parseInt(tab[0])-1)){
			     tmp = Factory.concatenation(tmp, Factory.Plus(tmp2));
			   }
			   else{
			     tmp = Factory.concatenation(tmp, tmp2);
			   }
			 }
			 $$ = tmp;
		       }
		       else{
			 int i;

			 ArrayList<Etat> finaux = new ArrayList<Etat>();
			 Etat f = Factory.creerEtat();

			 for(i=1; i<Integer.parseInt(tab[0]); i++){
			   tmp = Factory.concatenation(tmp, tmp2);
			 }

			 /* apres le min on peux s'arreter donc on ajoute
								   un etat final et des transitions */
			 for(; i<Integer.parseInt(tab[1]); i++){
			   if(i<(Integer.parseInt(tab[1])-1)){
			     finaux.addAll(tmp.getFinauxList());
			   }
			   tmp = Factory.concatenation(tmp, tmp2);
			 }

			 /* Ajouer une epsilon transition allant des anciens
			    etats finaux au nouvel etat final */
			 for(Etat e : finaux){
			   e.addTransition(Factory.creerTransition(e, f, new String("eps")));
			 }

			 tmp.addEtatFinal(f);

			 $$ = tmp;

		       }

		       break;
		     }
                   }
                   ;
one_char_or_coll_elem_ERE  : CHAR          { $$ = (Automate) Factory.creerAutomate(new String($1)); }
                   | QUOTED_CHAR           { $$ = (Automate) Factory.creerAutomate(new String($1)); }
                   | POINT                 { $$ = (Automate) Factory.creerAutomate(new String(".")); }
                   | bracket_expression    { $$ - (Automate) $1; }
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
  
  ArrayList<String> lettres = new ArrayList<String>();
  
  for (int j = 0; j < txt.length(); j++)
    lettres.add("" + txt.charAt(j));
  
  Parser yyparser = null;
  if (args.length > 0) {
    // parse a file
    for (int i = 0; i < args.length - 1; i++) {
      
      System.out.println("Fichier " + args[i] + " : ");
      
      auto = null;
      
      yyparser = new Parser(new FileReader(args[i]));
      yyparser.yyparse();
      
      boolean result;
      
      result = Automate.accept(auto.getInitial(), lettres, auto);
      
      System.out.println("Résultat : " + result);
      
    }
  } else {
    // interactive mode
    System.out.println("No input file");
  }
  
  System.out.println();
  System.out.println("....... Fin .......");
}
