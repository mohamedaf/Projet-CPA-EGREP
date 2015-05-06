package cpa.egrep.automate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
@SuppressWarnings("serial")
public class Automate implements Serializable {

	/**
	 * Etat initial de l'automate
	 */
	private Etat initial;
	/**
	 * Liste des etats finaux de l'automate
	 */
	private ArrayList<Etat> finaux;
	/**
	 * Alphabet de l'automate
	 */
	private ArrayList<String> alphabet;

	public Automate(Etat initial, ArrayList<Etat> finaux,
			ArrayList<String> alphabet) {
		super();
		this.initial = initial;
		this.finaux = finaux;
		this.alphabet = alphabet;
	}

	/**
	 * @return L'etat initial de l'automate
	 */
	public Etat getInitial() {
		return initial;
	}

	/**
	 * @param initial
	 *            Etat initial a attribuer a l'automate
	 */
	public void setInitial(Etat initial) {
		this.initial = initial;
	}

	/**
	 * @return
	 */
	public ArrayList<String> getAlphabetList() {
		return alphabet;
	}

	/**
	 * @param alphabet
	 */
	public void setAlphabet(ArrayList<String> alphabet) {
		this.alphabet = alphabet;
	}

	/**
	 * @param c
	 *            Caractere a ajouter a l'alphabet
	 */
	public void addCaractere(String c) {
		alphabet.add(c);
	}

	/**
	 * @param i
	 * @return
	 */
	public String getCaractere(int i) {
		return alphabet.get(i);
	}

	/**
	 * @param i
	 */
	public void removeCaractere(int i) {
		alphabet.remove(i);
	}

	/**
	 * @param finaux
	 *            Liste des etats finaux a attribuer a l'automate
	 */
	public void setFinaux(ArrayList<Etat> finaux) {
		this.finaux = finaux;
	}

	/**
	 * Ajoute un etat final
	 * 
	 * @param t
	 *            Etat a ajouter
	 */
	public void addEtatFinal(Etat t) {
		finaux.add(t);
	}

	/**
	 * @param i
	 * @return
	 */
	public Etat getEtatFinal(int i) {
		return finaux.get(i);
	}

	/**
	 * @param i
	 */
	public void removeEtatFinal(int i) {
		finaux.remove(i);
	}

	/**
	 * @return La liste des etats finaux
	 */
	public ArrayList<Etat> getFinauxList() {
		return finaux;
	}

	@Override
	public Automate clone() {
		ObjectOutputStream out;
		ObjectInputStream in;
		File f = new File("Automate.ser");
		f.deleteOnExit();
		Automate a = null;

		try {
			out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(this);
			out.flush();
			out.close();

			in = new ObjectInputStream(new FileInputStream(f));
			/* Lecture de la copie de l'automate */
			a = (Automate) in.readObject();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		}

		return a;
	}

	/**
	 * Teste l'automate sur une ligne de texte
	 * 
	 * @param line
	 *            Ligne a tester
	 * @param a
	 *            Automate de test
	 * @return true si le texte est matché, false sinon.
	 */
	public static boolean accept(String line, Automate a) {
		int length = line.length();
		int cpt = 0;
		if (a.getInitial().isDebutLigne()) {
			return accept(a.getInitial(), line, a);
		}

		while (cpt < length) {
			if (accept(a.getInitial(), line.substring(cpt), a)) {
				return true;
			}
			cpt++;
		}
		return false;
	}

	private static boolean accept(Etat init, String line, Automate a) {
		/* SI on est un etat final de l'automate */
		for (Etat e : a.getFinauxList()) {
			if (init == e || init.equals(e)) {
				/*
				 * Si l'etat final represente une fin de ligne, il faut que
				 * line.length() == 0
				 */
				if (init.isFinLigne()) {
					return line.length() == 0;
				}
				return true;
			}
		}

		String car = "";

		if (line.length() > 0)
			car += line.charAt(0);

		for (Transition t : init.getTransitionsList()) {
			if (t.getCaractere().equals("eps")
					&& accept(t.getArrivee(), line, a)) {
				return true;
			} else if (t.getCaractere().equals(car)
					&& accept(t.getArrivee(), line.substring(1), a)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param a
	 * @return Le nombre de transitions de l'automate.
	 */
	public static int getNbTransitions(Automate a) {
		return getNbTransitions(a.getInitial(), new HashSet<Etat>());
	}

	private static int getNbTransitions(Etat e, HashSet<Etat> etatsVisites) {
		if (dejaVisite(e, etatsVisites))
			return 0;

		int cpt = 0;
		etatsVisites.add(e);
		for (Transition t : e.getTransitionsList()) {
			cpt += getNbTransitions(t.getArrivee(), etatsVisites);
		}
		return e.getTransitionsList().size() + cpt;
	}

	/**
	 * @param a
	 * @return Le nombre d'epsilon transitions de l'automate.
	 */
	public static int getNbEpsilonTransitions(Automate a) {
		return getNbEpsilonTransitions(a.getInitial(), new HashSet<Etat>());
	}

	public static int getNbEpsilonTransitions(Etat e, HashSet<Etat> etatsVisites) {
		if (dejaVisite(e, etatsVisites))
			return 0;

		int cpt = 0;
		etatsVisites.add(e);
		for (Transition t : e.getTransitionsList()) {
			if (t.getCaractere().equals("eps"))
				cpt++;
			cpt += getNbEpsilonTransitions(t.getArrivee(), etatsVisites);
		}
		return cpt;
	}

	private static boolean dejaVisite(Etat e, HashSet<Etat> list) {
		for (Etat tmp : list) {
			if (e == tmp)
				return true;
		}
		return false;
	}

	/**
	 * @param a
	 * @return Tous les etats de l'automate a.
	 */
	public static HashSet<Etat> getAllEtats(Automate a) {
		return getAllEtats(a.getInitial(), new HashSet<Etat>());
	}

	private static HashSet<Etat> getAllEtats(Etat init,
			HashSet<Etat> dejaVisites) {
		if (dejaVisites.contains(init))
			return new HashSet<Etat>();

		dejaVisites.add(init);
		for (Transition t : init.getTransitionsList()) {
			dejaVisites.addAll(getAllEtats(t.getArrivee(), dejaVisites));
		}
		return dejaVisites;
	}

	/**
	 * @param a
	 * @return Toutes les transitions de a.
	 */
	public HashSet<Transition> getAllTransitions(Automate a) {
		return getAllTransitions(a.getInitial(), new HashSet<Etat>());
	}

	private HashSet<Transition> getAllTransitions(Etat init,
			HashSet<Etat> dejaVisites) {
		if (dejaVisites.contains(init))
			return new HashSet<Transition>();

		dejaVisites.add(init);
		ArrayList<Transition> transitionList = init.getTransitionsList();

		/* On ajoute toutes les transitions sortantes de l'etat actuel */
		HashSet<Transition> res = new HashSet<Transition>(transitionList);

		/* On ajoute recursivement les transitions des etats suivants */
		for (Transition t : transitionList) {
			res.addAll(getAllTransitions(t.getArrivee(), dejaVisites));
		}

		return res;
	}

}