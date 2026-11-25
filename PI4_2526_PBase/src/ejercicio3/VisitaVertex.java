package ejercicio3;

import java.util.List;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

/**
 * Modelo de la visita guiada como grafo virtual (para A* y BT).
 *
 * Se busca un ciclo que pase por todas las intersecciones exactamente una vez
 * y vuelva al origen (interseccion 0), minimizando el esfuerzo total.
 *
 * Propiedades individuales del vertice:
 *  - index: posicion del recorrido que se esta decidiendo (1..N son las
 *    intersecciones; N es la vuelta al origen).
 *  - camino: secuencia de intersecciones visitadas hasta el momento.
 *  - durAcumulada: duracion (tiempo) acumulada del recorrido.
 */
public record VisitaVertex(Integer index, List<Integer> camino, Double durAcumulada)
		implements VirtualVertex<VisitaVertex, VisitaEdge, Integer> {

	public static VisitaVertex initial() {
		return new VisitaVertex(1, List2.of(0), 0.);
	}

	public static VisitaVertex of(Integer index, List<Integer> camino, Double durAcumulada) {
		return new VisitaVertex(index, camino, durAcumulada);
	}

	/** Ultima interseccion visitada. */
	public Integer actual() {
		return camino.get(camino.size() - 1);
	}

	// Metodos del grafo ---------------------------------------------------

	/** Es vertice final cuando se ha completado el ciclo (vuelta al origen incluida). */
	public Boolean goal() {
		return index.equals(Datos3.N + 1);
	}

	/** El ciclo es solucion si tiene 2 intersecciones consecutivas con monumento. */
	public Boolean goalHasSolution() {
		for (int k = 0; k < camino.size() - 1; k++)
			if (Datos3.sonMonumentos(camino.get(k), camino.get(k + 1)))
				return true;
		return false;
	}

	@Override
	public Boolean isValid() {
		return index >= 1 && index <= Datos3.N + 1 && durAcumulada <= Datos3.maxTime;
	}

	/**
	 * Alternativas validas:
	 *  - mientras index &lt; N: intersecciones no visitadas conectadas con la
	 *    actual por una calle, sin superar el tiempo maximo.
	 *  - cuando index == N: volver al origen (interseccion 0), si hay calle y
	 *    no se supera el tiempo maximo.
	 */
	@Override
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

	@Override
	public VisitaVertex neighbor(Integer a) {
		List<Integer> nc = List2.copy(camino);
		nc.add(a);
		return of(index + 1, nc, durAcumulada + Datos3.tiempo(actual(), a));
	}

	@Override
	public VisitaEdge edge(Integer a) {
		return VisitaEdge.of(this, neighbor(a), a);
	}

	public String toGraph() {
		return camino.toString();
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
}
