package ejercicio1;

import java.util.List;

import us.lsi.common.List2;

/**
 * Estado mutable para el backtracking manual de la contratacion.
 * Mantiene el subproblema actual, la valoracion acumulada y la informacion
 * necesaria para deshacer los pasos (acciones y subproblemas anteriores).
 */
public class CandidatosState {

	private CandidatosProblem actual;
	private Double acumulado;
	private List<Integer> acciones;
	private List<CandidatosProblem> anteriores;

	private CandidatosState(CandidatosProblem p, Double a,
			List<Integer> ls1, List<CandidatosProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	public static CandidatosState initial() {
		return new CandidatosState(CandidatosProblem.initial(), 0., List2.empty(), List2.empty());
	}

	public Double acumulado() {
		return acumulado;
	}

	public List<Integer> alternativas() {
		return actual.actions();
	}

	public Boolean esSolucion() {
		return actual.esSolucion();
	}

	public Boolean esTerminal() {
		return actual.esTerminal();
	}

	/** Avanza tomando la alternativa a. */
	public void forward(Integer a) {
		acumulado += a * (double) Datos1.getValoracion(actual.index());
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	/** Deshace el ultimo paso. */
	public void back() {
		int last = acciones.size() - 1;
		CandidatosProblem anterior = anteriores.get(last);
		acumulado -= acciones.get(last) * (double) Datos1.getValoracion(anterior.index());
		acciones.remove(last);
		anteriores.remove(last);
		actual = anterior;
	}

	/**
	 * Cota superior de lo que se podria conseguir tomando la alternativa a:
	 * valoracion acumulada + la que aporta a + estimacion optimista del resto.
	 */
	public Double cota(Integer a) {
		Double aporta = a * (double) Datos1.getValoracion(actual.index());
		return acumulado + aporta + actual.neighbor(a).heuristic();
	}

	public Solucion1 getSolucion() {
		return Solucion1.create(acciones);
	}
}
