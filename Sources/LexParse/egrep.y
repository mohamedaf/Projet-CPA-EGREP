%{
package cpa.egrep.parserlexer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import Automate.Automate;
import Automate.Etat;
import Automate.Factory;
%}

%token LPAREN RPAREN PIPE LBRACES RBRACES LBRACKET RBRACKET QUOTED_CHAR
%token POINT STAR PLUS QUESTIONMARK CHAR DOLLAR FIRST DIGIT BACKSLASH
%start  extended_reg_exp

%type <sval> CHAR QUOTED_CHAR ERE_dupl_symbol QUESTIONMARK
%type <ival> DIGIT
%type <obj> bracket_expression matching_list nonmatching_list bracket_list
%type <obj> follow_list extended_reg_exp ERE_branch ERE_expression
%type <obj> one_char_or_coll_elem_ERE expression_term single_expression
%type <obj> range_expression start_range end_range others
%%

/* --------------------------------------------
   Bracket Expression
   -------------------------------------------
*/
bracket_expression : LBRACKET matching_list RBRACKET   {
                        $$ = Factory.creerAutomateFromMatchingList((HashSet<String>) $2);
                }
               | LBRACKET nonmatching_list RBRACKET    {
						$$ = Factory.creerAutomateFromMatchingList((HashSet<String>) $2);
               }
               ;
matching_list  : bracket_list    { $$ = (HashSet<String>) $1; }
               ;
nonmatching_list : FIRST bracket_list {
                    $$ = (HashSet<String>) Factory.getExtendedAsciiChar((HashSet<String>) $2);
               }
               ;
bracket_list   : follow_list    { $$ = (HashSet<String>) $1; }
               | follow_list '-'    { HashSet<String> res = new HashSet<String>((HashSet<String>) $1);
                       res.add(""+'-');
                       $$ = (HashSet<String>) res; }
               ;
follow_list    : expression_term { $$ = (HashSet<String>) $1; }
               | follow_list expression_term { HashSet<String> res = new HashSet<String>((HashSet<String>) $1);
												res.addAll((HashSet<String>) $2);
												$$ = res;
			   }
               ;
expression_term : single_expression { $$ = (HashSet<String>) $1; }
                | range_expression { $$ = (HashSet<String>) $1; }
               ;
single_expression : end_range { HashSet<String> res = new HashSet<String>();
								res.add("" + ((char) $1));
								$$ = res;
                    }
		    | others {HashSet<String> res = new HashSet<String>();
								res.add("" + ((char) $1));
								$$ = res;
		    }
               ;
range_expression : start_range end_range { $$ = Factory.getCharBetween((char) $1, (char) $2); }
               /*| start_range '-'         { $$ = Factory.getCharBetween((char) $1, (char) 126); }*/
               ;
start_range    : end_range '-' { $$ = (char) $1; }
               ;
end_range      : CHAR         { $$ = (char) $1.charAt(0); }
	       | DIGIT        { $$ = Character.toChars(48+$1)[0]; }
               ;
others         : '-' { $$ = '-'; }
               | BACKSLASH    { $$ = '\\'; }
               | FIRST        { $$ = '^'; }
               | DOLLAR       { $$ = '$'; }
               | STAR         { $$ = '*'; }
               | PLUS         { $$ = '+'; }
               | POINT        { $$ = '.'; }
               | QUESTIONMARK { $$ = '?'; }
               | LPAREN       { $$ = '('; }
               | RPAREN       { $$ = ')'; }
               | PIPE         { $$ = '|'; }
               | LBRACKET     { $$ = '['; }
               | LBRACES      { $$ = '{'; }
               | RBRACES      { $$ = '}'; }
               ;
/*FIRST DOLLAR LPAREN RPAREN PIPE BACKSLASH LBRACKET RBRACKET LBRACES RBRACES POINT STAR PLUS QUESTIONMARK*/

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
                   | FIRST                        { $$ = (Automate) Factory.creerAutomateDebutLigne(); }
                   | DOLLAR                       { $$ = (Automate) Factory.creerAutomateFinLigne(); }
                   | RPAREN extended_reg_exp LPAREN    { $$ = (Automate) $2; }
                   | ERE_expression ERE_dupl_symbol    { $$ = (Automate) Factory.ERE_dupl_symbol((Automate) $1,
                                                                                                 (String) $2); }
                   ;
one_char_or_coll_elem_ERE  : CHAR          { $$ = (Automate) Factory.creerAutomate(""+$1); }
                   | QUOTED_CHAR           { 
                                             $$ = (Automate) Factory.creerAutomate(""+$1.charAt(1)); }
                   | POINT                 { $$ = (Automate) Factory.creerAutomateFromMatchingList(Factory
				.getExtendedAsciiChar());
; }
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
	Parser yyparser = null;
	if (args.length > 0) {
		auto = null;
		/* On recupere la regex (premier argument) */
		File regex = textToTempFile(args[0]);
		yyparser = new Parser(new FileReader(regex));
		yyparser.yyparse();
		if (args.length > 1) {
			// parse a file
			for (int i = 1; i < args.length; i++) {
			readAndTestFile(args[i]);
			}
		} else {
			// interactive mode
			// No input file
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			readAndTestFile(s);
		}
	} else {
		System.err.println("Parser regex inputFile");
	}
}

public static void readAndTestFile(String s) {
	File f = null;
	FileReader fr = null;
	try {
		f = new File(s);
		fr = new FileReader(f);
	} catch (FileNotFoundException e) {
		System.err.println(s + " doesn't exist.");
		System.exit(1);
	}
	BufferedReader bf = new BufferedReader(fr);
		String line;
	try {
		while ((line = bf.readLine()) != null) {
			if (Automate.accept4(line, auto)) {
				System.out.println(line);
			}
		}
		bf.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static File textToTempFile(String txt) throws IOException {
	final File tempFile = File.createTempFile("temp", ".tmp");
	tempFile.deleteOnExit();
	try (BufferedWriter out = new BufferedWriter(new FileWriter(tempFile))) {
		out.write(txt);
		out.close();
	}
	return tempFile;
}
