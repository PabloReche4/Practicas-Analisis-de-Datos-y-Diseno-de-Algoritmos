package ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

/**
 * Arista del grafo de la visita guiada.
 * El peso es el esfuerzo de recorrer la calle entre la interseccion actual y
 * la siguiente (el objetivo es minimizar el esfuerzo total).
 */
public record VisitaEdge(VisitaVertex source, VisitaVertex target, Integer action, Double weight)
		implements SimpleEdgeAction<VisitaVertex, Integer> {

	public static VisitaEdge of(VisitaVertex v1, VisitaVertex v2, Integer action) {
		Double w = Datos3.esfuerzo(v1.actual(), action);
		return new VisitaEdge(v1, v2, action, w);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
