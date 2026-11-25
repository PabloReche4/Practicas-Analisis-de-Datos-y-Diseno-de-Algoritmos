package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.hypergraphs.VirtualHyperVertex;

/**
 * Modelo del problema de la contratacion como hipergrafo virtual (para PD).
 *
 * Propiedades individuales del hipervertice:
 *  - index: indice que recorre todas las variables del modelo (candidato actual).
 *  - cualidadesACubrir: indices de las cualidades que faltan por cubrir.
 *  - presupRestante: presupuesto que queda disponible.
 *  - cand: indices de candidatos que ya no pueden ser contratados (entre index y n).
 *      * al contratar al candidato i, los j incompatibles con el con j>i se anaden.
 *      * al pasar del nivel i al i+1 se elimina del conjunto al candidato i.
 *
 * La solucion (S) se representa como la lista de alternativas (0/1) tomadas.
 */
public record CandidatosHyperVertex(Integer index, Set<Integer> cualidadesACubrir,
		Double presupRestante, Set<Integer> cand)
		implements VirtualHyperVertex<CandidatosHyperVertex, CandidatosHyperEdge, Integer, List<Integer>> {

	public static CandidatosHyperVertex initial() {
		Set<Integer> todas = IntStream.range(0, Datos1.getNumCualidades())
				.boxed().collect(Collectors.toSet());
		return new CandidatosHyperVertex(0, todas, (double) Datos1.getPresupuestoMax(), Set2.empty());
	}

	public static CandidatosHyperVertex of(Integer index, Set<Integer> cualidades,
			Double presupuesto, Set<Integer> cand) {
		return new CandidatosHyperVertex(index, cualidades, presupuesto, cand);
	}

	// Metodos del hipergrafo ---------------------------------------------

	/** Caso base: se ha decidido sobre todos los candidatos. */
	@Override
	public Boolean isBaseCase() {
		return index.equals(Datos1.getNumCandidatos());
	}

	/** Hay solucion en el caso base solo si se han cubierto todas las cualidades. */
	@Override
	public Double baseCaseWeight() {
		return cualidadesACubrir.isEmpty() ? 0. : null;
	}

	@Override
	public List<Integer> baseCaseSolution() {
		return cualidadesACubrir.isEmpty() ? List2.empty() : null;
	}

	@Override
	public Boolean isValid() {
		return index >= 0 && index <= Datos1.getNumCandidatos() && presupRestante >= 0.;
	}

	/**
	 * Tipo de las alternativas: 1 (contratar) / 0 (no contratar).
	 * Solo se puede contratar al candidato 'index' si no esta en 'cand' (no es
	 * incompatible con uno ya contratado) y su sueldo cabe en el presupuesto.
	 */
	@Override
	public List<Integer> actions() {
		List<Integer> acciones = List2.empty();
		if (index < Datos1.getNumCandidatos()) {
			boolean cabe = Datos1.getSueldoMin(index) <= presupRestante;
			if (!cand.contains(index) && cabe)
				acciones.add(1);
			acciones.add(0);
		}
		return acciones;
	}

	@Override
	public List<CandidatosHyperVertex> neighbors(Integer a) {
		Set<Integer> nuevasCualidades = Set2.copy(cualidadesACubrir);
		Double nuevoPresup = presupRestante;
		Set<Integer> nuevoCand = Set2.copy(cand);
		nuevoCand.remove(index);
		if (a == 1) {
			nuevasCualidades = Set2.difference(cualidadesACubrir, Datos1.getCualidadesInt(index));
			nuevoPresup = presupRestante - Datos1.getSueldoMin(index);
			for (int j = index + 1; j < Datos1.getNumCandidatos(); j++)
				if (Datos1.getSonIncompatibles(index, j))
					nuevoCand.add(j);
		}
		return List.of(of(index + 1, nuevasCualidades, nuevoPresup, nuevoCand));
	}

	@Override
	public CandidatosHyperEdge edge(Integer a) {
		return CandidatosHyperEdge.of(this, neighbors(a), a);
	}

	@Override
	public List<Integer> solution(Integer a, List<List<Integer>> solutions) {
		List<Integer> res = new ArrayList<>(solutions.get(0));
		res.add(0, a);
		return res;
	}

	/** Valoracion total de una solucion (lista de alternativas 0/1). */
	public static Integer valor(List<Integer> solution) {
		Integer total = 0;
		for (int i = 0; i < solution.size(); i++)
			if (solution.get(i) == 1)
				total += Datos1.getValoracion(i);
		return total;
	}

	public String toGraph() {
		return String.format("(%d,%d,%.0f)", index, cualidadesACubrir.size(), presupRestante);
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
}
