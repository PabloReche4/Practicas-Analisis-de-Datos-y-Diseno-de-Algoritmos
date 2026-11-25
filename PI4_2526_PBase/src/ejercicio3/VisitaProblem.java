package ejercicio3;

import java.util.List;

import us.lsi.common.List2;

/**
 * Subproblema de la visita guiada, usado por la version manual (BT).
 * Estado: posicion del recorrido, intersecciones visitadas y tiempo acumulado.
 */
public record VisitaProblem(Integer index, List<Integer> camino, Double durAcumulada) {

	public static VisitaProblem initial() {
		return new VisitaProblem(1, List2.of(0), 0.);
	}

	public static VisitaProblem of(Integer index, List<Integer> camino, Double durAcumulada) {
		return new VisitaProblem(index, camino, durAcumulada);
	}

	public Integer actual() {
		return camino.get(camino.size() - 1);
	}

	public Boolean esTerminal() {
		return index.equals(Datos3.N + 1);
	}

	/** Hay 2 intersecciones consecutivas con monumento en el recorrido. */
	public Boolean tieneMonumentosConsecutivos() {
		for (int k = 0; k < camino.size() - 1; k++)
			if (Datos3.sonMonumentos(camino.get(k), camino.get(k + 1)))
				return true;
		return false;
	}

	/** Estado solucion: ciclo completo con 2 monumentos consecutivos. */
	public Boolean esSolucion() {
		return esTerminal() && tieneMonumentosConsecutivos();
	}

	public List<Integer> actions() {
		List<Integer> acciones = List2.empty();
		Integer actual = actual();
		if (index < Datos3.N) {
			for (int k = 0; k < Datos3.N; k++)
				if (!camino.contains(k) && Datos3.existeCalle(actual, k)
						&& durAcumulada + Datos3.tiempo(actual, k) <= Datos3.maxTime)
					acciones.add(k);
		} else if (index.equals(Datos3.N)) {
			if (Datos3.existeCalle(actual, 0)
					&& durAcumulada + Datos3.tiempo(actual, 0) <= Datos3.maxTime)
				acciones.add(0);
		}
		return acciones;
	}

	public VisitaProblem neighbor(Integer a) {
		List<Integer> nc = List2.copy(camino);
		nc.add(a);
		return of(index + 1, nc, durAcumulada + Datos3.tiempo(actual(), a));
	}

	/**
	 * Estimacion optimista (cota inferior): cada calle que queda por recorrer
	 * tendria el menor esfuerzo posible de entre todas las del grafo.
	 */
	public Double heuristic() {
		int callesRestantes = (Datos3.N + 1) - index;
		return callesRestantes * Datos3.getMinEsfuerzo();
	}
}
