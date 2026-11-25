package ejercicio2;

import java.util.function.Predicate;

/**
 * Heuristica para A*.
 * Problema de maximizacion: la estimacion debe ser optimista (cota superior).
 *
 * Cuantos contenedores se podrian terminar de completar con los elementos en
 * [i, n)? De forma optimista, todos los que aun no estan llenos del todo.
 */
public class ContenedoresHeuristic {

	public static Double heuristic(ContenedoresVertex v1, Predicate<ContenedoresVertex> goal,
			ContenedoresVertex v2) {
		if (v1.index() >= Datos2.getNumElementos())
			return 0.;          // en un vertice final ya no quedan colocaciones
		return (double) v1.capRestante().stream()
				.filter(c -> c > 0)
				.count();
	}
}
