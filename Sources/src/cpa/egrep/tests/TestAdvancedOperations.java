package cpa.egrep.tests;

import java.util.ArrayList;

import cpa.egrep.automate.AdvancedOperations;
import cpa.egrep.automate.Automate;
import cpa.egrep.automate.Etat;
import cpa.egrep.automate.Factory;
import cpa.egrep.automate.Transition;
import cpa.egrep.automate.Utils;

public class TestAdvancedOperations {

	public static void main(String[] args) {
		test1();

	}

	/**
	 * Test de remove Epsilon, determinisation, minimisation. Sortie : fichiers
	 * graphviz
	 */
	private static void test1() {
		ArrayList<String> alphabet = new ArrayList<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		alphabet.add("d");
		alphabet.add("e");

		Etat e1 = Factory.creerEtat();
		Etat e2 = Factory.creerEtat();
		Etat e3 = Factory.creerEtat();
		Etat e4 = Factory.creerEtat();
		Etat e5 = Factory.creerEtat();
		Etat e6 = Factory.creerEtat();
		Etat e7 = Factory.creerEtat();

		e1.addTransition(new Transition(e1, e2, "a"));
		e1.addTransition(new Transition(e1, e3, "a"));

		ArrayList<Etat> finaux1 = new ArrayList<Etat>();
		finaux1.add(e2);
		finaux1.add(e3);
		Automate a1 = Factory.creerAutomate(e1, finaux1, alphabet);
		a1 = Factory.Plus(a1);

		e4.addTransition(new Transition(e4, e5, "b"));
		e4.addTransition(new Transition(e4, e7, "e"));

		e5.addTransition(new Transition(e5, e6, "c"));
		e5.addTransition(new Transition(e5, e7, "d"));

		e6.addTransition(new Transition(e6, e7, "e"));

		ArrayList<Etat> finaux2 = new ArrayList<Etat>();
		finaux2.add(e7);
		Automate a2 = Factory.creerAutomate(e4, finaux2, alphabet);

		Automate a = Factory.concatenation(a1, a2);

		Utils.automateToGraphViz(a, "advancedGraphs/automate1.graph");
		a = AdvancedOperations.removeEpsilonTransition(a);
		Utils.automateToGraphViz(a, "advancedGraphs/automate1SansEps.graph");
	}
}
