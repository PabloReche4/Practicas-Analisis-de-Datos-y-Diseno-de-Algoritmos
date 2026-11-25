package tests;
import ejercicio3.*;
import java.util.Locale;
import java.util.Map;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio3 {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		// Sustituye "investigadores" por el nombre real de tu fichero de texto (sin la extensión .txt)
		testEjercicio3("PI2E3_DatosEntrada"); 
	}

	public static void testEjercicio3(String file) {
		
		// 1. Creación del grafo
		// NOTA: Verifica que 'Investigador::ofFormat' y 'Colaboracion::ofFormat' 
		// coinciden con los métodos de creación en tus clases POJO.
		Graph<Investigador, Colaboracion> grafo = 
				GraphsReader.newGraph("ficheros/" + file + ".txt",
				Investigador::ofFormat, 
				Colaboracion::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Colaboracion::getNColaboraciones); // Usamos el peso definido en la lógica
		
		System.out.println("\nArchivo " + file + ".txt \n" + "Datos de entrada: " + grafo);

		// 2. Test Apartado A
		System.out.println("\nApartado A):");
		var subgrafo = Ejercicio3.getSubgraph_EJ3A(grafo);
		System.out.println("Subgrafo generado con " + subgrafo.vertexSet().size() + " vértices y " 
				+ subgrafo.edgeSet().size() + " aristas.");
		System.out.println("Archivo gv generado en ficheros/gvs/Ejercicio3A.gv");

		// 3. Test Apartado B
		System.out.println("\nApartado B):");
		System.out.println("Los 5 investigadores con mayor número de colaboradores son:");
		Ejercicio3.getMayoresColaboradores_E3B(grafo)
			.forEach(inv -> System.out.println("\t- " + inv));
		System.out.println("Archivo gv generado en ficheros/gvs/Ejercicio3B.gv");

		// 4. Test Apartado C
		System.out.println("\nApartado C):");
		System.out.println("Mapa de colaboradores ordenados por importancia:");
		Map<Investigador, java.util.List<Investigador>> mapa = Ejercicio3.getMapListaColaboradores_E3C(grafo);
		
		// Mostramos, por ejemplo, los 3 primeros del mapa para no saturar la consola
		mapa.entrySet().stream().limit(3).forEach(entry -> {
			System.out.println("\t" + entry.getKey() + " colabora principalmente con: " + entry.getValue());
		});
		System.out.println("... (mostrando solo los 3 primeros registros)");
		System.out.println("Archivo gv generado en ficheros/gvs/Ejercicio3C.gv");

		// 5. Test Apartado D
		System.out.println("\nApartado D):");
		var parLejano = Ejercicio3.getParMasLejano_E3D(grafo);
		System.out.println("El par de investigadores más lejano es: ");
		System.out.println("\t" + parLejano.first() + " y " + parLejano.second());
		
		// El Apartado E está comentado en tu clase lógica, por lo que no lo incluyo aquí.
	}
}
