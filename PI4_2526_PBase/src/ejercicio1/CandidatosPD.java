package ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import us.lsi.common.List2;
import us.lsi.common.Map2;

/**
 * Programacion dinamica manual para el problema de la contratacion.
 * Se memorizan los subproblemas y, por ser un problema de maximizacion,
 * se elige en cada estado la alternativa que da mayor valoracion total.
 */
public class CandidatosPD {

	/** Solucion parcial de un subproblema: alternativa elegida y valoracion. */
	public record Sp(Integer a, Double weight) implements Comparable<Sp> {
		public static Sp of(Integer a, Double weight) {
			return new Sp(a, weight);
		}

		@Override
		public int compareTo(Sp other) {
			return this.weight.compareTo(other.weight);
		}
	}

	private static Map<CandidatosProblem, Sp> memory;

	public static Solucion1 search() {
		memory = Map2.empty();
		pdSearch(CandidatosProblem.initial());
		return getSolucion();
	}

	private static Sp pdSearch(CandidatosProblem prob) {
		Sp res;
		if (memory.containsKey(prob)) {
			res = memory.get(prob);
		} else if (prob.isBaseCase()) {
			Double w = prob.baseCaseWeight();
			res = w != null ? Sp.of(null, w) : null;
			memory.put(prob, res);
		} else {
			List<Sp> sps = new ArrayList<>();
			for (Integer a : prob.actions()) {
				Sp spVecino = pdSearch(prob.neighbor(a));
				if (spVecino != null) {
					Double valor = spVecino.weight() + a * (double) Datos1.getValoracion(prob.index());
					sps.add(Sp.of(a, valor));
				}
			}
			res = sps.stream().max(Comparator.naturalOrder()).orElse(null);
			memory.put(prob, res);
		}
		return res;
	}

	public static Solucion1 getSolucion() {
		CandidatosProblem prob = CandidatosProblem.initial();
		if (memory.get(prob) == null)
			return null;
		List<Integer> acciones = List2.empty();
		while (!prob.isBaseCase()) {
			Sp sp = memory.get(prob);
			acciones.add(sp.a());
			prob = prob.neighbor(sp.a());
		}
		return Solucion1.create(acciones);
	}
}
