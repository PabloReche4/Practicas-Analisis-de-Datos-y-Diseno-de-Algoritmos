package tests.ejercicio3;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import ejercicio3.Datos3;
import ejercicio3.Solucion3;
import ejercicio3.VisitaEdge;
import ejercicio3.VisitaHeuristic;
import ejercicio3.VisitaVertex;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestsAStar {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos3.iniDatos("datos_entrada/ejercicio3/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 3 - Algoritmo A* - DatosEntrada" + id + " ####");

			VisitaVertex start = VisitaVertex.initial();

			EGraph<VisitaVertex, VisitaEdge> graph = EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.edgeWeight(e -> e.weight())
					.heuristic(VisitaHeuristic::heuristic)
					.build();

			AStar<VisitaVertex, VisitaEdge, ?> aStar = AStar.of(graph);
			Optional<GraphPath<VisitaVertex, VisitaEdge>> camino = aStar.search();

			if (camino.isPresent())
				System.out.println(Solucion3.create(camino.get()));
			else
				System.out.println("No se ha encontrado solucion.");
			System.out.println();
		}
	}
}
