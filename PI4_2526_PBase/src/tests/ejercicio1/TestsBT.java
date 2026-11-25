package tests.ejercicio1;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import ejercicio1.CandidatosEdge;
import ejercicio1.CandidatosHeuristic;
import ejercicio1.CandidatosVertex;
import ejercicio1.Datos1;
import ejercicio1.Solucion1;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestsBT {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 1 - Algoritmo BT - DatosEntrada" + id + " ####");

			CandidatosVertex start = CandidatosVertex.initial();

			EGraph<CandidatosVertex, CandidatosEdge> graph = EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(e -> e.weight())
					.heuristic(CandidatosHeuristic::heuristic)
					.build();

			BT<CandidatosVertex, CandidatosEdge, Solucion1> bt = BT.of(graph);
			Optional<GraphPath<CandidatosVertex, CandidatosEdge>> camino = bt.search();

			if (camino.isPresent())
				System.out.println(Solucion1.create(camino.get()));
			else
				System.out.println("No se ha encontrado solucion.");
			System.out.println();
		}
	}
}
