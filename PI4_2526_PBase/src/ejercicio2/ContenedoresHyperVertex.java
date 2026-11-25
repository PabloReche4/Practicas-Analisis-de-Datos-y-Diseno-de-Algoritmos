package ejercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.hypergraphs.VirtualHyperVertex;

/**
 * Modelo del problema de los contenedores como hipergrafo virtual (para PD).
 *
 * Propiedades individuales del hipervertice:
 *  - index: indice que recorre las variables del modelo (elemento actual).
 *  - capRestante: capacidad que queda libre en cada contenedor.
 *
 * La solucion (S) se representa como la lista de alternativas tomadas.
 */
public record ContenedoresHyperVertex(Integer index, List<Integer> capRestante)
		implements VirtualHyperVertex<ContenedoresHyperVertex, ContenedoresHyperEdge, Integer, List<Integer>> {

	public static ContenedoresHyperVertex initial() {
		List<Integer> caps = IntStream.range(0, Datos2.getNumContenedores())
				.mapToObj(Datos2::getTamContenedor)
				.collect(Collectors.toList());
		return new ContenedoresHyperVertex(0, caps);
	}

	public static ContenedoresHyperVertex of(Integer index, List<Integer> capRestante) {
		return new ContenedoresHyperVertex(index, capRestante);
	}

	/** Indica si ubicar el elemento actual en el contenedor a lo deja lleno del todo. */
	public boolean completaContenedor(Integer a) {
		return a < Datos2.getNumContenedores()
				&& capRestante.get(a) - Datos2.getTamElemento(index) == 0;
	}

	// Metodos del hipergrafo ---------------------------------------------

	@Override
	public Boolean isBaseCase() {
		return index.equals(Datos2.getNumElementos());
	}

	@Override
	public Double baseCaseWeight() {
		return 0.;              // cualquier asignacion completa es valida
	}

	@Override
	public List<Integer> baseCaseSolution() {
		return List2.empty();
	}

	@Override
	public Boolean isValid() {
		return index >= 0 && index <= Datos2.getNumElementos();
	}

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
	public List<ContenedoresHyperVertex> neighbors(Integer a) {
		List<Integer> caps = List2.copy(capRestante);
		if (a < Datos2.getNumContenedores())
			caps.set(a, caps.get(a) - Datos2.getTamElemento(index));
		return List.of(of(index + 1, caps));
	}

	@Override
	public ContenedoresHyperEdge edge(Integer a) {
		return ContenedoresHyperEdge.of(this, neighbors(a), a);
	}

	@Override
	public List<Integer> solution(Integer a, List<List<Integer>> solutions) {
		List<Integer> res = new ArrayList<>(solutions.get(0));
		res.add(0, a);
		return res;
	}

	/** Numero de contenedores llenados totalmente por una solucion. */
	public static Integer valor(List<Integer> solution) {
		int m = Datos2.getNumContenedores();
		int[] ocupado = new int[m];
		for (int i = 0; i < solution.size(); i++) {
			int j = solution.get(i);
			if (j < m)
				ocupado[j] += Datos2.getTamElemento(i);
		}
		int llenos = 0;
		for (int j = 0; j < m; j++)
			if (ocupado[j] == Datos2.getTamContenedor(j))
				llenos++;
		return llenos;
	}

	public String toGraph() {
		return String.format("(%d,%s)", index, capRestante);
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
}
