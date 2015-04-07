package Automate;

import java.util.ArrayList;

public class AutomateEx {

    public static void main(String[] args) {
	/* Tous les tests fonctionnent */
	TestAutomate1();
	TestAutomate2();
	TestAutomate3();
	TestAutomate4();
	TestAutomate5();
	TestAutomate6();
	TestAutomate7();
	TestAutomate8();
	TestAutomate9();
	TestAutomate10();
	TestAutomate11();
	TestAutomate12();
	TestAutomate13();
	TestAutomate14();
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

	/* test sur des expressions donnees si l'automate accepte */

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	texte.add("a");
	texte.add("b");
	texte.add("b");
	texte.add("a");
	texte.add("b");
	texte.add("b");
	texte.add("b");

	if (Automate.accept(e0, "aabbabbb", a))
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

	if (Automate.accept(a3.getInitial(), "ab", a3))
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

	if (Automate.accept(a3.getInitial(), "b", a3))
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

	if (Automate.accept(a.getInitial(), "^acb$", a))
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

	if (Automate.accept(a.getInitial(), "abbbt", a))
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

	if (Automate.accept(a.getInitial(), "abt", a))
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

	if (Automate.accept(a.getInitial(), "abs", a))
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

	if (Automate.accept(a.getInitial(), "salut", a))
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

	if (Automate.accept(a.getInitial(), "artsts", a))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");
    }

    public static void TestAutomate10() {
	/* ap.k */

	Automate a1 = Factory.creerAutomate(new String("a"));
	Automate a2 = Factory.creerAutomate(new String("p"));
	Automate a3 = Factory.creerAutomate(new String("s"));
	Automate a4 = Factory.creerAutomate(new String("k"));

	/* a1 -> a2 -> a3 -> a4 -> a5 */
	Automate a = Factory.concatenation(Factory.concatenation(a1, a2),
		Factory.concatenation(a3, a4));

	ArrayList<String> texte = new ArrayList<String>();

	texte.add("a");
	texte.add("p");
	texte.add(".");
	texte.add("k");

	if (Automate.accept(a.getInitial(), "ap.k", a))
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
	texte.add("p");
	// texte.add("p");

	if (Automate.accept(a.getInitial(), "app", a))
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

	if (Automate.accept(a.getInitial(), "appp", a))
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

	if (Automate.accept(a.getInitial(), "appp", a))
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

	if (Automate.accept(a.getInitial(), "a\\.\\.\\.", a))
	    System.out.println("youpie");
	else
	    System.out.println("erreur");
    }

}