package ejercicio1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

/**
 * Arista del grafo de la contratacion.
 * El peso es la valoracion aportada al contratar al candidato (0 si no se contrata).
 */
public record CandidatosEdge(CandidatosVertex source, CandidatosVertex target, Integer action, Double weight)
		implements SimpleEdgeAction<CandidatosVertex, Integer> {

	public static CandidatosEdge of(CandidatosVertex v1, CandidatosVertex v2, Integer action) {
		Double w = action * (double) Datos1.getValoracion(v1.index());
		return new CandidatosEdge(v1, v2, action, w);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
