package Automate;

import java.util.ArrayList;

public class AutomateEx {

    public static void main(String[] args) {
	// TestAutomate4();
	// TestAutomate5();
	// TestAutomate6();
    }

    public static void TestAutomate1() {
	Etat e0, e1, e2, e3, e4, e5, e6, e7, e8, e9;
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	ArrayList<String> alphabet = new ArrayList<String>();

	/* Initialisation de l'automate */

	alphabet.add("a");
	alphabet.add("b");

	e0 = new Etat();
	e1 = new Etat();
	e2 = new Etat();
	e3 = new Etat();
	e4 = new Etat();
	e5 = new Etat();
	e6 = new Etat();
	e7 = new Etat();
	e8 = new Etat();
	e9 = new Etat();

	e0.addTransition(new Transition(e0, e6, "a"));
	e0.addTransition(new Transition(e0, e1, "b"));

	e1.addTransition(new Transition(e1, e2, "a"));
	e1.addTransition(new Transition(e1, e1, "b"));

	e2.addTransition(new Transition(e2, e3, "b"));

	e3.addTransition(new Transition(e3, e4, "b"));

	e4.addTransition(new Transition(e4, e5, "b"));

	e6.addTransition(new Transition(e6, e6, "a"));
	e6.addTransition(new Transition(e6, e7, "b"));

	e7.addTransition(new Transition(e7, e2, "a"));
	e7.addTransition(new Transition(e7, e8, "b"));

	e8.addTransition(new Transition(e8, e2, "a"));
	e8.addTransition(new Transition(e8, e9, "b"));

	e9.addTransition(new Transition(e9, e2, "a"));
	e9.addTransition(new Transition(e9, e1, "b"));

	finaux.add(e5);
	finaux.add(e9);

	Automate a = new Automate(e0, finaux, alphabet);

	/* test sur des expressions donnees si l'automate accepte */

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	texte.add("a");
	texte.add("b");
	texte.add("b");
	texte.add("a");
	texte.add("b");
	texte.add("b");
	texte.add("b");

	if (Automate.accept(e0, texte, a))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");
    }

    public static void TestAutomate2() {
	Etat e0, e1, e2, e3, e4, e5, e6;
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	ArrayList<String> alphabet = new ArrayList<String>();

	/* Initialisation de l'automate */

	alphabet.add("a");
	alphabet.add("b");
	alphabet.add("c");
	alphabet.add("d");

	e0 = new Etat();
	e1 = new Etat();
	e2 = new Etat();
	e3 = new Etat();
	e4 = new Etat();
	e5 = new Etat();
	e6 = new Etat();

	e0.addTransition(new Transition(e0, e0, "a"));
	e0.addTransition(new Transition(e0, e0, "b"));
	e0.addTransition(new Transition(e0, e0, "c"));
	e0.addTransition(new Transition(e0, e1, "d"));

	e1.addTransition(new Transition(e1, e2, "a"));
	e1.addTransition(new Transition(e1, e0, "b"));
	e1.addTransition(new Transition(e1, e0, "c"));
	e1.addTransition(new Transition(e1, e1, "d"));

	e2.addTransition(new Transition(e2, e0, "a"));
	e2.addTransition(new Transition(e2, e0, "b"));
	e2.addTransition(new Transition(e2, e3, "c"));
	e2.addTransition(new Transition(e2, e0, "d"));

	e3.addTransition(new Transition(e3, e0, "a"));
	e3.addTransition(new Transition(e3, e4, "b"));
	e3.addTransition(new Transition(e3, e0, "c"));
	e3.addTransition(new Transition(e3, e4, "d"));

	e4.addTransition(new Transition(e4, e5, "a"));
	e4.addTransition(new Transition(e4, e0, "b"));
	e4.addTransition(new Transition(e4, e0, "c"));
	e4.addTransition(new Transition(e4, e0, "d"));

	e5.addTransition(new Transition(e5, e0, "a"));
	e5.addTransition(new Transition(e5, e0, "b"));
	e5.addTransition(new Transition(e5, e6, "c"));
	e5.addTransition(new Transition(e5, e0, "d"));

	e6.addTransition(new Transition(e6, e0, "a"));
	e6.addTransition(new Transition(e6, e0, "b"));
	e6.addTransition(new Transition(e6, e0, "c"));
	e6.addTransition(new Transition(e6, e0, "d"));

	finaux.add(e6);

	Automate a = new Automate(e0, finaux, alphabet);

	/* test sur des expressions donnees si l'automate accepte */

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	texte.add("a");
	texte.add("d");
	texte.add("a");
	texte.add("c");
	texte.add("d");
	texte.add("a");
	texte.add("c");

	if (Automate.accept(e0, texte, a))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");
    }

    public static void TestAutomate3() {
	Etat initial, inter, inter2, fin;
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	ArrayList<String> alphabet = new ArrayList<String>();

	/* Initialisation de l'automate */

	alphabet.add("a");
	alphabet.add("b");

	inter2 = new Etat();
	inter = new Etat();
	initial = new Etat();
	fin = new Etat();

	initial.addTransition(new Transition(initial, inter, "a"));
	inter.addTransition(new Transition(inter, inter2, "b"));
	inter2.addTransition(new Transition(inter2, fin, "a"));

	finaux.add(fin);

	Automate a = new Automate(initial, finaux, alphabet);

	/* test sur des expressions donnees si l'automate accepte */

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	texte.add("b");
	texte.add("a");

	if (Automate.accept(a.getInitial(), texte, a))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");
    }

    public static void TestAutomate4() {
	Etat init1, init2, fin1, fin2;
	ArrayList<Etat> finaux1 = new ArrayList<Etat>();
	ArrayList<String> alphabet1 = new ArrayList<String>();
	ArrayList<Etat> finaux2 = new ArrayList<Etat>();
	ArrayList<String> alphabet2 = new ArrayList<String>();

	init1 = new Etat();
	init2 = new Etat();
	fin1 = new Etat();
	fin2 = new Etat();

	alphabet1.add("a");
	alphabet2.add("b");

	init1.addTransition(new Transition(init1, fin1, "a"));
	init2.addTransition(new Transition(init2, fin2, "b"));

	finaux1.add(fin1);
	finaux2.add(fin2);

	Automate a1 = new Automate(init1, finaux1, alphabet1);
	Automate a2 = new Automate(init2, finaux2, alphabet2);

	Automate a3 = Factory.concatenation(a1, a2);

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	// texte.add("b");

	if (Automate.accept(a3.getInitial(), texte, a3))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");

    }

    public static void TestAutomate5() {
	Etat init1, fin1;
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	ArrayList<String> alphabet = new ArrayList<String>();

	init1 = new Etat();
	fin1 = new Etat();

	alphabet.add("a");

	init1.addTransition(new Transition(init1, fin1, "a"));

	finaux.add(fin1);

	Automate a1 = new Automate(init1, finaux, alphabet);

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	texte.add("a");
	Automate a2 = a1.clone();

	a1 = Factory.concatenation(a1, a2);

	if (Automate.accept(a1.getInitial(), texte, a1))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");
    }

    public static void TestAutomate6() {
	Etat init1, init2, fin1, fin2;
	ArrayList<Etat> finaux1 = new ArrayList<Etat>();
	ArrayList<String> alphabet1 = new ArrayList<String>();
	ArrayList<Etat> finaux2 = new ArrayList<Etat>();
	ArrayList<String> alphabet2 = new ArrayList<String>();

	init1 = new Etat();
	init2 = new Etat();
	fin1 = new Etat();
	fin2 = new Etat();

	alphabet1.add("a");

	init1.addTransition(new Transition(init1, fin1, "a"));
	init2.addTransition(new Transition(init2, fin2, "a"));

	finaux1.add(fin1);
	finaux2.add(fin2);

	Automate a1 = new Automate(init1, finaux1, alphabet1);
	Automate a2 = new Automate(init2, finaux2, alphabet2);

	Automate a3 = Factory.concatenation(a1, a2);

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	// texte.add("b");

	if (Automate.accept(a3.getInitial(), texte, a3))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");

    }

}