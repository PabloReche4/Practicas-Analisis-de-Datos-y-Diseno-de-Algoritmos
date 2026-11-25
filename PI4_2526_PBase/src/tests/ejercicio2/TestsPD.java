package tests.ejercicio2;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ejercicio2.ContenedoresHyperEdge;
import ejercicio2.ContenedoresHyperVertex;
import ejercicio2.Datos2;
import ejercicio2.Solucion2;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.graphs.alg.PD.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestsPD {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos2.iniDatos("datos_entrada/ejercicio2/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 2 - Algoritmo PD - DatosEntrada" + id + " ####");

			ContenedoresHyperVertex start = ContenedoresHyperVertex.initial();

			SimpleVirtualHyperGraph<ContenedoresHyperVertex, ContenedoresHyperEdge, Integer> graph =
					SimpleVirtualHyperGraph.simpleVirtualHyperGraph(start);

			PD<ContenedoresHyperVertex, ContenedoresHyperEdge, Integer, List<Integer>> pd =
					PD.dynamicProgrammingSearch(graph, PDType.Max);
			pd.search();

			Map<ContenedoresHyperVertex, Sp<Integer, ContenedoresHyperEdge>> soluciones = pd.getSolutionsTree();

			if (soluciones.get(start) == null) {
				System.out.println("No se ha encontrado solucion.");
			} else {
				GraphTree<ContenedoresHyperVertex, ContenedoresHyperEdge, Integer, List<Integer>> tree =
						GraphTree.graphTree(start, soluciones);
				List<Integer> alternativas = tree.solution();
				System.out.println(Solucion2.create(alternativas));
				System.out.println("Contenedores llenados totalmente: "
						+ ContenedoresHyperVertex.valor(alternativas));
			}
			System.out.println();
		}
	}
}
