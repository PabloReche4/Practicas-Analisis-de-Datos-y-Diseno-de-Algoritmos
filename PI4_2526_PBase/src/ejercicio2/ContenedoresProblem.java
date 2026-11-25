package ejercicio2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

/**
 * Subproblema de los contenedores, usado por las versiones manuales (BT y PD).
 * Estado: indice del elemento actual y capacidad libre de cada contenedor.
 */
public record ContenedoresProblem(Integer index, List<Integer> capRestante) {

	public static ContenedoresProblem initial() {
		List<Integer> caps = IntStream.range(0, Datos2.getNumContenedores())
				.mapToObj(Datos2::getTamContenedor)
				.collect(Collectors.toList());
		return new ContenedoresProblem(0, caps);
	}

	public static ContenedoresProblem of(Integer index, List<Integer> capRestante) {
		return new ContenedoresProblem(index, capRestante);
	}

	public boolean completaContenedor(Integer a) {
		return a < Datos2.getNumContenedores()
				&& capRestante.get(a) - Datos2.getTamElemento(index) == 0;
	}

	/** Numero de contenedores completados al tomar la alternativa a (0 o 1). */
	public Integer pesoAccion(Integer a) {
		return completaContenedor(a) ? 1 : 0;
	}

	public Boolean esTerminal() {
		return index.equals(Datos2.getNumElementos());
	}

	/** Todo estado terminal es solucion (cualquier asignacion completa vale). */
	public Boolean esSolucion() {
		return esTerminal();
	}

	public Boolean isBaseCase() {
		return esTerminal();
	}

	public Double baseCaseWeight() {
		return 0.;
	}

	public List<Integer> actions() {
		List<Integer> acciones = List2.empty();
		if (index < Datos2.getNumElementos()) {
			for (int j = 0; j < Datos2.getNumContenedores(); j++)
				if (Datos2.getPuedeUbicarse(index, j)
						&& Datos2.getTamElemento(index) <= capRestante.get(j))
					acciones.add(j);
			acciones.add(Datos2.getNumContenedores());
		}
		return acciones;
	}

	public ContenedoresProblem neighbor(Integer a) {
		List<Integer> caps = List2.copy(capRestante);
		if (a < Datos2.getNumContenedores())
			caps.set(a, caps.get(a) - Datos2.getTamElemento(index));
		return of(index + 1, caps);
	}

	/**
	 * Estimacion optimista (cota superior): numero de contenedores que aun
	 * podrian terminar de completarse (todos los que no estan llenos del todo).
	 */
	public Double heuristic() {
		if (esTerminal())
			return 0.;
		return (double) capRestante.stream().filter(c -> c > 0).count();
	}
}
