package Automate;

import java.util.ArrayList;
import java.util.HashSet;

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

    public static Automate creerAutomate(String car) {
	ArrayList<String> caracteres = new ArrayList<String>();
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	Etat initial = new Etat();
	Etat fin = new Etat();

	initial.addTransition(new Transition(initial, fin, car));
	caracteres.add(car);

	finaux.add(fin);

	return new Automate(initial, finaux, caracteres);
    }

    public static Automate creerAutomate() {
	ArrayList<String> caracteres = new ArrayList<String>();
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	Etat initial = new Etat();

	finaux.add(initial);

	return new Automate(initial, finaux, caracteres);
    }

    public static Automate concatenation(Automate a1, Automate a2) {

	/* L'alphabet du nouvel automate et l'union des deux alphabets */
	ArrayList<String> alphabet = new ArrayList<String>();

	HashSet<String> hs = new HashSet<String>(a1.getAlphabetList());
	hs.addAll(a2.getAlphabetList());

	alphabet.addAll(hs);

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

	HashSet<String> hs = new HashSet<String>(a1.getAlphabetList());
	hs.addAll(a2.getAlphabetList());

	alphabet.addAll(hs);

	/* Etat finaux du nouvel automate l'union des etats finaux de a1 et a2 */

	ArrayList<Etat> finaux = new ArrayList<Etat>();

	finaux.addAll(a1.getFinauxList());
	finaux.addAll(a2.getFinauxList());

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

    /**************************************************************************/
    /* Essayer de faire fonctionner EGREP pour non deterministe avant de ***** */
    /*********************** passer a cette partie ****************************/
    /**************************************************************************/

    /*
     * Prend un automate non deterministe et retourne une minimisation de cet
     * automate deterministe
     */
    public static Automate getAutomateDeterministe(Automate a) {
	/* TO DO */
	return null;
    }

    /* Verifie que l'automate est complet */
    public static Boolean estAutomateComplet(Automate a) {
	/* TO DO */
	return false;
    }

    /* Supprime les epsilon transitions dans l'automate */
    public static Automate removeEpsilonTransition(Automate a) {
	/* TO DO */
	return null;
    }

    /*
     * Retourne l'automate reconaissant le langage mirroir de l'automate donnee
     * en argument
     */
    public static Automate getAutomateMirroir(Automate a) {
	/* TO DO */
	return null;
    }

    /*
     * Prend un automate deterministe et retourne une minimisation de cet
     * automate
     */
    public static Automate minimisation(Automate a) {
	/* TO DO */
	return null;
    }
}
