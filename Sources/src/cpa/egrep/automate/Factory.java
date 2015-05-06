package cpa.egrep.automate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
/**
 * @author HK-Lab
 *
 */
public class Factory {

	/**
	 * @return Un nouvel etat sans aucune transition.
	 */
	public static Etat creerEtat() {
		return new Etat();
	}

	/**
	 * @param transitions
	 *            Transitions sortantes de l'etat
	 * @return Un nouvel etat ayant les transitions passees en parametre.
	 */
	public static Etat creerEtat(ArrayList<Transition> transitions) {
		Etat e = creerEtat();
		e.setTransitions(transitions);
		return e;
	}

	/**
	 * @return Un nouvel etat sans transitions representant un debut de ligne
	 *         (regex commencant par '^').
	 */
	public static Etat creerEtatDebutLigne() {
		Etat e = creerEtat();
		e.setDebutLigne(true);
		return e;
	}

	/**
	 * @return Un nouvel etat sans transitions representant une fin de ligne
	 *         (regex terminant par '$').
	 */
	public static Etat creerEtatFinLigne() {
		Etat e = creerEtat();
		e.setFinLigne(true);
		return e;
	}

	/**
	 * @param depart
	 *            Etat de depart.
	 * @param arrivee
	 *            Etat d'arrivee.
	 * @param caractere
	 *            Valeur de la transition.
	 * @return Une nouvelle transition allant de depart a arrivee portant le
	 *         caractere car.
	 */
	public static Transition creerTransition(Etat depart, Etat arrivee,
			String car) {
		return new Transition(depart, arrivee, car);
	}

	/**
	 * @return Un nouvel automate avec un etat initial qui est aussi final.
	 */
	public static Automate creerAutomate() {
		ArrayList<String> caracteres = new ArrayList<String>();
		ArrayList<Etat> finaux = new ArrayList<Etat>();
		Etat initial = new Etat();

		finaux.add(initial);

		return new Automate(initial, finaux, caracteres);
	}

	/**
	 * @param car
	 *            Caractere de la transition
	 * @return Un nouvel automate avec une seule transition portant le caractere
	 *         car.
	 */
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

	/**
	 * @param initial
	 *            Etat initial.
	 * @param finaux
	 *            Etats finaux.
	 * @param caracteres
	 *            Alphabet de l'automate.
	 * @return Un nouveal automate.
	 */
	public static Automate creerAutomate(Etat initial, ArrayList<Etat> finaux,
			ArrayList<String> caracteres) {
		return new Automate(initial, finaux, caracteres);
	}

	/**
	 * @param ls
	 *            Liste des caracteres des transitions
	 * @return un nouvel automate avec un etat initial, un etat final, et des
	 *         transitions dont les caracteres sont contenus dans ls.
	 */
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

	/**
	 * @return Un automate dont l'etat initial represente un debut de ligne.
	 */
	public static Automate creerAutomateDebutLigne() {
		Etat e1 = creerEtatDebutLigne();
		ArrayList<Etat> finaux = new ArrayList<Etat>();
		finaux.add(e1);
		ArrayList<String> alphabet = new ArrayList<String>();
		return creerAutomate(e1, finaux, alphabet);
	}

	/**
	 * @return Un automate dont l'etat initial represente une fin de ligne.
	 */
	public static Automate creerAutomateFinLigne() {
		Etat e1 = creerEtatFinLigne();
		ArrayList<Etat> finaux = new ArrayList<Etat>();
		finaux.add(e1);
		ArrayList<String> alphabet = new ArrayList<String>();
		return creerAutomate(e1, finaux, alphabet);
	}

	/**
	 * @param a1
	 *            Un automate, ou null.
	 * @param a2
	 *            Un automate, ou null.
	 * @return La concatenation de a1 et a2.
	 */
	public static Automate concatenation(Automate a1, Automate a2) {

		if (a1 == null)
			return a2;

		if (a2 == null)
			return a1;

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

	/**
	 * @param a1
	 *            Un automate ou null.
	 * @param a2
	 *            Un automate ou null.
	 * @return L'automate représentant l'union de a1 et a2 (a1 U a2).
	 */
	public static Automate union(Automate a1, Automate a2) {
		if (a1 == null)
			return a2;

		if (a2 == null)
			return a1;

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

	/**
	 * @param a
	 * @return L'automate forme par la regexp ?.
	 */
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

	/**
	 * @param a1
	 * @return L'automate forme par la regexp *.
	 */
	public static Automate Star(Automate a1) {
		Automate a = a1.clone();
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

	/**
	 * @param a
	 * @return L'automate forme par la regexp +.
	 */
	public static Automate Plus(Automate a) {
		Automate a1 = a.clone();
		/*
		 * On ajoute un transition allant vers l'etat initial pour chaque etat
		 * final de a
		 */
		for (Etat e : a1.getFinauxList()) {
			e.addTransition(creerTransition(e, a1.getInitial(), new String(
					"eps")));
		}
		return a1;
	}

	/**
	 * @param tmp
	 * @param s
	 * @return Un automate forme par la regexp *, +, ? ou {x}, {x,}, {x,y}.
	 */
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
				/* Exactement n repetitions, donc n-1 concatenations */
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

	/**
	 * @param origin
	 * @param dest
	 * @return La liste des caracteres compris entre origin et dest.
	 */
	public static HashSet<String> getCharBetween(char origin, char dest) {
		HashSet<String> res = new HashSet<String>();
		for (char c = origin; c <= dest; c++) {
			res.add("" + c);
		}
		return res;
	}

	/**
	 * @return La liste des caracteres ASCII etendue (de 0 a 255, sauf le caractere \n).
	 */
	public static HashSet<String> getExtendedAsciiChar() {
		HashSet<String> res = new HashSet<String>();
		for (char c = 0; c <= 255; c++) {
			res.add(String.valueOf(c));
		}
		res.remove("\n");
		return res;
	}

	/**
	 * @param s
	 * @return
	 */
	public static HashSet<String> getExtendedAsciiChar(HashSet<String> s) {
		HashSet<String> res = new HashSet<String>();
		for (char c = 0; c <= 255; c++) {
			if (!s.contains(String.valueOf(c)))
				res.add(String.valueOf(c));
		}
		return res;
	}

}
