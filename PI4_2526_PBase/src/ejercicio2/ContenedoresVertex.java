package ejercicio2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

/**
 * Modelo del problema de los contenedores como grafo virtual (para A* y BT).
 *
 * Propiedades individuales del vertice:
 *  - index: indice que recorre todas las variables del modelo (elemento actual).
 *  - capRestante: capacidad que queda libre en cada contenedor.
 *
 * Por cada elemento se decide en que contenedor se ubica; la alternativa m
 * (numero de contenedores) significa que el elemento no se ubica.
 */
public record ContenedoresVertex(Integer index, List<Integer> capRestante)
		implements VirtualVertex<ContenedoresVertex, ContenedoresEdge, Integer> {

	public static ContenedoresVertex initial() {
		List<Integer> caps = IntStream.range(0, Datos2.getNumContenedores())
				.mapToObj(Datos2::getTamContenedor)
				.collect(Collectors.toList());
		return new ContenedoresVertex(0, caps);
	}

	public static ContenedoresVertex of(Integer index, List<Integer> capRestante) {
		return new ContenedoresVertex(index, capRestante);
	}

	/** Indica si ubicar el elemento actual en el contenedor a lo deja lleno del todo. */
	public boolean completaContenedor(Integer a) {
		return a < Datos2.getNumContenedores()
				&& capRestante.get(a) - Datos2.getTamElemento(index) == 0;
	}

	// Metodos del grafo ---------------------------------------------------

	public Boolean goal() {
		return index.equals(Datos2.getNumElementos());
	}

	public Boolean goalHasSolution() {
		return true;            // cualquier asignacion completa es valida
	}

	@Override
	public Boolean isValid() {
		return index >= 0 && index <= Datos2.getNumElementos();
	}

	/**
	 * Alternativas validas: contenedores j en los que el elemento puede ubicarse
	 * (tipo permitido y con capacidad suficiente), mas la alternativa m (no ubicar).
	 */
	@Override
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

	@Override
	public ContenedoresVertex neighbor(Integer a) {
		List<Integer> caps = List2.copy(capRestante);
		if (a < Datos2.getNumContenedores())
			caps.set(a, caps.get(a) - Datos2.getTamElemento(index));
		return of(index + 1, caps);
	}

	@Override
	public ContenedoresEdge edge(Integer a) {
		return ContenedoresEdge.of(this, neighbor(a), a);
	}

	public String toGraph() {
		return String.format("(%d,%s)", index, capRestante);
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
}
