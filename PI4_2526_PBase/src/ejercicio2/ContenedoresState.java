package ejercicio2;

import java.util.List;

import us.lsi.common.List2;

/**
 * Estado mutable para el backtracking manual de los contenedores.
 */
public class ContenedoresState {

	private ContenedoresProblem actual;
	private Double acumulado;
	private List<Integer> acciones;
	private List<ContenedoresProblem> anteriores;

	private ContenedoresState(ContenedoresProblem p, Double a,
			List<Integer> ls1, List<ContenedoresProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	public static ContenedoresState initial() {
		return new ContenedoresState(ContenedoresProblem.initial(), 0., List2.empty(), List2.empty());
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

	public void forward(Integer a) {
		acumulado += actual.pesoAccion(a);
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	public void back() {
		int last = acciones.size() - 1;
		ContenedoresProblem anterior = anteriores.get(last);
		acumulado -= anterior.pesoAccion(acciones.get(last));
		acciones.remove(last);
		anteriores.remove(last);
		actual = anterior;
	}

	/** Cota superior de contenedores que se podrian completar tomando a. */
	public Double cota(Integer a) {
		return acumulado + actual.pesoAccion(a) + actual.neighbor(a).heuristic();
	}

	public Solucion2 getSolucion() {
		return Solucion2.create(acciones);
	}
}
