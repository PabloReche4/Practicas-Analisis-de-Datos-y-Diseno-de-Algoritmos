package tests.ejercicio2;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import ejercicio2.ContenedoresEdge;
import ejercicio2.ContenedoresHeuristic;
import ejercicio2.ContenedoresVertex;
import ejercicio2.Datos2;
import ejercicio2.Solucion2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestsAStar {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos2.iniDatos("datos_entrada/ejercicio2/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 2 - Algoritmo A* - DatosEntrada" + id + " ####");

			ContenedoresVertex start = ContenedoresVertex.initial();

			EGraph<ContenedoresVertex, ContenedoresEdge> graph = EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(e -> e.weight())
					.heuristic(ContenedoresHeuristic::heuristic)
					.build();

			AStar<ContenedoresVertex, ContenedoresEdge, ?> aStar = AStar.ofGreedy(graph);
			Optional<GraphPath<ContenedoresVertex, ContenedoresEdge>> camino = aStar.search();

			if (camino.isPresent())
				System.out.println(Solucion2.create(camino.get()));
			else
				System.out.println("No se ha encontrado solucion.");
			System.out.println();
		}
	}
}
