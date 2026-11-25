package tests.ejercicio1;

import java.util.Locale;

import ejercicio1.CandidatosPD;
import ejercicio1.Datos1;
import ejercicio1.Solucion1;

public class TestsPDManual {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos1.iniDatos("datos_entrada/ejercicio1/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 1 - Algoritmo PD manual - DatosEntrada" + id + " ####");

			Solucion1 solucion = CandidatosPD.search();

			if (solucion != null)
				System.out.println(solucion);
			else
				System.out.println("No se ha encontrado solucion.");
			System.out.println();
		}
	}
}
