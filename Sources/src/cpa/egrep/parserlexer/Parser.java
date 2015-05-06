//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";

//#line 2 "TME4.y"
package cpa.egrep.parserlexer;

import java.io.*;
import java.util.HashSet;

import cpa.egrep.automate.Automate;
import cpa.egrep.automate.Factory;

//#line 27 "Parser.java"

public class Parser {

	boolean yydebug; // do I want debug output?
	int yynerrs; // number of errors so far
	int yyerrflag; // was there an error?
	int yychar; // the current working character

	// ########## MESSAGES ##########
	// ###############################################################
	// method: debug
	// ###############################################################
	void debug(String msg) {
		if (yydebug)
			System.out.println(msg);
	}

	// ########## STATE STACK ##########
	final static int YYSTACKSIZE = 500; // maximum stack size
	int statestk[] = new int[YYSTACKSIZE]; // state stack
	int stateptr;
	int stateptrmax; // highest index of stackptr
	int statemax; // state when highest index reached

	// ###############################################################
	// methods: state stack push,pop,drop,peek
	// ###############################################################

	final void state_push(int state) {
		try {
			stateptr++;
			statestk[stateptr] = state;
		} catch (ArrayIndexOutOfBoundsException e) {
			int oldsize = statestk.length;
			int newsize = oldsize * 2;
			int[] newstack = new int[newsize];
			System.arraycopy(statestk, 0, newstack, 0, oldsize);
			statestk = newstack;
			statestk[stateptr] = state;
		}
	}

	final int state_pop() {
		return statestk[stateptr--];
	}

	final void state_drop(int cnt) {
		stateptr -= cnt;
	}

	final int state_peek(int relative) {
		return statestk[stateptr - relative];
	}

	// ###############################################################
	// method: init_stacks : allocate and prepare stacks
	// ###############################################################
	final boolean init_stacks() {
		stateptr = -1;
		val_init();
		return true;
	}

	// ###############################################################
	// method: dump_stacks : show n levels of the stacks
	// ###############################################################
	void dump_stacks(int count) {
		int i;
		System.out.println("=index==state====value=     s:" + stateptr + "  v:"
				+ valptr);
		for (i = 0; i < count; i++)
			System.out.println(" " + i + "    " + statestk[i] + "      "
					+ valstk[i]);
		System.out.println("======================");
	}

	// ########## SEMANTIC VALUES ##########
	// public class ParserVal is defined in ParserVal.java

	String yytext;// user variable to return contextual strings
	ParserVal yyval; // used to return semantic vals from action routines
	ParserVal yylval;// the 'lval' (result) I got from yylex()
	ParserVal valstk[];
	int valptr;

	// ###############################################################
	// methods: value stack push,pop,drop,peek.
	// ###############################################################
	void val_init() {
		valstk = new ParserVal[YYSTACKSIZE];
		yyval = new ParserVal();
		yylval = new ParserVal();
		valptr = -1;
	}

	void val_push(ParserVal val) {
		if (valptr >= YYSTACKSIZE)
			return;
		valstk[++valptr] = val;
	}

	ParserVal val_pop() {
		if (valptr < 0)
			return new ParserVal();
		return valstk[valptr--];
	}

	void val_drop(int cnt) {
		int ptr;
		ptr = valptr - cnt;
		if (ptr < 0)
			return;
		valptr = ptr;
	}

	ParserVal val_peek(int relative) {
		int ptr;
		ptr = valptr - relative;
		if (ptr < 0)
			return new ParserVal();
		return valstk[ptr];
	}

	final ParserVal dup_yyval(ParserVal val) {
		ParserVal dup = new ParserVal();
		dup.ival = val.ival;
		dup.dval = val.dval;
		dup.sval = val.sval;
		dup.obj = val.obj;
		return dup;
	}

	// #### end semantic value section ####
	public final static short LPAREN = 257;
	public final static short RPAREN = 258;
	public final static short PIPE = 259;
	public final static short LBRACES = 260;
	public final static short RBRACES = 261;
	public final static short LBRACKET = 262;
	public final static short RBRACKET = 263;
	public final static short QUOTED_CHAR = 264;
	public final static short POINT = 265;
	public final static short STAR = 266;
	public final static short PLUS = 267;
	public final static short QUESTIONMARK = 268;
	public final static short CHAR = 269;
	public final static short DOLLAR = 270;
	public final static short FIRST = 271;
	public final static short DIGIT = 272;
	public final static short BACKSLASH = 273;
	public final static short YYERRCODE = 256;
	final static short yylhs[] = { -1, 2, 2, 3, 4, 5, 5, 6, 6, 10, 10, 11, 11,
			12, 13, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
			15, 0, 0, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 1, 1, 1, 1, 1, 1, };
	final static short yylen[] = { 2, 3, 3, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 2, 1, 1,
			1, 3, 2, 1, 1, 1, 1, 1, 1, 1, 3, 4, 5, };
	final static short yydefred[] = { 0, 0, 0, 41, 42, 40, 37, 36, 0, 43, 0, 0,
			35, 0, 25, 26, 27, 29, 30, 28, 23, 21, 22, 24, 15, 20, 0, 16, 18,
			17, 0, 0, 3, 0, 7, 9, 10, 0, 0, 12, 0, 0, 0, 44, 45, 46, 39, 38,
			19, 4, 1, 2, 0, 8, 13, 14, 0, 0, 47, 0, 48, 0, 49, };
	final static short yydgoto[] = { 8, 46, 9, 30, 31, 32, 33, 10, 11, 12, 34,
			35, 36, 37, 38, 39, };
	final static short yysindex[] = { -223, -223, -28, 0, 0, 0, 0, 0, -234, 0,
			-223, -254, 0, -250, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 0, 0,
			0, -227, -226, 0, 48, 0, 0, 0, -261, -5, 0, -223, -254, -229, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -223, -42, 0, -257, 0, -217, 0, };
	final static short yyrindex[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -225, 0, 0, 0, 0, 0, 0, -218,
			0, 0, 0, 0, -239, 0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -45, 0,
			0, 0, 10, 0, 0, 0, 0, 0, 0, };
	final static short yygindex[] = { 49, 0, 0, 0, 0, 25, 0, 12, -7, 0, 20, 0,
			0, 0, 17, 0, };
	final static int YYTABLESIZE = 321;
	static short yytable[];
	static {
		yytable();
	}

	static void yytable() {
		yytable = new short[] { 17, 33, 59, 41, 60, 31, 42, 47, 24, 40, 32, 27,
				43, 44, 45, 61, 34, 29, 11, 11, 11, 11, 11, 11, 11, 40, 11, 11,
				11, 11, 11, 11, 11, 11, 11, 1, 50, 51, 19, 2, 55, 3, 4, 57, 62,
				5, 5, 6, 7, 41, 13, 49, 56, 53, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 52, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 17, 17, 17, 17, 17, 17, 6, 58, 17, 17, 17, 17, 17, 17, 17,
				17, 17, 14, 15, 16, 17, 18, 19, 0, 0, 20, 21, 22, 23, 24, 25,
				26, 27, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 33, 33, 0,
				31, 33, 31, 33, 33, 32, 0, 32, 33, 33, 33, 34, 34, 34, 0, 0,
				34, 0, 34, 34, 0, 0, 0, 34, 34, 34, 14, 15, 16, 17, 18, 19, 0,
				0, 20, 21, 22, 23, 24, 25, 48, 27, 28, 14, 15, 16, 17, 18, 19,
				0, 0, 20, 21, 22, 23, 24, 25, 48, 27, 28, };
	}

	static short yycheck[];
	static {
		yycheck();
	}

	static void yycheck() {
		yycheck = new short[] { 45, 0, 44, 10, 261, 0, 260, 257, 269, 259, 0,
				272, 266, 267, 268, 272, 0, 45, 257, 258, 259, 260, 261, 262,
				263, 259, 265, 266, 267, 268, 269, 270, 271, 272, 273, 258,
				263, 263, 263, 262, 45, 264, 265, 272, 261, 263, 269, 270, 271,
				56, 1, 26, 40, 33, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 45, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 45, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, 257, 258, 259, 260, 261, 262, 263, 261, 265, 266,
				267, 268, 269, 270, 271, 272, 273, 257, 258, 259, 260, 261,
				262, -1, -1, 265, 266, 267, 268, 269, 270, 271, 272, 273, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 257, 258, 259, -1,
				257, 262, 259, 264, 265, 257, -1, 259, 269, 270, 271, 257, 258,
				259, -1, -1, 262, -1, 264, 265, -1, -1, -1, 269, 270, 271, 257,
				258, 259, 260, 261, 262, -1, -1, 265, 266, 267, 268, 269, 270,
				271, 272, 273, 257, 258, 259, 260, 261, 262, -1, -1, 265, 266,
				267, 268, 269, 270, 271, 272, 273, };
	}

	final static short YYFINAL = 8;
	final static short YYMAXTOKEN = 273;
	final static String yyname[] = { "end-of-file", null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, "','", "'-'", null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null,
			"LPAREN", "RPAREN", "PIPE", "LBRACES", "RBRACES", "LBRACKET",
			"RBRACKET", "QUOTED_CHAR", "POINT", "STAR", "PLUS", "QUESTIONMARK",
			"CHAR", "DOLLAR", "FIRST", "DIGIT", "BACKSLASH", };
	final static String yyrule[] = { "$accept : extended_reg_exp",
			"bracket_expression : LBRACKET matching_list RBRACKET",
			"bracket_expression : LBRACKET nonmatching_list RBRACKET",
			"matching_list : bracket_list",
			"nonmatching_list : FIRST bracket_list",
			"bracket_list : follow_list", "bracket_list : follow_list '-'",
			"follow_list : expression_term",
			"follow_list : follow_list expression_term",
			"expression_term : single_expression",
			"expression_term : range_expression",
			"single_expression : end_range", "single_expression : others",
			"range_expression : start_range end_range",
			"start_range : end_range '-'", "end_range : CHAR",
			"end_range : DIGIT", "others : '-'", "others : BACKSLASH",
			"others : FIRST", "others : DOLLAR", "others : STAR",
			"others : PLUS", "others : POINT", "others : QUESTIONMARK",
			"others : LPAREN", "others : RPAREN", "others : PIPE",
			"others : LBRACKET", "others : LBRACES", "others : RBRACES",
			"extended_reg_exp : ERE_branch",
			"extended_reg_exp : extended_reg_exp PIPE ERE_branch",
			"ERE_branch : ERE_expression",
			"ERE_branch : ERE_branch ERE_expression",
			"ERE_expression : one_char_or_coll_elem_ERE",
			"ERE_expression : FIRST", "ERE_expression : DOLLAR",
			"ERE_expression : RPAREN extended_reg_exp LPAREN",
			"ERE_expression : ERE_expression ERE_dupl_symbol",
			"one_char_or_coll_elem_ERE : CHAR",
			"one_char_or_coll_elem_ERE : QUOTED_CHAR",
			"one_char_or_coll_elem_ERE : POINT",
			"one_char_or_coll_elem_ERE : bracket_expression",
			"ERE_dupl_symbol : STAR", "ERE_dupl_symbol : PLUS",
			"ERE_dupl_symbol : QUESTIONMARK",
			"ERE_dupl_symbol : LBRACES DIGIT RBRACES",
			"ERE_dupl_symbol : LBRACES DIGIT ',' RBRACES",
			"ERE_dupl_symbol : LBRACES DIGIT ',' DIGIT RBRACES", };

	// #line 134 "TME4.y"

	private Yylex lexer;

	static Automate auto;

	private int yylex() {
		int yyl_return = -1;
		try {
			yylval = new ParserVal(0);
			yyl_return = lexer.yylex();
		} catch (IOException e) {
			System.err.println("IO error :" + e);
		}
		return yyl_return;
	}

	public void yyerror(String error) {
		System.err.println("Error: " + error);
	}

	public Parser(Reader r) {
		lexer = new Yylex(r, this);
	}

	static boolean interactive;

	public int parse() {
		return yyparse();
	}

	public Automate getAutomate() {
		return auto;
	}

	public void resetAutomate() {
		auto = null;
	}

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
				System.out.print("Enter a file name : ");
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				readAndTestFile(s);
			}
		} else {
			System.err.println("Utilisation : Parser <regex> <inputFile>");
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
				if (Automate.accept(line, auto)) {
					System.out.println(line);
				}
			}
			bf.close();
		} catch (IOException e) {
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

	// #line 413 "Parser.java"
	// ###############################################################
	// method: yylexdebug : check lexer state
	// ###############################################################
	void yylexdebug(int state, int ch) {
		String s = null;
		if (ch < 0)
			ch = 0;
		if (ch <= YYMAXTOKEN) // check index bounds
			s = yyname[ch]; // now get it
		if (s == null)
			s = "illegal-symbol";
		debug("state " + state + ", reading " + ch + " (" + s + ")");
	}

	// The following are now global, to aid in error reporting
	int yyn; // next next thing to do
	int yym; //
	int yystate; // current parsing state from state table
	String yys; // current token string

	// ###############################################################
	// method: yyparse : parse input and execute indicated items
	// ###############################################################
	@SuppressWarnings("unchecked")
	int yyparse() {
		boolean doaction;
		init_stacks();
		yynerrs = 0;
		yyerrflag = 0;
		yychar = -1; // impossible char forces a read
		yystate = 0; // initial state
		state_push(yystate); // save it
		val_push(yylval); // save empty value
		while (true) // until parsing is done, either correctly, or w/error
		{
			doaction = true;
			if (yydebug)
				debug("loop");
			// #### NEXT ACTION (from reduction table)
			for (yyn = yydefred[yystate]; yyn == 0; yyn = yydefred[yystate]) {
				if (yydebug)
					debug("yyn:" + yyn + "  state:" + yystate + "  yychar:"
							+ yychar);
				if (yychar < 0) // we want a char?
				{
					yychar = yylex(); // get next token
					if (yydebug)
						debug(" next yychar:" + yychar);
					// #### ERROR CHECK ####
					if (yychar < 0) // it it didn't work/error
					{
						yychar = 0; // change it to default string (no -1!)
						if (yydebug)
							yylexdebug(yystate, yychar);
					}
				}// yychar<0
				yyn = yysindex[yystate]; // get amount to shift by (shift index)
				if ((yyn != 0) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE
						&& yycheck[yyn] == yychar) {
					if (yydebug)
						debug("state " + yystate + ", shifting to state "
								+ yytable[yyn]);
					// #### NEXT STATE ####
					yystate = yytable[yyn];// we are in a new state
					state_push(yystate); // save it
					val_push(yylval); // push our lval as the input for next
										// rule
					yychar = -1; // since we have 'eaten' a token, say we need
									// another
					if (yyerrflag > 0) // have we recovered an error?
						--yyerrflag; // give ourselves credit
					doaction = false; // but don't process yet
					break; // quit the yyn=0 loop
				}

				yyn = yyrindex[yystate]; // reduce
				if ((yyn != 0) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE
						&& yycheck[yyn] == yychar) { // we reduced!
					if (yydebug)
						debug("reduce");
					yyn = yytable[yyn];
					doaction = true; // get ready to execute
					break; // drop down to actions
				} else // ERROR RECOVERY
				{
					if (yyerrflag == 0) {
						yyerror("syntax error");
						yynerrs++;
					}
					if (yyerrflag < 3) // low error count?
					{
						yyerrflag = 3;
						while (true) // do until break
						{
							if (stateptr < 0) // check for under & overflow here
							{
								yyerror("stack underflow. aborting..."); // note
																			// lower
																			// case
																			// 's'
								return 1;
							}
							yyn = yysindex[state_peek(0)];
							if ((yyn != 0) && (yyn += YYERRCODE) >= 0
									&& yyn <= YYTABLESIZE
									&& yycheck[yyn] == YYERRCODE) {
								if (yydebug)
									debug("state "
											+ state_peek(0)
											+ ", error recovery shifting to state "
											+ yytable[yyn] + " ");
								yystate = yytable[yyn];
								state_push(yystate);
								val_push(yylval);
								doaction = false;
								break;
							} else {
								if (yydebug)
									debug("error recovery discarding state "
											+ state_peek(0) + " ");
								if (stateptr < 0) // check for under & overflow
													// here
								{
									yyerror("Stack underflow. aborting..."); // capital
																				// 'S'
									return 1;
								}
								state_pop();
								val_pop();
							}
						}
					} else // discard this token
					{
						if (yychar == 0)
							return 1; // yyabort
						if (yydebug) {
							yys = null;
							if (yychar <= YYMAXTOKEN)
								yys = yyname[yychar];
							if (yys == null)
								yys = "illegal-symbol";
							debug("state " + yystate
									+ ", error recovery discards token "
									+ yychar + " (" + yys + ")");
						}
						yychar = -1; // read another
					}
				}// end error recovery
			}// yyn=0 loop
			if (!doaction) // any reason not to proceed?
				continue; // skip action
			yym = yylen[yyn]; // get count of terminals on rhs
			if (yydebug)
				debug("state " + yystate + ", reducing " + yym + " by rule "
						+ yyn + " (" + yyrule[yyn] + ")");
			if (yym > 0) // if count of rhs not 'nil'
				yyval = val_peek(yym - 1); // get current semantic value
			yyval = dup_yyval(yyval); // duplicate yyval if ParserVal is used as
										// semantic value
			switch (yyn) {
			// ########## USER-SUPPLIED ACTIONS ##########
			case 1:
			// #line 29 "TME4.y"
			{
				yyval.obj = Factory
						.creerAutomateFromMatchingList((HashSet<String>) val_peek(1).obj);
			}
				break;
			case 2:
			// #line 35 "TME4.y"
			{
				yyval.obj = Factory
						.creerAutomateFromMatchingList((HashSet<String>) val_peek(1).obj);
			}
				break;
			case 3:
			// #line 39 "TME4.y"
			{
				yyval.obj = (HashSet<String>) val_peek(0).obj;
			}
				break;
			case 4:
			// #line 41 "TME4.y"
			{
				yyval.obj = (HashSet<String>) Factory
						.getExtendedAsciiChar((HashSet<String>) val_peek(0).obj);
			}
				break;
			case 5:
			// #line 45 "TME4.y"
			{
				yyval.obj = (HashSet<String>) val_peek(0).obj;
			}
				break;
			case 6:
			// #line 46 "TME4.y"
			{
				HashSet<String> res = new HashSet<String>(
						(HashSet<String>) val_peek(1).obj);
				res.add("" + '-');
				yyval.obj = (HashSet<String>) res;
			}
				break;
			case 7:
			// #line 50 "TME4.y"
			{
				yyval.obj = (HashSet<String>) val_peek(0).obj;
			}
				break;
			case 8:
			// #line 51 "TME4.y"
			{
				HashSet<String> res = new HashSet<String>(
						(HashSet<String>) val_peek(1).obj);
				res.addAll((HashSet<String>) val_peek(0).obj);
				yyval.obj = res;
			}
				break;
			case 9:
			// #line 56 "TME4.y"
			{
				yyval.obj = (HashSet<String>) val_peek(0).obj;
			}
				break;
			case 10:
			// #line 57 "TME4.y"
			{
				yyval.obj = (HashSet<String>) val_peek(0).obj;
			}
				break;
			case 11:
			// #line 59 "TME4.y"
			{
				HashSet<String> res = new HashSet<String>();
				res.add("" + ((char) val_peek(0).obj));
				yyval.obj = res;
			}
				break;
			case 12:
			// #line 63 "TME4.y"
			{
				HashSet<String> res = new HashSet<String>();
				res.add("" + ((char) val_peek(0).obj));
				yyval.obj = res;
			}
				break;
			case 13:
			// #line 68 "TME4.y"
			{
				yyval.obj = Factory.getCharBetween((char) val_peek(1).obj,
						(char) val_peek(0).obj);
			}
				break;
			case 14:
			// #line 71 "TME4.y"
			{
				yyval.obj = (char) val_peek(1).obj;
			}
				break;
			case 15:
			// #line 73 "TME4.y"
			{
				yyval.obj = (char) val_peek(0).sval.charAt(0);
			}
				break;
			case 16:
			// #line 74 "TME4.y"
			{
				yyval.obj = Character.toChars(48 + val_peek(0).ival)[0];
			}
				break;
			case 17:
			// #line 76 "TME4.y"
			{
				yyval.obj = '-';
			}
				break;
			case 18:
			// #line 77 "TME4.y"
			{
				yyval.obj = '\\';
			}
				break;
			case 19:
			// #line 78 "TME4.y"
			{
				yyval.obj = '^';
			}
				break;
			case 20:
			// #line 79 "TME4.y"
			{
				yyval.obj = '$';
			}
				break;
			case 21:
			// #line 80 "TME4.y"
			{
				yyval.obj = '*';
			}
				break;
			case 22:
			// #line 81 "TME4.y"
			{
				yyval.obj = '+';
			}
				break;
			case 23:
			// #line 82 "TME4.y"
			{
				yyval.obj = '.';
			}
				break;
			case 24:
			// #line 83 "TME4.y"
			{
				yyval.obj = '?';
			}
				break;
			case 25:
			// #line 84 "TME4.y"
			{
				yyval.obj = '(';
			}
				break;
			case 26:
			// #line 85 "TME4.y"
			{
				yyval.obj = ')';
			}
				break;
			case 27:
			// #line 86 "TME4.y"
			{
				yyval.obj = '|';
			}
				break;
			case 28:
			// #line 87 "TME4.y"
			{
				yyval.obj = '[';
			}
				break;
			case 29:
			// #line 88 "TME4.y"
			{
				yyval.obj = '{';
			}
				break;
			case 30:
			// #line 89 "TME4.y"
			{
				yyval.obj = '}';
			}
				break;
			case 31:
			// #line 98 "TME4.y"
			{
				auto = (Automate) val_peek(0).obj;
			}
				break;
			case 32:
			// #line 99 "TME4.y"
			{
				auto = (Automate) Factory.union((Automate) val_peek(2).obj,
						(Automate) val_peek(0).obj);
			}
				break;
			case 33:
			// #line 104 "TME4.y"
			{
				yyval.obj = (Automate) val_peek(0).obj;
			}
				break;
			case 34:
			// #line 105 "TME4.y"
			{
				yyval.obj = (Automate) Factory.concatenation(
						(Automate) val_peek(1).obj, (Automate) val_peek(0).obj);
			}
				break;
			case 35:
			// #line 110 "TME4.y"
			{
				yyval.obj = (Automate) val_peek(0).obj;
			}
				break;
			case 36:
			// #line 111 "TME4.y"
			{
				yyval.obj = (Automate) Factory.creerAutomateDebutLigne();
			}
				break;
			case 37:
			// #line 112 "TME4.y"
			{
				yyval.obj = (Automate) Factory.creerAutomateFinLigne();
			}
				break;
			case 38:
			// #line 113 "TME4.y"
			{
				yyval.obj = (Automate) val_peek(1).obj;
			}
				break;
			case 39:
			// #line 114 "TME4.y"
			{
				yyval.obj = (Automate) Factory.ERE_dupl_symbol(
						(Automate) val_peek(1).obj, (String) val_peek(0).sval);
			}
				break;
			case 40:
			// #line 117 "TME4.y"
			{
				yyval.obj = (Automate) Factory.creerAutomate(""
						+ val_peek(0).sval);
			}
				break;
			case 41:
			// #line 118 "TME4.y"
			{
				yyval.obj = (Automate) Factory.creerAutomate(""
						+ val_peek(0).sval.charAt(1));
			}
				break;
			case 42:
			// #line 120 "TME4.y"
			{
				yyval.obj = (Automate) Factory
						.creerAutomateFromMatchingList(Factory
								.getExtendedAsciiChar());
				;
			}
				break;
			case 43:
			// #line 123 "TME4.y"
			{
				yyval.obj = (Automate) val_peek(0).obj;
			}
				break;
			case 44:
			// #line 125 "TME4.y"
			{
				yyval.sval = (String) new String("*");
			}
				break;
			case 45:
			// #line 126 "TME4.y"
			{
				yyval.sval = (String) new String("+");
			}
				break;
			case 46:
			// #line 127 "TME4.y"
			{
				yyval.sval = (String) new String("?");
			}
				break;
			case 47:
			// #line 128 "TME4.y"
			{
				yyval.sval = (String) new String("" + val_peek(1).ival);
			}
				break;
			case 48:
			// #line 129 "TME4.y"
			{
				yyval.sval = (String) new String(val_peek(2).ival + ",hk");
			}
				break;
			case 49:
			// #line 130 "TME4.y"
			{
				yyval.sval = (String) new String(val_peek(3).ival + ","
						+ val_peek(1).ival);
			}
				break;
			// #line 788 "Parser.java"
			// ########## END OF USER-SUPPLIED ACTIONS ##########
			}// switch
				// #### Now let's reduce... ####
			if (yydebug)
				debug("reduce");
			state_drop(yym); // we just reduced yylen states
			yystate = state_peek(0); // get new state
			val_drop(yym); // corresponding value drop
			yym = yylhs[yyn]; // select next TERMINAL(on lhs)
			if (yystate == 0 && yym == 0)// done? 'rest' state and at first
											// TERMINAL
			{
				if (yydebug)
					debug("After reduction, shifting from state 0 to state "
							+ YYFINAL + "");
				yystate = YYFINAL; // explicitly say we're done
				state_push(YYFINAL); // and save it
				val_push(yyval); // also save the semantic value of parsing
				if (yychar < 0) // we want another character?
				{
					yychar = yylex(); // get next character
					if (yychar < 0)
						yychar = 0; // clean, if necessary
					if (yydebug)
						yylexdebug(yystate, yychar);
				}
				if (yychar == 0) // Good exit (if lex returns 0 ;-)
					break; // quit the loop--all DONE
			}// if yystate
			else // else not done yet
			{ // get next state and push, for next yydefred[]
				yyn = yygindex[yym]; // find out where to go
				if ((yyn != 0) && (yyn += yystate) >= 0 && yyn <= YYTABLESIZE
						&& yycheck[yyn] == yystate)
					yystate = yytable[yyn]; // get new state
				else
					yystate = yydgoto[yym]; // else go to new defred
				if (yydebug)
					debug("after reduction, shifting from state "
							+ state_peek(0) + " to state " + yystate + "");
				state_push(yystate); // going again, so push state & val...
				val_push(yyval); // for next action
			}
		}// main loop
		return 0;// yyaccept!!
	}

	// ## end of method parse() ######################################

	// ## run() --- for Thread #######################################
	/**
	 * A default run method, used for operating this parser object in the
	 * background. It is intended for extending Thread or implementing Runnable.
	 * Turn off with -Jnorun .
	 */
	public void run() {
		yyparse();
	}

	// ## end of method run() ########################################

	// ## Constructors ###############################################
	/**
	 * Default constructor. Turn off with -Jnoconstruct .
	 */
	public Parser() {
		// nothing to do
	}

	/**
	 * Create a parser, setting the debug to true or false.
	 * 
	 * @param debugMe
	 *            true for debugging, false for no debug.
	 */
	public Parser(boolean debugMe) {
		yydebug = debugMe;
	}
	// ###############################################################

}
// ################### END OF CLASS ##############################
