package tests.ejercicio1;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ejercicio1.CandidatosHyperEdge;
import ejercicio1.CandidatosHyperVertex;
import ejercicio1.Datos1;
import ejercicio1.Solucion1;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.graphs.alg.PD.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestsPD {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 1 - Algoritmo PD - DatosEntrada" + id + " ####");

			CandidatosHyperVertex start = CandidatosHyperVertex.initial();

			SimpleVirtualHyperGraph<CandidatosHyperVertex, CandidatosHyperEdge, Integer> graph =
					SimpleVirtualHyperGraph.simpleVirtualHyperGraph(start);

			PD<CandidatosHyperVertex, CandidatosHyperEdge, Integer, List<Integer>> pd =
					PD.dynamicProgrammingSearch(graph, PDType.Max);
			pd.search();

			Map<CandidatosHyperVertex, Sp<Integer, CandidatosHyperEdge>> soluciones = pd.getSolutionsTree();

			if (soluciones.get(start) == null) {
				System.out.println("No se ha encontrado solucion.");
			} else {
				GraphTree<CandidatosHyperVertex, CandidatosHyperEdge, Integer, List<Integer>> tree =
						GraphTree.graphTree(start, soluciones);
				List<Integer> alternativas = tree.solution();
				System.out.println(Solucion1.create(alternativas));
				System.out.println("Valoracion total: " + CandidatosHyperVertex.valor(alternativas));
			}
			System.out.println();
		}
	}
}
