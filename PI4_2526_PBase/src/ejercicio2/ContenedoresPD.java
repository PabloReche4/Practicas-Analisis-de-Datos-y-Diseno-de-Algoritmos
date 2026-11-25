package ejercicio2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import us.lsi.common.List2;
import us.lsi.common.Map2;

/**
 * Programacion dinamica manual para el problema de los contenedores.
 * Memoriza los subproblemas y elige en cada estado la alternativa que conduce
 * a mayor numero de contenedores completados.
 */
public class ContenedoresPD {

	public record Sp(Integer a, Double weight) implements Comparable<Sp> {
		public static Sp of(Integer a, Double weight) {
			return new Sp(a, weight);
		}

		@Override
		public int compareTo(Sp other) {
			return this.weight.compareTo(other.weight);
		}
	}

	private static Map<ContenedoresProblem, Sp> memory;

	public static Solucion2 search() {
		memory = Map2.empty();
		pdSearch(ContenedoresProblem.initial());
		return getSolucion();
	}

	private static Sp pdSearch(ContenedoresProblem prob) {
		Sp res;
		if (memory.containsKey(prob)) {
			res = memory.get(prob);
		} else if (prob.isBaseCase()) {
			res = Sp.of(null, prob.baseCaseWeight());
			memory.put(prob, res);
		} else {
			List<Sp> sps = new ArrayList<>();
			for (Integer a : prob.actions()) {
				Sp spVecino = pdSearch(prob.neighbor(a));
				if (spVecino != null) {
					Double valor = spVecino.weight() + prob.pesoAccion(a);
					sps.add(Sp.of(a, valor));
				}
			}
			res = sps.stream().max(Comparator.naturalOrder()).orElse(null);
			memory.put(prob, res);
		}
		return res;
	}

	public static Solucion2 getSolucion() {
		ContenedoresProblem prob = ContenedoresProblem.initial();
		if (memory.get(prob) == null)
			return null;
		List<Integer> acciones = List2.empty();
		while (!prob.isBaseCase()) {
			Sp sp = memory.get(prob);
			acciones.add(sp.a());
			prob = prob.neighbor(sp.a());
		}
		return Solucion2.create(acciones);
	}
}
