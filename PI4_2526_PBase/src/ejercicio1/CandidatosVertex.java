package ejercicio1;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

/**
 * Modelo del problema de la contratacion como grafo virtual (para A* y BT).
 *
 * Propiedades individuales del vertice:
 *  - Basicas:
 *      index: indice que recorre todas las variables del modelo (candidato actual).
 *      as: lista de alternativas (0/1) tomadas hasta el momento.
 *  - Derivadas (calculadas a partir de 'as'):
 *      cualidadesACubrir(): indices de las cualidades que faltan por cubrir.
 *      presupRestante(): presupuesto que queda disponible.
 */
public record CandidatosVertex(Integer index, List<Integer> as)
		implements VirtualVertex<CandidatosVertex, CandidatosEdge, Integer> {

	public static CandidatosVertex initial() {
		return new CandidatosVertex(0, List2.empty());
	}

	public static CandidatosVertex of(Integer index, List<Integer> as) {
		return new CandidatosVertex(index, as);
	}

	// Propiedades derivadas -----------------------------------------------

	/** Indices de los candidatos contratados hasta el momento. */
	public List<Integer> seleccionados() {
		return IntStream.range(0, as.size())
				.filter(k -> as.get(k) == 1)
				.boxed().toList();
	}

	/** Indices de las cualidades deseadas que aun no estan cubiertas. */
	public Set<Integer> cualidadesACubrir() {
		Set<Integer> faltan = IntStream.range(0, Datos1.getNumCualidades())
				.boxed().collect(Collectors.toSet());
		for (Integer k : seleccionados())
			faltan.removeAll(Datos1.getCualidadesInt(k));
		return faltan;
	}

	/** Presupuesto que queda sin gastar. */
	public Double presupRestante() {
		Double gasto = seleccionados().stream()
				.mapToDouble(k -> Datos1.getSueldoMin(k)).sum();
		return Datos1.getPresupuestoMax() - gasto;
	}

	// Metodos del grafo ---------------------------------------------------

	/** Es vertice final cuando ya se ha decidido sobre todos los candidatos. */
	public Boolean goal() {
		return index.equals(Datos1.getNumCandidatos());
	}

	/** El vertice final es solucion solo si cubre todas las cualidades deseadas. */
	public Boolean goalHasSolution() {
		return cualidadesACubrir().isEmpty();
	}

	@Override
	public Boolean isValid() {
		return index >= 0 && index <= Datos1.getNumCandidatos() && presupRestante() >= 0.;
	}

	/**
	 * Tipo de las alternativas: 1 (contratar) / 0 (no contratar).
	 * Alternativas validas: contratar al candidato 'index' solo si es compatible
	 * con los ya contratados y su sueldo cabe en el presupuesto restante.
	 */
	@Override
	public List<Integer> actions() {
		List<Integer> acciones = List2.empty();
		if (index < Datos1.getNumCandidatos()) {
			boolean compatible = seleccionados().stream()
					.noneMatch(k -> Datos1.getSonIncompatibles(index, k));
			boolean cabe = Datos1.getSueldoMin(index) <= presupRestante();
			if (compatible && cabe)
				acciones.add(1);
			acciones.add(0);
		}
		return acciones;
	}

	@Override
	public CandidatosVertex neighbor(Integer a) {
		List<Integer> nas = List2.copy(as);
		nas.add(a);
		return of(index + 1, nas);
	}

	@Override
	public CandidatosEdge edge(Integer a) {
		return CandidatosEdge.of(this, neighbor(a), a);
	}

	public String toGraph() {
		return String.format("(%d,%s)", index, goal() && goalHasSolution() ? "Si" : "No");
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
}
