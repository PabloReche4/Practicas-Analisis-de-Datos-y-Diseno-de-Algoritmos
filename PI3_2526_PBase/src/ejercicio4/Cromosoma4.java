package ejercicio4;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma4 implements PermutationData<Solucion4> {
	public Cromosoma4(String file) {
		Datos4.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}

	@Override
	public Integer size() {
		return Datos4.N;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = goal(value);
		Double totalDuracion = totalDuracion(value);
		Double totalConsecutivos = totalConsecutivos(value);
		return goal - 10000 * (AuxiliaryAg.distanceToLeZero(totalDuracion - Datos4.maxTime)
				+ AuxiliaryAg.distanceToGeZero(totalConsecutivos - 1));
	}  

	public static Double goal(List<Integer> value) {
		Double esfuerzoTotal = 0.;
		for (int i = 0; i < value.size(); i++) {
			esfuerzoTotal += Datos4.esfuerzo(value.get(i), value.get((i + 1) % Datos4.N));//el % Datos4.N hace que al llegar al último vuelva al primero
		}
		return esfuerzoTotal;
	}

	public static Double totalDuracion(List<Integer> value) {
		Double duracionTotal = 0.;
		for (int i = 0; i < value.size(); i++) {
			duracionTotal += Datos4.tiempo(value.get(i), value.get((i + 1) % Datos4.N));//el % Datos4.N hace que al llegar al último vuelva al primero
		}
		return duracionTotal;
	}

	public static Double totalConsecutivos(List<Integer> value) {
		Double totalConsecutivos = 0.;
		for (int i = 0; i < value.size(); i++) {
			if (Datos4.sonMonumentos(value.get(i), value.get((i + 1) % Datos4.N))) { //el % Datos4.N hace que al llegar al último vuelva al primero.
				totalConsecutivos++;
			}
		}
		return totalConsecutivos;
	}

	@Override
	public Solucion4 solution(List<Integer> value) {
		return Solucion4.create(value);
	}
}
