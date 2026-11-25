package ejercicio1;

import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Heuristica para A*.
 * Como el problema es de maximizacion, la estimacion debe ser optimista
 * (una cota superior de la valoracion que aun se podria conseguir).
 *
 * Cual es la valoracion maxima que se podria obtener contratando candidatos
 * en [i, n)? -> contratarlos todos: la suma de sus valoraciones.
 */
public class CandidatosHeuristic {

	public static Double heuristic(CandidatosVertex v1, Predicate<CandidatosVertex> goal, CandidatosVertex v2) {
		return IntStream.range(v1.index(), Datos1.getNumCandidatos())
				.mapToDouble(i -> Datos1.getValoracion(i))
				.sum();
	}
}
