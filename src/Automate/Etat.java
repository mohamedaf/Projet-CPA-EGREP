package Automate;

import java.util.ArrayList;

public class Etat {

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

    public ArrayList<Etat> getNextEtats(Etat depart, String c) {
	ArrayList<Etat> arrivees = new ArrayList<Etat>();

	/* Traiter le cas du point egal a n'importe quel caractere */
	if (c.equals(new String("."))) {
	    for (Transition t : transitions) {
		arrivees.add(t.getArrivee());
	    }
	} else {
	    for (Transition t : transitions) {
		if (t.getDepart() == depart && t.getCaractere().equals(c))
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
}
