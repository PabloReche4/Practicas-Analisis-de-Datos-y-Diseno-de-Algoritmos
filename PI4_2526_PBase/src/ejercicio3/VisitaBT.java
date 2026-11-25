package ejercicio3;

/**
 * Backtracking manual para la visita guiada.
 * Minimiza el esfuerzo total; se poda una alternativa cuando su cota inferior
 * no mejora la mejor solucion encontrada hasta el momento.
 */
public class VisitaBT {

	private static Double mejorValor;
	private static VisitaState estado;
	private static Solucion3 solucion;

	public static Solucion3 search() {
		solucion = null;
		mejorValor = Double.MAX_VALUE;     // minimizamos
		estado = VisitaState.initial();
		btSearch();
		return solucion;
	}

	private static void btSearch() {
		if (estado.esSolucion()) {
			if (estado.acumulado() < mejorValor) {
				mejorValor = estado.acumulado();
				solucion = estado.getSolucion();
			}
		} else if (!estado.esTerminal()) {
			for (Integer a : estado.alternativas()) {
				if (estado.cota(a) < mejorValor) {     // poda
					estado.forward(a);
					btSearch();
					estado.back();
				}
			}
		}
	}

	public static Solucion3 getSolucion() {
		return solucion;
	}
}
