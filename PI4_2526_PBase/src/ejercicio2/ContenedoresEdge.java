package ejercicio2;

import us.lsi.graphs.virtual.SimpleEdgeAction;

/**
 * Arista del grafo de los contenedores.
 * El peso es 1 si al ubicar el elemento se completa totalmente un contenedor,
 * y 0 en cualquier otro caso (el objetivo es maximizar contenedores llenos).
 */
public record ContenedoresEdge(ContenedoresVertex source, ContenedoresVertex target,
		Integer action, Double weight)
		implements SimpleEdgeAction<ContenedoresVertex, Integer> {

	public static ContenedoresEdge of(ContenedoresVertex v1, ContenedoresVertex v2, Integer action) {
		Double w = v1.completaContenedor(action) ? 1. : 0.;
		return new ContenedoresEdge(v1, v2, action, w);
	}

	@Override
	public String toString() {
		return String.format("%d; %.0f", action, weight);
	}
}
