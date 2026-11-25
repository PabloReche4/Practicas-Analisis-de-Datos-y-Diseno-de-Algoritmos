package ejercicio1;

/**
 * Backtracking manual para el problema de la contratacion.
 * Se trata de un problema de maximizacion (valoracion total), por lo que se
 * poda una alternativa cuando su cota superior no supera la mejor encontrada.
 */
public class CandidatosBT {

	private static Double mejorValor;
	private static CandidatosState estado;
	private static Solucion1 solucion;

	public static Solucion1 search() {
		solucion = null;
		mejorValor = Double.NEGATIVE_INFINITY;   // maximizamos
		estado = CandidatosState.initial();
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

	public static Solucion1 getSolucion() {
		return solucion;
	}
}
