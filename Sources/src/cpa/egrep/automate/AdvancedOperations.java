package cpa.egrep.automate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
public class AdvancedOperations {

	/**
	 * * Prend un automate deterministe et retourne une minimisation de cet
	 * automate. Algorithme de Brzozowski
	 * 
	 * @param a
	 *            Automate a minimiser
	 * @return L'automate a minimise
	 */
	public static Automate minimisation(Automate a) {
		/*
		 * Ordre : Miroir -> removeEpsilon -> determinisation -> miroir ->
		 * removeEpsilon -> determinisation == minimisation
		 */
		return determiniserAutomate(removeEpsilonTransition(getAutomateMirroir(determiniserAutomate(removeEpsilonTransition(getAutomateMirroir(a))))));
	}

	/**
	 * Retourne l'automate reconaissant le langage mirroir de l'automate a donne
	 * en argument
	 * 
	 * @param a
	 *            un automate
	 * @return Le miroir de a
	 */
	public static Automate getAutomateMirroir(Automate a) {
		/* On clone l'automate en entree */
		Automate a1 = a.clone();

		/* On recupere toutes les transitions de l'automate */
		HashSet<Transition> trans = getAllTransitions(a1.getInitial(),
				new HashSet<Transition>(), new ArrayList<Etat>());
		ArrayList<Transition> newTrans = new ArrayList<Transition>(trans);

		Etat depart;

		/* On inverse toutes les transitions */
		for (int i = 0; i < trans.size(); i++) {
			Transition t = newTrans.get(i);
			depart = t.getDepart();
			t.setDepart(t.getArrivee());
			t.setArrivee(depart);
		}

		HashSet<Etat> etats = Automate.getAllEtats(a1);

		ArrayList<Transition> aAjouter = new ArrayList<Transition>();

		/* On attribue les nouvelles transitions aux etats */
		for (Etat e : etats) {
			aAjouter.clear();

			for (Transition t : newTrans) {
				if (t.getDepart() == e) {
					aAjouter.add(t);
				}
			}
			e.setTransitions(aAjouter);
			newTrans.removeAll(aAjouter);
		}

		/* Tous les etats finaux deviennent des etats initiaux */
		ArrayList<Etat> etatsFinaux = a1.getFinauxList();

		Etat nouveauInitial = new Etat();

		for (Etat e : etatsFinaux) {
			/*
			 * Si un etat final etait une fin de ligne ($) il devient debut de
			 * ligne
			 */
			boolean debut = e.isDebutLigne();
			boolean fin = e.isFinLigne();
			e.setDebutLigne(fin);
			e.setFinLigne(debut);

			nouveauInitial.addTransition(new Transition(nouveauInitial, e,
					"eps"));
		}

		/* Le nouvel etat final est l'ancien etat initial */
		ArrayList<Etat> newFinaux = new ArrayList<Etat>();

		/*
		 * Si l'ancien etat initial etait un debut de ligne (^), il devient fin
		 * de ligne ($) et vis versa.
		 */
		Etat ancienInit = a1.getInitial();
		boolean debut = ancienInit.isDebutLigne();
		boolean fin = ancienInit.isFinLigne();
		ancienInit.setDebutLigne(fin);
		ancienInit.setFinLigne(debut);

		newFinaux.add(a1.getInitial());

		a1.setFinaux(newFinaux);

		a1.setInitial(nouveauInitial);

		return a1;
	}

	/**
	 * Supprime les epsilon transition d'un automate
	 * 
	 * @param a1
	 * @return L'automate a1 sans epsilon transition.
	 */
	public static Automate removeEpsilonTransition(Automate a1) {
		Automate a = a1.clone();

		ArrayList<Etat> etatsFinaux = a.getFinauxList();
		Etat e = removeEpsilonTransition(a.getInitial(), etatsFinaux,
				new HashSet<Etat>());

		return Factory.creerAutomate(e, etatsFinaux, a.getAlphabetList());
	}

	@SuppressWarnings("unchecked")
	private static Etat removeEpsilonTransition(Etat init,
			ArrayList<Etat> etatsFinaux, HashSet<Etat> etatsVisites) {

		if (etatsVisites.contains(init))
			return init;
		etatsVisites.add(init);

		/*
		 * On passe 2 fois sur chaque etat car il se peut qu'apres la premiere
		 * iteration on ait recupere des epsilon transition de l'etat suivant
		 */
		for (int i = 0; i < 2; i++) {
			for (Transition t : (ArrayList<Transition>) init
					.getTransitionsList().clone()) {
				boolean estEtatFinal = etatsFinaux.contains(t.getArrivee());

				/*
				 * On eneleve recursivement les epsilon transition sortant des
				 * etats suivants
				 */
				Etat tmp = removeEpsilonTransition(t.getArrivee(), etatsFinaux,
						etatsVisites);

				/* Si t est une epsilon transition */
				if (t.getCaractere().equals("eps")) {

					/* S'il y a un epsilon sur le meme etat */
					if (t.getDepart() == t.getArrivee()) {
						init.removeTransition(t);
						continue;
					}

					/* On ajoute les transitions a l'etat actuel */
					for (Transition t2 : (ArrayList<Transition>) tmp
							.getTransitionsList().clone()) {

						Etat arrivee = t2.getArrivee();

						boolean dejaPresent = false;
						for (Transition t3 : (ArrayList<Transition>) init
								.getTransitionsList().clone()) {
							if (t3.getArrivee() == arrivee
									&& t3.getCaractere().equals(
											t2.getCaractere())) {
								dejaPresent = true;
								break;
							}
						}
						if (!dejaPresent)
							init.addTransition(new Transition(init, arrivee, t2
									.getCaractere()));
					}

					if (tmp.isDebutLigne())
						init.setDebutLigne(true);
					if (tmp.isFinLigne())
						init.setFinLigne(true);

					if (!init.removeTransition(t)) {
						System.err.println("erreur suppression");
					}
					if (init.getTransitionsList().contains(t)) {
						System.err.println("erreur suppression");
					}

					/*
					 * Si c'etait un etat final, on l'enleve de la liste des
					 * etats finaux, et on ajoute l'etat actuel aux etas finaux
					 */
					if (estEtatFinal) {
						if (!etatsFinaux.contains(init)) {
							etatsFinaux.add(init);
						}
					}
				}
			}
		}

		return init;
	}

	/**
	 * Determinise un automate
	 * 
	 * @param a
	 * @return L'automate determinise.
	 */
	public static Automate determiniserAutomate(Automate a) {
		/* Q est initialise a vide et soit E une pile d'etats */
		Automate newA = Factory.creerAutomate();
		ArrayList<Etat> Q = new ArrayList<Etat>();
		ArrayList<Etat> tmp = new ArrayList<Etat>();
		ArrayList<Etat> epsilon;
		Stack<Etat> E = new Stack<Etat>();
		Etat S, S2;
		int trouve;
		tmp.add(a.getInitial());
		newA.setInitial(new Etat(tmp));
		newA.setAlphabet(a.getAlphabetList());
		/* la pile d'etat contient initialement l'etat contenant {etat initial} */
		E.push(newA.getInitial());
		while (!E.isEmpty()) {
			/* choisir un element S de E */
			S = E.pop();
			Q.add(S);
			/* pour tout simbole de l'alphabet */
			for (String st : a.getAlphabetList()) {
				S2 = new Etat();
				trouve = 0;
				/*
				 * 
				 * calculer l'etat S2 contenant tous les etats atteignables
				 * 
				 * depuis S avec le caractere st
				 */
				for (Etat e : S.getEtatsDetList()) {
					epsilon = new ArrayList<Etat>();
					S2.addAllEtatDet(Etat.getNextEtats(e, st, epsilon));
				}
				/* On verifie si S2 est dans la liste Q */
				for (Etat e : Q) {
					if (e.getEtatsDetList().equals(S2.getEtatsDetList())) {
						trouve = 1;
						break;
					}
				}

				/* S2 n'est pas dans la liste Q on l'ajoute donc a la pile E */
				if (trouve == 0)
					E.push(S2);
				/* Ajouter un arc entre S et S2 et la valuer par st */
				S.addTransition(new Transition(S, S2, st));
			}
		}
		/* Ajouter les etats finaux */
		for (Etat qe : Q) {
			for (Etat e : qe.getEtatsDetList()) {
				if (a.getFinauxList().contains(e)) {
					newA.addEtatFinal(qe);
					break;
				}
			}
		}
		return newA;
	}

	/**
	 * @param init
	 *            Etat initial
	 * @param trans
	 *            Hash de transitions
	 * @param etatsVisites
	 *            Etats deja visites
	 * @return Toutes les transitions d'un automate dont l'etat initial est init
	 */
	private static HashSet<Transition> getAllTransitions(Etat init,
			HashSet<Transition> trans, ArrayList<Etat> etatsVisites) {
		if (etatsVisites.contains(init))
			return new HashSet<Transition>();
		etatsVisites.add(init);

		for (Transition t : init.getTransitionsList()) {
			if (!trans.contains(t)) {
				trans.add(t);
			}
			trans.addAll(getAllTransitions(t.getArrivee(), trans, etatsVisites));
		}
		return trans;
	}

	/**
	 * Verifie que l'automate est complet
	 * 
	 * @param a
	 * @return true si l'automate est complet, false sinon.
	 */
	public static Boolean estAutomateComplet(Automate a, Etat e) {
		ArrayList<Etat> next;
		ArrayList<Etat> epsilon = new ArrayList<Etat>();
		for (String s : a.getAlphabetList()) {
			next = Etat.getNextEtats(e, s, epsilon);
			/* Pas de transition pour cette lettre */
			if (next == null)
				return false;
			else {
				for (Etat es : epsilon) {
					/* Si false retournee pas besoin de continuer */
					if (!estAutomateComplet(a, es)) {
						return false;
					}
				}
				for (Etat es : next) {
					/* Si false retournee pas besoin de continuer */
					if (!estAutomateComplet(a, es)) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
