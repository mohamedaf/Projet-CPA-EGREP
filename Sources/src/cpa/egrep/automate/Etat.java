package cpa.egrep.automate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
/**
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
@SuppressWarnings("serial")
public class Etat implements Serializable {

	/**
	 * Liste des transitions sortantes
	 */
	private ArrayList<Transition> transitions;
	/**
	 * Vrai si l'etat est un debut de ligne (regex commencant par '^')
	 */
	private boolean debutLigne;
	/**
	 * Vrai si l'etat est une fin de ligne (regex terminant par '$')
	 */
	private boolean finLigne;
	/**
	 * 
	 */
	private ArrayList<Etat> etatsDet;

	/**
	 * @return Vrai si l'etat est un debut de ligne (regex commencant par '^')
	 */
	public boolean isDebutLigne() {
		return debutLigne;
	}

	/**
	 * @param debutLigne
	 */
	public void setDebutLigne(boolean debutLigne) {
		this.debutLigne = debutLigne;
	}

	/**
	 * @return Vrai si l'etat est une fin de ligne (regex terminant par '$')
	 */
	public boolean isFinLigne() {
		return finLigne;
	}

	/**
	 * @param finLigne
	 */
	public void setFinLigne(boolean finLigne) {
		this.finLigne = finLigne;
	}

	public Etat() {
		this.transitions = new ArrayList<Transition>();
		debutLigne = false;
		finLigne = false;
		this.etatsDet = new ArrayList<Etat>();
	}

	public Etat(ArrayList<Etat> etatsDet) {
		this.transitions = new ArrayList<Transition>();
		this.etatsDet = etatsDet;
		debutLigne = false;
		finLigne = false;
	}

	/**
	 * @param transitions
	 */
	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	/**
	 * @param t
	 */
	public void addTransition(Transition t) {
		transitions.add(t);
	}

	/**
	 * @param list
	 */
	public void addAllTransitions(Collection<Transition> list) {
		transitions.addAll(list);
	}

	/**
	 * @param i
	 * @return
	 */
	public Transition getTransition(int i) {
		return transitions.get(i);
	}

	/**
	 * @param depart
	 * @param c
	 * @param epsilon
	 * @return
	 */
	public static ArrayList<Etat> getNextEtats(Etat depart, String c,
			ArrayList<Etat> epsilon) {
		if (depart == null)
			return null;

		ArrayList<Etat> arrivees = new ArrayList<Etat>();
		/* afin d'etre sur que la liste est vide */
		epsilon.removeAll(epsilon);

		/* Traiter le cas du point egal a n'importe quel caractere */
		if ((c != null) && c.equals(".")) {
			for (Transition t : depart.getTransitionsList()) {
				if (t.getCaractere().equals("eps"))
					epsilon.add(t.getArrivee());
				else
					arrivees.add(t.getArrivee());
			}
		} else {
			if ((c != null) && c.equals("\\."))
				c = new String(".");

			for (Transition t : depart.getTransitionsList()) {
				if (t.getCaractere().equals("eps"))
					epsilon.add(t.getArrivee());
				else if (t.getCaractere().equals(c))
					arrivees.add(t.getArrivee());
			}
		}

		return arrivees;
	}

	/**
	 * @param i
	 */
	public void removeTransition(int i) {
		transitions.remove(i);
	}

	/**
	 * @param t
	 * @return
	 */
	public boolean removeTransition(Transition t) {
		return transitions.remove(t);
	}

	/**
	 * @return
	 */
	public ArrayList<Transition> getTransitionsList() {
		return transitions;
	}

	@Override
	public Etat clone() {
		ArrayList<Transition> transClo = new ArrayList<Transition>();

		for (Transition t : transitions) {
			transClo.add(t.clone());
		}
		Etat fin = new Etat();
		fin.setTransitions(transClo);
		return fin;
	}

	/**
	 * @return
	 */
	public ArrayList<Etat> getEtatsDetList() {
		return etatsDet;
	}

	/**
	 * @param etatsDet
	 */
	public void setEtatsDet(ArrayList<Etat> etatsDet) {
		this.etatsDet = etatsDet;
	}

	/**
	 * @param e
	 */
	public void addEtatDet(Etat e) {
		this.etatsDet.add(e);
	}

	/**
	 * @param e
	 */
	public void addAllEtatDet(ArrayList<Etat> e) {
		this.etatsDet.addAll(e);
	}

	/**
	 * @param e
	 */
	public void removeEtatDet(Etat e) {
		this.etatsDet.remove(e);
	}
}
