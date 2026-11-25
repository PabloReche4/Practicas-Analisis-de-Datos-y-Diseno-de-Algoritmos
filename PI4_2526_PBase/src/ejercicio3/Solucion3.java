package ejercicio3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

public class Solucion3 {

	// Para A* y BT: obtiene la lista de alternativas del camino y llama al otro factoria
	public static Solucion3 create(GraphPath<VisitaVertex, VisitaEdge> gp) {
		List<Integer> ls = gp.getEdgeList().stream().map(VisitaEdge::action).toList();
		return create(ls);
	}

	public static Solucion3 create(List<Integer> ls) {
		return new Solucion3(ls);
	}

	private String camino;
	private Double totalTime, totalEffort, totalMns;

	private Solucion3(List<Integer> ls) {  // Lista de acciones/alternativas

		/* Las propiedades de la clase son:
		 * camino = secuencia de vertices visitados
		 * totalTime = tiempo total empleado
		 * totalEffort = esfuerzo total empleado
		 * totalMns = monumentos visitados consecutivamente (antes o despues de otro) */

		// La ruta completa parte del origen (interseccion 0) y vuelve a el.
		List<Integer> ruta = new ArrayList<>();
		ruta.add(0);
		ruta.addAll(ls);

		totalTime = 0.;
		totalEffort = 0.;
		for (int k = 0; k < ruta.size() - 1; k++) {
			totalTime += Datos3.tiempo(ruta.get(k), ruta.get(k + 1));
			totalEffort += Datos3.esfuerzo(ruta.get(k), ruta.get(k + 1));
		}

		camino = ruta.stream()
				.map(i -> String.valueOf(Datos3.getVertex(i).id()))
				.collect(Collectors.joining(" -> "));

		// Intersecciones (distintas) con monumento y un vecino consecutivo con monumento.
		List<Integer> distintas = ruta.subList(0, ruta.size() - 1);
		int n = distintas.size();
		int cont = 0;
		for (int p = 0; p < n; p++) {
			Integer v = distintas.get(p);
			Integer prev = distintas.get((p - 1 + n) % n);
			Integer next = distintas.get((p + 1) % n);
			if (Datos3.tieneMonumento(v)
					&& (Datos3.tieneMonumento(prev) || Datos3.tieneMonumento(next)))
				cont++;
		}
		totalMns = (double) cont;
	}

	@Override
	public String toString() {
		String s1 = String.format("\nTiempos (total/maximo): %.1f / %.1f", totalTime, Datos3.maxTime);
		String s2 = String.format("\nEsfuerzo total: %.1f", totalEffort);
		String s3 = String.format("\nNº de monumentos visitados antes o despues de otro: %d", totalMns.intValue());
		return camino + s1 + s2 + s3;
	}
}
