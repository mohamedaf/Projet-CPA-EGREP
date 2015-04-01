package Automate;

import java.util.ArrayList;

public class Factory {

    public static Etat creerEtat() {
	return new Etat();
    }

    public static Etat creerEtat(ArrayList<Transition> transitions) {
	return new Etat(transitions);
    }

    public static Transition creerTransition(Etat depart, Etat arrivee,
	    String caractere) {
	return new Transition(depart, arrivee, caractere);
    }

    public static Automate creerAutomate(Etat initial, ArrayList<Etat> finaux,
	    ArrayList<String> caracteres) {
	return new Automate(initial, finaux, caracteres);
    }

    public static Automate concatenation(Automate a1, Automate a2) {

	/* L'alphabet du nouvel automate et l'union des deux alphabets */
	ArrayList<String> alphabet = new ArrayList<String>();

	for (String s : a1.getCaracteresList()) {
	    alphabet.add(s);
	}

	for (String s : a2.getCaracteresList()) {
	    alphabet.add(s);
	}

	/* Etat Initial du nouvel automate est l'etat initial de a1 */
	Etat initial = a1.getInitial();

	/* Les etats Finaux du nouvel automate sont les etats finaux de a2 */
	ArrayList<Etat> finaux = a2.getFinauxList();

	/* On prend les etats finaux de a1 */
	/* Nouvelle transition avec initial de a2 */

	for (Etat e : a1.getFinauxList()) {
	    e.addTransition(creerTransition(e, a2.getInitial(), new String(
		    "eps")));
	}

	return creerAutomate(initial, finaux, alphabet);
    }

    public static Automate union(Automate a1, Automate a2) {
	/* L'alphabet du nouvel automate et l'union des deux alphabets */
	ArrayList<String> alphabet = new ArrayList<String>();

	for (String s : a1.getCaracteresList()) {
	    alphabet.add(s);
	}

	for (String s : a2.getCaracteresList()) {
	    alphabet.add(s);
	}

	/* Etat finaux du nouvel automate l'union des etats finaux de a1 et a2 */

	ArrayList<Etat> finaux = new ArrayList<Etat>();

	for (Etat e : a1.getFinauxList()) {
	    finaux.add(e);
	}

	for (Etat e : a2.getFinauxList()) {
	    finaux.add(e);
	}

	/* Etat Initial du nouvel automate */
	Etat init = creerEtat();
	init.addTransition(creerTransition(init, a1.getInitial(), new String(
		"eps")));
	init.addTransition(creerTransition(init, a2.getInitial(), new String(
		"eps")));

	return creerAutomate(init, finaux, alphabet);
    }

    public static Automate questionMark(Automate a) {
	Etat init = creerEtat();
	Etat f = creerEtat();

	init.addTransition(creerTransition(init, a.getInitial(), new String(
		"eps")));
	init.addTransition(creerTransition(init, f, new String("eps")));

	ArrayList<Etat> finaux = a.getFinauxList();
	finaux.add(f);

	a.setInitial(init);
	a.setFinaux(finaux);

	return a;
    }

    public static Automate Star(Automate a) {
	Etat init = creerEtat();
	Etat f = creerEtat();

	init.addTransition(creerTransition(init, a.getInitial(), new String(
		"eps")));
	init.addTransition(creerTransition(init, f, new String("eps")));

	ArrayList<Etat> finaux = a.getFinauxList();

	/*
	 * On ajoute un transition allant vers le nouvel etat initial pour
	 * chaque etat final de a
	 */

	for (Etat e : finaux) {
	    e.addTransition(creerTransition(e, init, new String("eps")));
	}

	finaux.add(f);

	a.setInitial(init);
	a.setFinaux(finaux);

	return a;
    }

    public static Automate Plus(Automate a) {

	/*
	 * On ajoute un transition allant vers l'etat initial pour chaque etat
	 * final de a
	 */

	for (Etat e : a.getFinauxList()) {
	    e.addTransition(creerTransition(e, a.getInitial(),
		    new String("eps")));
	}

	return a;
    }

}
