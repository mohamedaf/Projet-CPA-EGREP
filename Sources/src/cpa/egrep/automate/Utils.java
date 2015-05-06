package cpa.egrep.automate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
/**
 * @author AFFES Mohamed-Amin &&& KOBROSLI Hassan
 *
 */
public class Utils {

	/**
	 * Genere un fichier texte representant l'automate a pour representation par GraphViz.
	 * @param a Un automate.
	 * @param fileName Nom du fichier de sortie
	 */
	public static void automateToGraphViz(Automate a, String fileName) {
		HashMap<Etat, Integer> etats = new HashMap<Etat, Integer>();
		HashSet<Etat> etatsVisites = new HashSet<Etat>();
		Cpt cpt = new Cpt();

		String res = "digraph G {\n"
				+ automateToString(a.getInitial(), etats, etatsVisites, cpt)
				+ "}";

		try {
			FileWriter fw = new FileWriter("graphe/" + fileName);
			fw.write(res);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String automateToString(Etat initial,
			HashMap<Etat, Integer> map, HashSet<Etat> etatsVisites, Cpt cpt) {
		String res = "";

		if (!etatsVisites.contains(initial)) {

			etatsVisites.add(initial);

			if (!map.containsKey(initial)) {
				map.put(initial, cpt.getNextCpt());
			}

			int initValue = map.get(initial);

			for (Transition t : initial.getTransitionsList()) {
				Etat arrivee = t.getArrivee();
				if (!map.containsKey(arrivee)) {
					map.put(arrivee, cpt.getNextCpt());
				}
				res += "\t" + initValue + " -> " + map.get(arrivee)
						+ " [label = \"" + t.getCaractere() + "\"];\n";
				res += automateToString(arrivee, map, etatsVisites, cpt);

			}
		}
		return res;
	}

	static class Cpt {
		int cpt = 0;

		int getNextCpt() {
			return cpt++;
		}
	}

}
