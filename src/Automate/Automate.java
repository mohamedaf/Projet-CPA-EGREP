package Automate;

import java.util.ArrayList;

public class Automate {

    private Etat initial;
    private ArrayList<Etat> finaux;
    private ArrayList<String> alphabet;

    public Automate(Etat initial, ArrayList<Etat> finaux,
	    ArrayList<String> caracteres) {
	super();
	this.initial = initial;
	this.finaux = finaux;
	this.alphabet = caracteres;
    }

    public Etat getInitial() {
	return initial;
    }

    public void setInitial(Etat initial) {
	this.initial = initial;
    }

    public ArrayList<Etat> getFinaux() {
	return finaux;
    }

    public void setFinaux(ArrayList<Etat> finaux) {
	this.finaux = finaux;
    }

    public ArrayList<String> getCaracteres() {
	return alphabet;
    }

    public void setCaracteres(ArrayList<String> caracteres) {
	this.alphabet = caracteres;
    }

    public void addEtatFinal(Etat t) {
	finaux.add(t);
    }

    public Etat getEtatFinal(int i) {
	return finaux.get(i);
    }

    public void removeEtatFinal(int i) {
	finaux.remove(i);
    }

    public ArrayList<Etat> getFinauxList() {
	return finaux;
    }

    public void addCaractere(String c) {
	alphabet.add(c);
    }

    public String getCaractere(int i) {
	return alphabet.get(i);
    }

    public void removeCaractere(int i) {
	alphabet.remove(i);
    }

    public ArrayList<String> getCaracteresList() {
	return alphabet;
    }

    /* A revoir pour le cas ou il y'a une epsilon transition */
    public static boolean accept(Etat e, ArrayList<String> lettres, Automate a) {

	/* Si liste vide et etat final ok sinon ko */
	if (lettres.isEmpty()) {
	    if (a.getFinauxList().contains(e)) {
		System.out.println("Liste vide etat final");
		return true;
	    } else {
		System.out.println("Liste vide pas etat final");
		return false;
	    }
	}

	String l = lettres.get(0);
	lettres.remove(0);
	ArrayList<Etat> next = e.getNextEtats(e, l);

	/* s'il n'ya pas d'etat suivant pour la lettre courante ko */
	if (next.isEmpty())
	    return false;

	for (Etat n : next) {
	    /* on continue le parcours de l'automate */
	    if (accept(n, lettres, a))
		return true;
	}

	return false;
    }
}
