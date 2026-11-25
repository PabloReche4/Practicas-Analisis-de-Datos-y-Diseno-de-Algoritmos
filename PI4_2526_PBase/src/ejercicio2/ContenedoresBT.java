package ejercicio2;

/**
 * Backtracking manual para el problema de los contenedores.
 * Maximiza el numero de contenedores llenados totalmente; se poda una
 * alternativa cuando su cota superior no supera la mejor solucion encontrada.
 */
public class ContenedoresBT {

	private static Double mejorValor;
	private static ContenedoresState estado;
	private static Solucion2 solucion;

	public static Solucion2 search() {
		solucion = null;
		mejorValor = Double.NEGATIVE_INFINITY;   // maximizamos
		estado = ContenedoresState.initial();
		btSearch();
		return solucion;
	}

	private static void btSearch() {
		if (estado.esSolucion()) {
			if (estado.acumulado() > mejorValor) {
				mejorValor = estado.acumulado();
				solucion = estado.getSolucion();
			}
		} else if (!estado.esTerminal()) {
			for (Integer a : estado.alternativas()) {
				if (estado.cota(a) > mejorValor) {     // poda
					estado.forward(a);
					btSearch();
					estado.back();
				}
			}
		}
	}

	public static Solucion2 getSolucion() {
		return solucion;
	}
}
