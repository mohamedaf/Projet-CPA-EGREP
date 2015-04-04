package Automate;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Transition implements Serializable {

    private Etat depart;
    private Etat arrivee;
    /* une chaine constitue d'un seul caractere */
    private String caractere;

    public Transition(Etat depart, Etat arrivee, String caractere) {
	this.depart = depart;
	this.arrivee = arrivee;
	this.caractere = caractere;
    }

    public Etat getDepart() {
	return depart;
    }

    public void setDepart(Etat depart) {
	this.depart = depart;
    }

    public Etat getArrivee() {
	return arrivee;
    }

    public void setArrivee(Etat arrivee) {
	this.arrivee = arrivee;
    }

    public String getCaractere() {
	return caractere;
    }

    public void setCaractere(String caractere) {
	this.caractere = caractere;
    }

    @Override
    public Transition clone() {
	return new Transition(depart.clone(), arrivee.clone(), new String(""
		+ caractere));
    }
}
