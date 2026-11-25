package tests.ejercicio3;

import java.util.Locale;

import ejercicio3.Datos3;
import ejercicio3.Solucion3;
import ejercicio3.VisitaBT;

public class TestsBTManual {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos3.iniDatos("datos_entrada/ejercicio3/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 3 - Algoritmo BT manual - DatosEntrada" + id + " ####");

			Solucion3 solucion = VisitaBT.search();

			if (solucion != null)
				System.out.println(solucion);
			else
				System.out.println("No se ha encontrado solucion.");
			System.out.println();
		}
	}
}
