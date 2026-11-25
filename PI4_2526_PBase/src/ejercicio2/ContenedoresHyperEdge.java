package ejercicio2;

import java.util.List;

import us.lsi.hypergraphs.SimpleHyperEdge;

/**
 * Hiperarista del problema de los contenedores.
 * El peso suma 1 cuando la ubicacion del elemento completa un contenedor.
 */
public record ContenedoresHyperEdge(ContenedoresHyperVertex source,
		List<ContenedoresHyperVertex> targets, Integer action)
		implements SimpleHyperEdge<ContenedoresHyperVertex, ContenedoresHyperEdge, Integer> {

	public static ContenedoresHyperEdge of(ContenedoresHyperVertex source,
			List<ContenedoresHyperVertex> targets, Integer action) {
		return new ContenedoresHyperEdge(source, targets, action);
	}

	@Override
	public Double weight(List<Double> targetsWeight) {
		return targetsWeight.get(0) + (source.completaContenedor(action) ? 1. : 0.);
	}
}
