package ejercicio2;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements RangeIntegerData<Solucion2> {
	public Cromosoma2(String file) {
		Datos2.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

	@Override
	public Integer size() {
		return Datos2.getNumProductos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		int goal = goal(value);
		Double totalTiempoProd = totalTiempoProd(value);
		Double totalTiempoElab = totalTiempoElab(value);
		return goal - 10000 * (totalTiempoProd + totalTiempoElab);
	}

	private Integer goal(List<Integer> value) {
		int valorTotal = 0;
		for (int i = 0; i < value.size(); i++) {
			valorTotal += Datos2.getPrecioProd(i) * value.get(i);
		}
		return valorTotal;
	}

	private Double totalTiempoProd(List<Integer> value) {
		Double tiempo = 0.;
		for (int i = 0; i < value.size(); i++) {
			tiempo += Datos2.getTiempoProdProd(i) * value.get(i);
		}
		return AuxiliaryAg.distanceToLeZero(tiempo - Datos2.getTiempoProdTotal());
	}

	private Double totalTiempoElab(List<Integer> value) {
		Double tiempo = 0.;
		for (int i = 0; i < value.size(); i++) {
			tiempo += Datos2.getTiempoElabProd(i) * value.get(i);
		}
		return AuxiliaryAg.distanceToLeZero(tiempo - Datos2.getTiempoElabTotal());
	}

	@Override
	public Solucion2 solution(List<Integer> value) {
		return Solucion2.create(value);
	}

	@Override
	public Integer max(Integer i) {
		return Datos2.getUnidsSemanaProd(i) + 1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
}