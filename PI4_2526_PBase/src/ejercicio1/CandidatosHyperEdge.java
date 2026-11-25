package ejercicio1;

import java.util.List;

import us.lsi.hypergraphs.SimpleHyperEdge;

/**
 * Hiperarista del problema de la contratacion.
 * El peso acumula la valoracion del candidato contratado en este nivel.
 */
public record CandidatosHyperEdge(CandidatosHyperVertex source,
		List<CandidatosHyperVertex> targets, Integer action)
		implements SimpleHyperEdge<CandidatosHyperVertex, CandidatosHyperEdge, Integer> {

	public static CandidatosHyperEdge of(CandidatosHyperVertex source,
			List<CandidatosHyperVertex> targets, Integer action) {
		return new CandidatosHyperEdge(source, targets, action);
	}

	@Override
	public Double weight(List<Double> targetsWeight) {
		return targetsWeight.get(0) + action * (double) Datos1.getValoracion(source.index());
	}
}
