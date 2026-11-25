package ejercicio3;

import java.util.List;

import us.lsi.common.List2;

/**
 * Estado mutable para el backtracking manual de la visita guiada.
 */
public class VisitaState {

	private VisitaProblem actual;
	private Double acumulado;
	private List<Integer> acciones;
	private List<VisitaProblem> anteriores;

	private VisitaState(VisitaProblem p, Double a,
			List<Integer> ls1, List<VisitaProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	public static VisitaState initial() {
		return new VisitaState(VisitaProblem.initial(), 0., List2.empty(), List2.empty());
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
		acumulado += Datos3.esfuerzo(actual.actual(), a);
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	public void back() {
		int last = acciones.size() - 1;
		VisitaProblem anterior = anteriores.get(last);
		acumulado -= Datos3.esfuerzo(anterior.actual(), acciones.get(last));
		acciones.remove(last);
		anteriores.remove(last);
		actual = anterior;
	}

	/** Cota inferior del esfuerzo total si se toma la alternativa a. */
	public Double cota(Integer a) {
		return acumulado + Datos3.esfuerzo(actual.actual(), a) + actual.neighbor(a).heuristic();
	}

	public Solucion3 getSolucion() {
		return Solucion3.create(acciones);
	}
}
