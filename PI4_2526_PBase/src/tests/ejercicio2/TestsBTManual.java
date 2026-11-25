package tests.ejercicio2;

import java.util.Locale;

import ejercicio2.ContenedoresBT;
import ejercicio2.Datos2;
import ejercicio2.Solucion2;

public class TestsBTManual {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id = 1; id <= 3; id++) {
			Datos2.iniDatos("datos_entrada/ejercicio2/DatosEntrada" + id + ".txt", true);
			System.out.println("#### Ejercicio 2 - Algoritmo BT manual - DatosEntrada" + id + " ####");

			Solucion2 solucion = ContenedoresBT.search();

			if (solucion != null)
				System.out.println(solucion);
			else
				System.out.println("No se ha encontrado solucion.");
			System.out.println();
		}
	}
}
