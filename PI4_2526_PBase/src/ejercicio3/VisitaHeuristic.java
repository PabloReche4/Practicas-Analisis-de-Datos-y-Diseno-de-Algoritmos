package ejercicio3;

import java.util.function.Predicate;

/**
 * Heuristica para A*.
 * Problema de minimizacion: la estimacion debe ser optimista (cota inferior).
 *
 * Cual seria el menor esfuerzo desde el vertice actual hasta el final?
 * De forma optimista: cada una de las calles que quedan por recorrer tendria
 * el menor esfuerzo posible de entre todas las calles del grafo.
 */
public class VisitaHeuristic {

	public static Double heuristic(VisitaVertex v1, Predicate<VisitaVertex> goal, VisitaVertex v2) {
		int callesRestantes = (Datos3.N + 1) - v1.index();
		return callesRestantes * Datos3.getMinEsfuerzo();
	}
}
