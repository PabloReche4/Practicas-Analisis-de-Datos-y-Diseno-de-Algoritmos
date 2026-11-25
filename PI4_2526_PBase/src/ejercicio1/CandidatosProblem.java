package ejercicio1;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.Set2;

/**
 * Subproblema de la contratacion, usado por las versiones manuales (BT y PD).
 * Representa el estado del modelo:
 *  - index: candidato actual.
 *  - cualidades: indices de las cualidades que faltan por cubrir.
 *  - presupuesto: presupuesto restante.
 *  - cand: indices de candidatos que ya no pueden contratarse (incompatibles).
 */
public record CandidatosProblem(Integer index, Set<Integer> cualidades,
		Double presupuesto, Set<Integer> cand) {

	public static CandidatosProblem initial() {
		Set<Integer> todas = IntStream.range(0, Datos1.getNumCualidades())
				.boxed().collect(Collectors.toSet());
		return new CandidatosProblem(0, todas, (double) Datos1.getPresupuestoMax(), Set2.empty());
	}

	public static CandidatosProblem of(Integer index, Set<Integer> cualidades,
			Double presupuesto, Set<Integer> cand) {
		return new CandidatosProblem(index, cualidades, presupuesto, cand);
	}

	/** Se ha decidido sobre todos los candidatos. */
	public Boolean esTerminal() {
		return index.equals(Datos1.getNumCandidatos());
	}

	/** Es un estado solucion: terminal y con todas las cualidades cubiertas. */
	public Boolean esSolucion() {
		return esTerminal() && cualidades.isEmpty();
	}

	/** Caso base de la PD (coincide con el estado terminal). */
	public Boolean isBaseCase() {
		return esTerminal();
	}

	/** Peso del caso base: 0 si es solucion, null si no hay solucion. */
	public Double baseCaseWeight() {
		return cualidades.isEmpty() ? 0. : null;
	}

	/** Alternativas validas: 1 (contratar) / 0 (no contratar). */
	public List<Integer> actions() {
		List<Integer> acciones = List2.empty();
		if (index < Datos1.getNumCandidatos()) {
			boolean cabe = Datos1.getSueldoMin(index) <= presupuesto;
			if (!cand.contains(index) && cabe)
				acciones.add(1);
			acciones.add(0);
		}
		return acciones;
	}

	public CandidatosProblem neighbor(Integer a) {
		Set<Integer> nuevasCualidades = Set2.copy(cualidades);
		Double nuevoPresup = presupuesto;
		Set<Integer> nuevoCand = Set2.copy(cand);
		nuevoCand.remove(index);
		if (a == 1) {
			nuevasCualidades = Set2.difference(cualidades, Datos1.getCualidadesInt(index));
			nuevoPresup = presupuesto - Datos1.getSueldoMin(index);
			for (int j = index + 1; j < Datos1.getNumCandidatos(); j++)
				if (Datos1.getSonIncompatibles(index, j))
					nuevoCand.add(j);
		}
		return of(index + 1, nuevasCualidades, nuevoPresup, nuevoCand);
	}

	/**
	 * Estimacion optimista (cota superior): valoracion maxima que se podria
	 * obtener contratando a todos los candidatos en [index, n).
	 */
	public Double heuristic() {
		return IntStream.range(index, Datos1.getNumCandidatos())
				.mapToDouble(i -> Datos1.getValoracion(i))
				.sum();
	}
}
