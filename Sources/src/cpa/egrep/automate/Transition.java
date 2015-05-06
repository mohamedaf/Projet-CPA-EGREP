package cpa.egrep.automate;

import java.io.Serializable;

/**
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
@SuppressWarnings("serial")
public class Transition implements Serializable {

	/**
	 * Etat de depart
	 */
	private Etat depart;
	/**
	 * Etat d'arrivee
	 */
	private Etat arrivee;
	/**
	 * Caractere de la transition
	 */
	private String caractere;

	public Transition(Etat depart, Etat arrivee, String caractere) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.caractere = caractere;
	}

	/**
	 * @return L'etat de depart.
	 */
	public Etat getDepart() {
		return depart;
	}

	/**
	 * @param depart
	 */
	public void setDepart(Etat depart) {
		this.depart = depart;
	}

	/**
	 * @return L'etat d'arrivee.
	 */
	public Etat getArrivee() {
		return arrivee;
	}

	/**
	 * @param arrivee
	 */
	public void setArrivee(Etat arrivee) {
		this.arrivee = arrivee;
	}

	/**
	 * @return Le caractere de la transition.
	 */
	public String getCaractere() {
		return caractere;
	}

	/**
	 * @param caractere
	 */
	public void setCaractere(String caractere) {
		this.caractere = caractere;
	}

	@Override
	public Transition clone() {
		return new Transition(depart.clone(), arrivee.clone(), new String(""
				+ caractere));
	}
}
