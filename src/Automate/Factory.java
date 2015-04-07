package Automate;

import java.util.ArrayList;
import java.util.Collection;
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
	ArrayList<String> alphabet = new ArrayList<String>();
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	Etat initial = new Etat();
	Etat fin = new Etat();

	initial.addTransition(new Transition(initial, fin, car));
	alphabet.add(car);

	finaux.add(fin);

	return new Automate(initial, finaux, alphabet);
    }

    public static Automate creerAutomate() {
	ArrayList<String> caracteres = new ArrayList<String>();
	ArrayList<Etat> finaux = new ArrayList<Etat>();
	Etat initial = new Etat();

	finaux.add(initial);

	return new Automate(initial, finaux, caracteres);
    }

    public static Automate concatenation(Automate a1, Automate a2) {

	if (a1 == null)
	    return a2;

	if (a2 == null)
	    return a1;

	/* si a1 vide retourner a2 */
	if ((a1.getFinauxList().size() == 1)
		&& (a1.getInitial() == a1.getEtatFinal(0))) {
	    return a2;
	}

	/* si a2 vide retourner a1 */
	if ((a2.getFinauxList().size() == 1)
		&& (a2.getInitial() == a2.getEtatFinal(0))) {
	    return a1;
	}

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

    public static Automate ERE_dupl_symbol(Automate tmp, String s) {
	Automate tmp2 = tmp.clone();

	switch (s) {
	case "*":
	    return Factory.Star(tmp);
	case "+":
	    return Factory.Plus(tmp);
	case "?":
	    return Factory.questionMark(tmp);
	default:
	    String tab[] = s.split(",");
	    if (tab.length == 1) {
		/* Exactement n repetitions */
		for (int i = 1; i < Integer.parseInt(tab[0]); i++) {
		    tmp = Factory.concatenation(tmp, tmp2.clone());
		}

		return tmp;
	    } else if (tab[1].equals("hk")) {
		/*
		 * Au moins n repetitions, donc n-1 concatenation et
		 * transformation Plus pour dernier automate pour pouvoir
		 * repeter
		 */
		for (int i = 1; i < Integer.parseInt(tab[0]); i++) {
		    if (i == (Integer.parseInt(tab[0]) - 1)) {
			tmp = Factory.concatenation(tmp,
				Factory.Plus(tmp2.clone()));
		    } else {
			tmp = Factory.concatenation(tmp, tmp2.clone());
		    }
		}

		return tmp;
	    } else {
		ArrayList<Etat> finaux = new ArrayList<Etat>();

		for (int i = 1; i < Integer.parseInt(tab[1]); i++) {
		    tmp = Factory.concatenation(tmp, tmp2.clone());

		    if (i >= (Integer.parseInt(tab[0]) - 1)) {
			finaux.addAll(tmp.getFinauxList());
		    }
		}

		tmp.setFinaux(finaux);
		tmp.getEtatFinal(tmp.getFinauxList().size() - 1)
			.setTransitions(new ArrayList<Transition>());

		return tmp;
	    }
	}
    }

    public static Automate creerAutomateFromMatchingList(Collection<String> ls) {

	ArrayList<String> alphabet = new ArrayList<String>(ls);

	/* Etats initial et final */
	Etat initial = creerEtat();
	Etat fin = creerEtat();

	/* On ajoute des transitions de l'etat initial a l'etat final */
	for (String s : ls) {
	    initial.addTransition(creerTransition(initial, fin, s));
	}

	ArrayList<Etat> finaux = new ArrayList<Etat>();
	finaux.add(fin);

	return creerAutomate(initial, finaux, alphabet);
    }

    public static HashSet<String> getCharBetween(char origin, char dest) {
	HashSet<String> res = new HashSet<String>();
	for (char c = origin; c <= dest; c++) {
	    res.add("" + c);
	}
	return res;
    }

    public static HashSet<String> getExtendedAsciiChar(HashSet<String> s) {
	HashSet<String> res = new HashSet<String>();
	for (char c = 33; c <= 126; c++) {
	    if (!s.contains(String.valueOf(c)))
		res.add(String.valueOf(c));
	}
	return res;
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
