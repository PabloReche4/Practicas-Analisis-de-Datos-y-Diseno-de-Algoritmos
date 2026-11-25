package tests;
import ejercicio4.*;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio4 {

	    // Utilidad para buscar intersección por ID (no por nombre)
	    public static Interseccion interseccionPorId(
	            Graph<Interseccion, Calle> g, int id) {

	        return g.vertexSet().stream()
	                .filter(v -> Objects.equals(v.getId(), id))
	                .findFirst()
	                .orElse(null);
	    }
	

	// Utilidad para buscar calle por ID de intersecciones
	public static Calle callePorIds(Graph<Interseccion, Calle> g, int id1, int id2) {
	    Interseccion u = interseccionPorId(g, id1);
	    Interseccion v = interseccionPorId(g, id2);

	    if (u != null && v != null) {
	        Calle c = g.getEdge(u, v);
	        if (c == null) {
	            c = g.getEdge(v, u);
	        }
	        return c;
	    }

	    return null;
	}


	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		testEjercicio4("PI2E4_DatosEntrada"); 
	}

	public static void testEjercicio4(String file) {
	
		Graph<Interseccion, Calle> grafo = 
				GraphsReader.newGraph("ficheros/" + file + ".txt",
				Interseccion::ofFormat, 
				Calle::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Calle::getDuracion); 
		Graph<Interseccion, Calle> gEsfuerzo = 
				GraphsReader.newGraph("ficheros/" + file + ".txt",
				Interseccion::ofFormat, 
				Calle::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Calle::getEsfuerzo); 
		
		

		
		//Apartado A
		GraphPath<Interseccion, Calle> Ej4A1 = Ejercicio4.getSubgraph_EJ4A("m7", "m2", grafo, "ficheros/gvs/apartadoA1.gv");
		GraphPath<Interseccion, Calle> Ej4A2 = Ejercicio4.getSubgraph_EJ4A("m4", "m9", grafo, "ficheros/gvs/apartadoA2.gv");

		//Apartado B
		GraphPath<Interseccion, Calle> Ej4B = Ejercicio4.getRecorrido_E4B(gEsfuerzo);

		
		/*
		 * APARTADO C
		 * Conectividad con calles cortadas y máxima relevancia.
		 */
		//APARTADO C)

		System.out.println("Apartado C):");

		// Apartado C
		Set<Calle> cortesTest1 = Stream.of(
		        callePorIds(gEsfuerzo, 1, 6),
		        callePorIds(gEsfuerzo, 4, 7),
		        callePorIds(gEsfuerzo, 5, 8),
		        callePorIds(gEsfuerzo, 4, 6)
		)
		.filter(Objects::nonNull)
		.collect(Collectors.toSet());

		Graph<Interseccion, Calle> Ej4C1 = Ejercicio4.getRecorridoMaxRelevante_E4C(
		        cortesTest1,
		        gEsfuerzo,
		        "ficheros/gvs/apartadoC1.gv"
		);

		Set<Calle> cortesTest2 = new HashSet<>(cortesTest1);
		Calle calle9 = callePorIds(gEsfuerzo, 7, 8);
		if (calle9 != null) {
		    cortesTest2.add(calle9);
		}

		Graph<Interseccion, Calle> Ej4C2 = Ejercicio4.getRecorridoMaxRelevante_E4C(
		        cortesTest2,
		        gEsfuerzo,
		        "ficheros/gvs/apartadoC2.gv"
		);

	}
}
