package cpa.egrep.tests;

import java.util.ArrayList;

import cpa.egrep.automate.AdvancedOperations;
import cpa.egrep.automate.Automate;
import cpa.egrep.automate.Etat;
import cpa.egrep.automate.Factory;
import cpa.egrep.automate.Transition;
import cpa.egrep.automate.Utils;

/**
 * Classe des tests
 * 
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
public class TestAutomates {

	public static void main(String[] args) {

		/* Tous les tests fonctionnent */
		System.out.print("0 ");
		TestAutomate0();
		System.out.print("1 ");
		TestAutomate1();
		System.out.print("2 ");
		TestAutomate2();
		System.out.print("3 ");
		TestAutomate3();
		System.out.print("4 ");
		TestAutomate4();
		System.out.print("5 ");
		TestAutomate5();
		System.out.print("6 ");
		TestAutomate6();
		System.out.print("7 ");
		TestAutomate7();
		System.out.print("8 ");
		TestAutomate8();
		System.out.print("9 ");
		TestAutomate9();
		System.out.print("10 ");
		TestAutomate10();
		System.out.print("11 ");
		TestAutomate11();
		System.out.print("12 ");
		TestAutomate12();
		System.out.print("13 ");
		TestAutomate13();
		System.out.print("14 ");
		TestAutomate14();
		System.out.print("15 ");
		TestAutomate15();
		System.out.print("16 ");
		TestAutomate16();
		System.out.print("17 ");
		TestAutomate17();
		System.out.print("18 ");
		TestAutomate18();
		System.out.print("19 ");
		TestAutomate19();
		System.out.print("20 ");
		TestAutomate20();
		System.out.print("21 ");
		TestAutomate21();
		System.out.print("22 ");
		TestAutomate22();
		System.out.print("23 ");
		TestAutomate23();
		System.out.print("24 ");
		TestAutomate24();

	}

	public static void TestAutomate0() {
		Etat e = new Etat();
		e.addTransition(new Transition(e, e, "a"));

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		ArrayList<String> alphabet = new ArrayList<String>();
		finaux.add(e);
		alphabet.add("a");

		Automate a = new Automate(e, finaux, alphabet);

		// a = AdvancedOperations.determiniser(a);

		if (Automate.accept("aabbabbb", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");

	}

	public static void TestAutomate1() {
		Etat e0, e1, e2, e3, e4, e5, e6, e7, e8, e9;
		ArrayList<Etat> finaux = new ArrayList<Etat>();
		ArrayList<String> alphabet = new ArrayList<String>();

		/* Initialisation de l'automate */

		alphabet.add("a");
		alphabet.add("b");

		e0 = new Etat();
		e1 = new Etat();
		e2 = new Etat();
		e3 = new Etat();
		e4 = new Etat();
		e5 = new Etat();
		e6 = new Etat();
		e7 = new Etat();
		e8 = new Etat();
		e9 = new Etat();

		e0.addTransition(new Transition(e0, e6, "a"));
		e0.addTransition(new Transition(e0, e1, "b"));

		e1.addTransition(new Transition(e1, e2, "a"));
		e1.addTransition(new Transition(e1, e1, "b"));

		e2.addTransition(new Transition(e2, e3, "b"));

		e3.addTransition(new Transition(e3, e4, "b"));

		e4.addTransition(new Transition(e4, e5, "b"));

		e6.addTransition(new Transition(e6, e6, "a"));
		e6.addTransition(new Transition(e6, e7, "b"));

		e7.addTransition(new Transition(e7, e2, "a"));
		e7.addTransition(new Transition(e7, e8, "b"));

		e8.addTransition(new Transition(e8, e2, "a"));
		e8.addTransition(new Transition(e8, e9, "b"));

		e9.addTransition(new Transition(e9, e2, "a"));
		e9.addTransition(new Transition(e9, e1, "b"));

		finaux.add(e5);
		finaux.add(e9);

		Automate a = new Automate(e0, finaux, alphabet);

		/* test sur des expressions donnees si l'automate accept4e */

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("a");
		texte.add("b");
		texte.add("b");
		texte.add("a");
		texte.add("b");
		texte.add("b");
		texte.add("b");

		// a = AdvancedOperations.determiniser(a);

		if (Automate.accept("aabbabbb", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate2() {
		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("b"));

		Automate a3 = Factory.concatenation(a1, a2);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("b");

		// a3 = AdvancedOperations.determiniser(a3);

		if (Automate.accept("ab", a3))
			System.out.println("youpie");
		else
			System.out.println("erreur");

	}

	public static void TestAutomate3() {
		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("b"));

		Automate a3 = Factory.union(a1, a2);

		ArrayList<String> texte = new ArrayList<String>();

		// texte.add("a");
		texte.add("b");

		// a3 = AdvancedOperations.determiniser(a3);

		if (Automate.accept("b", a3))
			System.out.println("youpie");
		else
			System.out.println("erreur");

	}

	public static void TestAutomate4() {
		/* ^acb$ */

		Automate a1 = Factory.creerAutomate();
		Automate a2 = Factory.creerAutomate(new String("a"));
		Automate a3 = Factory.creerAutomate(new String("c"));
		Automate a4 = Factory.creerAutomate(new String("b"));
		Automate a5 = Factory.creerAutomate();

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(
				Factory.concatenation(Factory.concatenation(a1, a2),
						Factory.concatenation(a3, a4)), a5);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("^");
		texte.add("a");
		texte.add("c");
		texte.add("b");
		texte.add("$");

		// a = AdvancedOperations.determiniser(a);

		if (Automate.accept("^acb$", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate5() {
		/* ab*t */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("b"));
		a2 = Factory.Star(a2);
		Automate a3 = Factory.creerAutomate(new String("t"));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(Factory.concatenation(a1, a2), a3);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		/* fonctionne aussi si aucun b ou un seul b */
		texte.add("b");
		texte.add("b");
		texte.add("b");
		texte.add("t");

		// a = AdvancedOperations.determiniser(a);

		Utils.automateToGraphViz(a, "testAutomate5Eps.graph");
		Utils.automateToGraphViz(AdvancedOperations.removeEpsilonTransition(a),
				"testAutomate5.graph");

		if (Automate.accept("abbt", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate6() {
		/* ab?t */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("b"));
		a2 = Factory.questionMark(a2);
		Automate a3 = Factory.creerAutomate(new String("t"));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(Factory.concatenation(a1, a2), a3);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		/* fonctionne aussi si aucun b */
		texte.add("b");
		texte.add("t");

		// a = AdvancedOperations.determiniser(a);

		if (Automate.accept("abt", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate7() {
		/* abs+ */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("b"));
		Automate a3 = Factory.Plus(Factory.creerAutomate(new String("s")));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(Factory.concatenation(a1, a2), a3);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("b");
		/* Fonctionne pour au moins un s */
		texte.add("s");

		if (Automate.accept("abs", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate8() {
		/* sa*l+u?t */

		Automate a1 = Factory.creerAutomate(new String("s"));
		Automate a2 = Factory.Star(Factory.creerAutomate(new String("a")));
		Automate a3 = Factory.Plus(Factory.creerAutomate(new String("l")));
		Automate a4 = Factory.questionMark(Factory
				.creerAutomate(new String("u")));
		Automate a5 = Factory.creerAutomate(new String("t"));
		;

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(
				Factory.concatenation(Factory.concatenation(a1, a2),
						Factory.concatenation(a3, a4)), a5);

		Utils.automateToGraphViz(a, "testAutomate8Eps.graph");

		Utils.automateToGraphViz(AdvancedOperations.removeEpsilonTransition(a),
				"testAutomate8.graph");

		if (Automate.accept("salut", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate9() {
		/* ar(ts)* */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("r"));
		Automate a3 = Factory.Star(Factory.concatenation(
				Factory.creerAutomate(new String("t")),
				Factory.creerAutomate(new String("s"))));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(Factory.concatenation(a1, a2), a3);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("r");
		texte.add("t");
		texte.add("s");
		texte.add("t");
		texte.add("s");

		if (Automate.accept("atsarts", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate10() {
		/* ap.k */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("p"));
		Automate a3 = Factory.creerAutomateFromMatchingList(Factory
				.getExtendedAsciiChar());
		Automate a4 = Factory.creerAutomate(new String("+"));
		Automate a5 = Factory.creerAutomate(new String("k"));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(
				Factory.concatenation(Factory.concatenation(a1, a2),
						Factory.concatenation(a3, a4)), a5);

		if (Automate.accept("bonjour est est ce que apx+k  ", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate11() {
		/* ap{2} */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("p"));
		a2 = Factory.ERE_dupl_symbol(a2, new String("2"));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(a1, a2);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("p");
		texte.add("a");
		texte.add("p");
		texte.add("p");

		Utils.automateToGraphViz(AdvancedOperations.removeEpsilonTransition(a),
				"test11.graph");

		if (Automate.accept("app", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate12() {
		/* ap{2,} */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("p"));
		a2 = Factory.ERE_dupl_symbol(a2, new String("2,hk"));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(a1, a2);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("p");
		texte.add("p");
		texte.add("p");

		if (Automate.accept("app", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate13() {
		/* ap{2,3} */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("p"));
		a2 = Factory.ERE_dupl_symbol(a2, new String("2,3"));

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(a1, a2);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("p");
		texte.add("p");
		texte.add("p");
		// texte.add("p");

		if (Automate.accept("apppp", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate14() {
		/* a\.{2,3}$ */

		Automate a1 = Factory.creerAutomate(new String("a"));
		Automate a2 = Factory.creerAutomate(new String("."));
		a2 = Factory.ERE_dupl_symbol(a2, new String("2,3"));
		Automate a3 = Factory.creerAutomate();

		/* a1 -> a2 -> a3 -> a4 -> a5 */
		Automate a = Factory.concatenation(Factory.concatenation(a1, a2), a3);

		ArrayList<String> texte = new ArrayList<String>();

		texte.add("a");
		texte.add("\\.");
		texte.add("\\.");
		texte.add("\\.");
		// texte.add("\\.");

		testRemoveEpsilon(a);

		if (Automate.accept("a...", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate15() {
		Automate a1;
		Etat e1 = new Etat();
		Etat e2 = new Etat();
		Etat e3 = new Etat();
		Etat e4 = new Etat();

		Transition t1 = new Transition(e1, e2, "eps");
		Transition t2 = new Transition(e2, e3, "eps");
		Transition t3 = new Transition(e3, e4, "a");

		ArrayList<Etat> etatsFinaux = new ArrayList<Etat>();
		e1.addTransition(t1);
		e2.addTransition(t2);
		e3.addTransition(t3);

		etatsFinaux.add(e4);

		ArrayList<String> alphabet = new ArrayList<String>();
		alphabet.add("a");

		a1 = new Automate(e1, etatsFinaux, alphabet);

		int i = Automate.getNbEpsilonTransitions(a1);

		int j = Automate.getNbTransitions(a1);

		System.out.println("Nb epsilon transitions (normalement 2) : " + i);
		System.out.println("Nb transitions (normalement 3) : " + j);
	}

	public static void TestAutomate16() {
		Automate a1;
		Etat e1 = new Etat();
		Etat e2 = new Etat();
		Etat e3 = new Etat();
		Etat e4 = new Etat();

		Transition t1 = new Transition(e1, e2, "eps");
		Transition t2 = new Transition(e2, e3, "eps");
		Transition t3 = new Transition(e3, e4, "a");

		ArrayList<Etat> etatsFinaux = new ArrayList<Etat>();
		e1.addTransition(t1);
		e2.addTransition(t2);
		e3.addTransition(t3);

		etatsFinaux.add(e4);

		ArrayList<String> alphabet = new ArrayList<String>();
		alphabet.add("a");

		a1 = new Automate(e1, etatsFinaux, alphabet);

		a1 = AdvancedOperations.removeEpsilonTransition(a1);

		int i = Automate.getNbEpsilonTransitions(a1);
		int j = Automate.getNbTransitions(a1);

		System.out.println("Nb epsilon transitions (normalement 0) : " + i);
		System.out.println("Nb transitions (normalement 1) : " + j);
	}

	public static void TestAutomate17() {
		Etat e = new Etat();
		Etat e1 = new Etat();

		Transition t = new Transition(e, e1, "a");
		e.addTransition(t);

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		ArrayList<String> alphabet = new ArrayList<String>();
		finaux.add(e1);
		alphabet.add("a");

		Automate a = new Automate(e, finaux, alphabet);

		if (Automate.accept("a", a))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate18() {
		Automate a = Factory.creerAutomateDebutLigne();

		Etat e1 = new Etat();
		Etat e2 = new Etat();

		Transition t = new Transition(e1, e2, "a");
		e1.addTransition(t);

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		ArrayList<String> alphabet = new ArrayList<String>();
		finaux.add(e2);
		alphabet.add("a");

		Automate a1 = new Automate(e1, finaux, alphabet);
		a1 = Factory.concatenation(a, a1);

		if (Automate.accept("afkfkr", a1))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate19() {
		Automate a = Factory.creerAutomateFinLigne();

		Etat e1 = new Etat();
		Etat e2 = new Etat();

		Transition t = new Transition(e1, e2, "a");
		e1.addTransition(t);

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		ArrayList<String> alphabet = new ArrayList<String>();
		finaux.add(e2);
		alphabet.add("a");

		Automate a1 = new Automate(e1, finaux, alphabet);
		a1 = Factory.concatenation(a1, a);

		if (Automate.accept("baacda", a1))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate20() {
		Etat e1 = Factory.creerEtatDebutLigne();
		Etat e2 = new Etat();

		Transition t = new Transition(e1, e2, "a");
		e1.addTransition(t);

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		ArrayList<String> alphabet = new ArrayList<String>();
		finaux.add(e2);
		alphabet.add("a");

		Automate a1 = new Automate(e1, finaux, alphabet);

		if (Automate.accept("aacdac", a1))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate21() {
		Automate a1 = Factory.creerAutomateDebutLigne();
		Automate a2 = Factory.creerAutomateFinLigne();

		a1 = Factory.concatenation(a1, a2);

		if (Automate.accept("", a1))
			System.out.println("youpie");
		else
			System.out.println("erreur");
	}

	public static void TestAutomate22() {
		Etat a1 = new Etat();
		Etat a2 = new Etat();
		Etat a3 = new Etat();

		a1.addTransition(new Transition(a1, a2, "eps"));
		a2.addTransition(new Transition(a2, a3, "a"));
		a3.addTransition(new Transition(a3, a1, "eps"));

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		finaux.add(a3);
		ArrayList<String> alphabet = new ArrayList<String>();
		alphabet.add("a");

		Automate auto = new Automate(a1, finaux, alphabet);

		Utils.automateToGraphViz(auto, "testAutomate22Eps.graph");
		Utils.automateToGraphViz(AdvancedOperations
				.removeEpsilonTransition(AdvancedOperations
						.removeEpsilonTransition(auto)), "testAutomate22.graph");
	}

	public static void TestAutomate23() {
		Etat a1 = new Etat();
		Etat a2 = new Etat();

		a1.addTransition(new Transition(a1, a2, "eps"));
		a2.addTransition(new Transition(a2, a1, "a"));

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		finaux.add(a2);
		ArrayList<String> alphabet = new ArrayList<String>();
		alphabet.add("a");

		Automate auto = new Automate(a1, finaux, alphabet);

		Utils.automateToGraphViz(auto, "testAutomate23Eps.graph");
		Utils.automateToGraphViz(
				AdvancedOperations.removeEpsilonTransition(auto),
				"testAutomate23.graph");
	}

	public static void TestAutomate24() {
		Etat a1 = new Etat();
		Etat a2 = new Etat();
		Etat a3 = new Etat();

		a1.addTransition(new Transition(a1, a2, "r"));
		a1.addTransition(new Transition(a1, a3, "s"));

		a2.addTransition(new Transition(a2, a1, "q"));
		a2.addTransition(new Transition(a2, a3, "eps"));

		a3.addTransition(new Transition(a3, a1, "q"));
		a3.addTransition(new Transition(a3, a2, "eps"));

		ArrayList<Etat> finaux = new ArrayList<Etat>();
		finaux.add(a3);
		ArrayList<String> alphabet = new ArrayList<String>();
		alphabet.add("q");
		alphabet.add("r");
		alphabet.add("s");

		Automate auto = new Automate(a1, finaux, alphabet);

		Utils.automateToGraphViz(auto, "testAutomate24Eps.graph");
		Utils.automateToGraphViz(
				AdvancedOperations.removeEpsilonTransition(auto),
				"testAutomate24.graph");
	}

	private static void testRemoveEpsilon(Automate a) {
		int i = Automate.getNbEpsilonTransitions(a);
		int j = Automate.getNbTransitions(a);

		System.out.println("Nb epsilon transitions avant : " + i);
		System.out.println("Nb transitions avant : " + j);

		Automate a1 = AdvancedOperations.removeEpsilonTransition(a);

		i = Automate.getNbEpsilonTransitions(a1);
		j = Automate.getNbTransitions(a1);

		System.out.println("Nb epsilon transitions apres (normalement 0) : "
				+ i);
		System.out.println("Nb transitions apres : " + j);
	}

}