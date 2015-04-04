package Automate;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Etat implements Serializable {

    private ArrayList<Transition> transitions;

    public Etat() {
	this.transitions = new ArrayList<Transition>();
    }

    public Etat(ArrayList<Transition> transitions) {
	this.transitions = transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
	this.transitions = transitions;
    }

    public void addTransition(Transition t) {
	transitions.add(t);
    }

    public Transition getTransition(int i) {
	return transitions.get(i);
    }

    public static ArrayList<Etat> getNextEtats(Etat depart, String c,
	    ArrayList<Etat> epsilon) {
	if (depart == null)
	    return null;

	ArrayList<Etat> arrivees = new ArrayList<Etat>();
	/* afin d'etre sur que la liste est vide */
	epsilon.removeAll(epsilon);

	/* Traiter le cas du point egal a n'importe quel caractere */
	if (c.equals(".")) {
	    for (Transition t : depart.getTransitionsList()) {
		arrivees.add(t.getArrivee());
	    }
	} else {
	    for (Transition t : depart.getTransitionsList()) {
		if (t.getCaractere().equals("eps"))
		    epsilon.add(t.getArrivee());
		else if (t.getCaractere().equals(c))
		    arrivees.add(t.getArrivee());
	    }
	}

	return arrivees;
    }

    public void removeTransition(int i) {
	transitions.remove(i);
    }

    public ArrayList<Transition> getTransitionsList() {
	return transitions;
    }

    @Override
    public Etat clone() {
	ArrayList<Transition> transClo = new ArrayList<Transition>();

	for (Transition t : transitions) {
	    transClo.add(t.clone());
	}

	return new Etat(transClo);
    }
}
