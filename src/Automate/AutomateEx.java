package Automate;

import java.util.ArrayList;

public class AutomateEx {

    public static void main(String[] args) {
	TestAutomate2();
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

	e2.addTransition(new Transition(e2, null, "a"));
	e2.addTransition(new Transition(e2, e3, "b"));

	e3.addTransition(new Transition(e3, null, "a"));
	e3.addTransition(new Transition(e3, e4, "b"));

	e4.addTransition(new Transition(e4, null, "a"));
	e4.addTransition(new Transition(e4, e5, "b"));

	e5.addTransition(new Transition(e5, null, "a"));
	e5.addTransition(new Transition(e5, null, "b"));

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
}
