package Automate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Automate implements Serializable {

    private Etat initial;
    private ArrayList<Etat> finaux;
    private ArrayList<String> alphabet;

    public Automate(Etat initial, ArrayList<Etat> finaux,
	    ArrayList<String> alphabet) {
	super();
	this.initial = initial;
	this.finaux = finaux;
	this.alphabet = alphabet;
    }

    public Etat getInitial() {
	return initial;
    }

    public void setInitial(Etat initial) {
	this.initial = initial;
    }

    public ArrayList<String> getAlphabetList() {
	return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
	this.alphabet = alphabet;
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

    public void setFinaux(ArrayList<Etat> finaux) {
	this.finaux = finaux;
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

    public static boolean accept(Etat e, String mot, Automate a) {
	String tab[] = mot.split("");
	ArrayList<String> lettres = new ArrayList<String>();

	for (int i = 0; i < tab.length; i++) {
	    /*
	     * lettres = new ArrayList<String>();
	     * 
	     * for (int j = i; j < tab.length; j++) { lettres.add(tab[j]); if
	     * (accept2(e, lettres, a)) { return true; } }
	     */

	    if ((i < (tab.length - 1)) && tab[i].equals("\\")) {
		lettres.add("\\" + tab[i + 1]);
		i++;
	    } else {
		lettres.add(tab[i]);
	    }

	}

	return accept2(e, lettres, a);

	// return false;
    }

    @SuppressWarnings("unchecked")
    public static boolean accept2(Etat e, ArrayList<String> lettres, Automate a) {

	/* Si liste vide et etat final ok sinon ko */
	if (lettres.isEmpty()) {
	    if (a.getFinauxList().contains(e)) {
		// System.out.println("Liste vide etat final");
		return true;
	    } else {
		/*
		 * on doit verifier l'existance epsilon transition appel
		 * recursif sinon return false
		 */
		ArrayList<Etat> eps = new ArrayList<Etat>();
		Etat.getNextEtats(e, null, eps);

		for (Etat n : eps) {
		    if (accept2(n, (ArrayList<String>) lettres.clone(), a))
			return true;
		}

		// System.out.println("Liste vide pas etat final");
		return false;
	    }
	}

	String l = lettres.get(0);

	/* Ajouter les cas debut '^' et fin '$' */
	if (l.equals("^")) {
	    if (a.getInitial() != e) {
		return false;
	    } else {
		lettres.remove(0);
		if (accept2(e, (ArrayList<String>) lettres.clone(), a))
		    return true;
	    }
	} else if (l.equals("$")) {
	    if (!a.getFinauxList().contains(e)) {
		return false;
	    } else {
		lettres.remove(0);

		if (!lettres.isEmpty())
		    return false;

		return true;
	    }
	}

	ArrayList<Etat> epsilon = new ArrayList<Etat>();
	ArrayList<Etat> next = Etat.getNextEtats(e, l, epsilon);

	/* cas epsilon transition */
	for (Etat n : epsilon) {
	    /* on continue le parcours de l'automate */
	    if (accept2(n, (ArrayList<String>) lettres.clone(), a))
		return true;
	}

	/* on passe a la lettre suivante car non epsilon transition */
	lettres.remove(0);

	for (Etat n : next) {
	    /* on continue le parcours de l'automate */
	    if (accept2(n, (ArrayList<String>) lettres.clone(), a))
		return true;
	}

	return false;
    }

    @Override
    public Automate clone() {
	ObjectOutputStream out;
	ObjectInputStream in;

	Automate a = null;

	try {
	    out = new ObjectOutputStream(new FileOutputStream("Automate.ser"));
	    out.writeObject(this);
	    out.flush();
	    out.close();

	    in = new ObjectInputStream(new FileInputStream("Automate.ser"));
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
}
