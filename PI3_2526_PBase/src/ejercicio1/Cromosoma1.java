package ejercicio1;

import java.util.List;
import java.util.Set;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.common.Set2;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		Datos1.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		double goal= 0.0;
		double coste= 0.0;
		Set<String> cSet= Set2.empty();
		int e1= 0;
		for(int i=0;i<size();i++) {
			if(value.get(i)>0) {
				goal += Datos1.getValoracion(i);
				cSet.addAll(Datos1.getCualidades(i));
				coste += Datos1.getSueldoMin(i);
				for(int j: Datos1.getCandidato(i).incompatibilidades()) {
					e1+=value.get(j)>0?1:0;
				}
			}
		}
		int e2= 0;
		for(String c: Datos1.getCualidades()) {
			e2+=cSet.contains(c)?0:1;
		}
		double e3= Datos1.getPresupuestoMax() >= coste? 0 : coste - Datos1.getPresupuestoMax();
		double error = e1 + e2 +e3;
		return goal -10000*error;
	}
	
	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}