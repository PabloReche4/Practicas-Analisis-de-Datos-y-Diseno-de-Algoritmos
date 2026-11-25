package ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.traverse.BreadthFirstIterator;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.graphs.views.SubGraphView;


public class Ejercicio3 {



	/*
	 * EJERCICIO 3 APARTADO A
	 */
	public static Graph<Investigador,Colaboracion> getSubgraph_EJ3A(Graph <Investigador, Colaboracion> g ) {
		//Obtenga una vista del grafo que sólo incluya:  
		// • Los investigadores nacidos antes de 1982 o que tengan más de 5 artículos con alguno de sus coautores.   
		Predicate<Investigador> pv = v -> v.getFNacimiento() < 1982 || g.edgesOf(v).stream().anyMatch(e -> e.getNColaboraciones() > 5) ;
		// • Las colaboraciones más relevantes (número de artículos mayor que 5).
		Predicate<Colaboracion> pe = e -> e.getNColaboraciones() > 5 ;
		
		var r = SubGraphView.of(g, pv, pe); //creamos el subgrafo con los dos predicados que le hemos metido
		GraphColors.toDot(g, 
				"ficheros/gvs/Ejercicio3A.gv", 
				v -> v.toString()+" "+ v.getFNacimiento().toString(), //en cada punto del grafo metemos la fecha de nacimiento
				e -> e.getNColaboraciones().intValue()+"",			  //y el numero de colaboraciones	
				v -> GraphColors.colorIf(Color.blue, pv.test(v)), //le ponemos el color azul a los que cumplan con el predicado
				e -> GraphColors.colorIf(Color.blue, pe.test(e)));
		return r;
	}
	
	
	
	/*
	 * EJERCICIO 3 APARTADO B
	 */
	
//	Implemente un método que devuelva un conjunto con los 5 investigadores que tienen un mayor número de investigadores colaboradores. 
//	Muestre el grafo configurando su apariencia de forma que se resalten dichos investigadores.
	public static List<Investigador> getMayoresColaboradores_E3B (Graph<Investigador,Colaboracion> g) {
		// Crear comparator por numero de colaboradores (el grado de los vertices)
		// Ordenar los vertices segun cmp descendentemente
		// Coger los 5 primeros
		Comparator<Investigador> cmp = Comparator.comparing(v -> g.degreeOf(v)); 
		List<Investigador> res = g.vertexSet().stream()
			.sorted(cmp.reversed())
			.limit(5)
			.collect(Collectors.toList());
		
		GraphColors.toDot(g, 
				"ficheros/gvs/Ejercicio3B.gv", 
				v -> v.toString(), 
				e -> e.getNColaboraciones().intValue()+"",
				v -> GraphColors.colorIf(Color.blue, res.contains(v)), // resaltamos en color azul los investigadores
				e -> GraphColors.color(Color.black));
		return res;
	}

	
	
	/*
	 * EJERCICIO 3 APARTADO C
	 * Implemente un método que devuelva un map que almacene para cada investigador 
una lista de investigadores colaboradores ordenados de mayor a menor número de 
artículos conjuntos con él. Muestre el grafo configurando su apariencia de forma 
que se resalte para cada investigador su colaboración con mayor número de 
artículos.   
	 */
	public static Map<Investigador,List<Investigador>> getMapListaColaboradores_E3C (Graph<Investigador,Colaboracion> g) {
		// Map vacio
		// Comparator para las aristas en funcion del numero de colaboraciones (cmp)
		// Para cada vertice del grafo
		// 		obtener sus vecinos (consultar graphs y graphs2, para la busqueda de vecinos) ordenados descendentemente segun cmp (en uuna lista ls)
		// 		hacer put en el map: put(v,ls)
		Map<Investigador,List<Investigador>> res = new HashMap<>();
		Comparator<Colaboracion> cmp = Comparator.comparing(Colaboracion::getNColaboraciones);
		
		for (Investigador i: g.vertexSet()) {
			Set<Colaboracion> aristas = g.edgesOf(i);
			List<Colaboracion> listAr = new ArrayList<>(aristas);
			listAr.sort(cmp.reversed());
			List<Investigador> value = new ArrayList<>();
			
			for (Colaboracion c: listAr) {
				Investigador colaborador = Graphs.getOppositeVertex(g, c, i);
				value.add(colaborador);
			}
			res.put(i, value);
		}
		
		GraphColors.toDot(g, 
				"ficheros/gvs/Ejercicio3C.gv", 
				v -> v.toString(), 
				e -> e.getNColaboraciones().intValue()+"",
				v -> GraphColors.color(Color.black),// Importante lo del test ya que debe haber un booleano
				e -> GraphColors.colorIf(Color.blue, 
						res.get(g.getEdgeSource(e)).getFirst() == g.getEdgeTarget(e) // ¿El destino de esta arista es el principal colaborador del origen?
						|| res.get(g.getEdgeTarget(e)).getFirst() == g.getEdgeSource(e))); // ¿El origen de esta arista es el principal colaborador del destino?
		
		
		return res;
	}
	
	
	
	/*
	 * EJERCICIO 3 APARTADO D
	 * La distancia entre 2 investigadores es el número mínimo de investigadores 
intermedios que los relacionan, o 0 en caso de que estén relacionados directamente 
entre sí. Determine cuál es el par de investigadores que están más alejados, o uno 
de los pares en caso de que haya más de uno.   
	 * 
	 */
	
	
//	
//	private static int distancia(Investigador a,Investigador b, Graph<Investigador,Colaboracion> g) {
//		var alg = new BreadthFirstIterator<>(g,a); // La anchura entre dos vertices es el camino minimo entre ambos. 
//												   // El constructor del algoritmo iterativo para contar en profundida recibe:
//													// el grafo y el vertice de referencia, desde donde debe empezar a iterear.
//		while(!alg.next().equals(b)); 				// Se avanza/itera mientras el siguiente vertice no sea 'b', con el que queremos calcular el camino
//		return alg.getDepth(b);
//	}
//	
//	
//	public static Pair<Investigador,Investigador> getParMasLejano_E3D (Graph<Investigador,Colaboracion> g) {
//		Map<Pair<Investigador,Investigador>, Integer> mem = new HashMap<>(); // Creamos un mapa como memoria para guardar y comparar los resulatados para todas las combinaciones de pares de vertices
//		Set<Investigador> s = g.vertexSet(); // Nos quedamos con los vertices del grafo
//		List<Investigador> l = new ArrayList<>(s); // Convertimos el set de veritices a una lista para poder iterar
//		
//		// La distancia de A-B es la misma que de b-a, por lo que solo necesitamos calcular la mitad de las combinaciones
//		// La distancia de un vertice consigo mismo es 0, por lo que no hace falta calcularla
//		for (int i = 0; i < s.size()-1; i++ ) {
//			for (int j = i+1; j < s.size(); j++ ) {
//				// Guardar el par en variable clave
//				Pair<Investigador,Investigador> p = Pair.of(l.get(i), l.get(j)); // Creamos el par de investigadores (clave del mapa/memoria)
//				// Calcular la distancia entre ambos investigadores
//				int dist = distancia(l.get(i), l.get(j), g);
//				mem.put(p, dist); // Insertar en la memoria para posteriormente comparar y obtener el maximo
//			}
//		}
//		// Obtener el par con la distancia maxima
//		Pair<Investigador, Investigador> res = mem.entrySet() // Transformamos el mapa en un conjunto entry/contenedor con pares clave-valor
//				.stream()
//				.max(Comparator.comparing(Map.Entry::getValue)) // Comparamos cada par por su valor (distancia)
//				.get().getKey(); // Obtenemos el maximo y nos quedamos con la clave (par de investigadores)
//		return res;  
//	}
//	
	public static Pair<Investigador, Investigador> getParMasLejano_E3D(Graph<Investigador, Colaboracion> g) {
	    // Obtenemos la lista de vértices del grafo
	    List<Investigador> vertices = g.vertexSet().stream().toList();
	    
	    // Inicializamos el algoritmo de Floyd-Warshall para obtener todos los caminos mínimos
	    FloydWarshallShortestPaths<Investigador, Colaboracion> a = new FloydWarshallShortestPaths<>(g);
	    
	    Pair<Investigador, Investigador> p = null;
	    
	    // Doble bucle para comparar todos los pares de investigadores (i, j)
	    for (int i = 0; i < vertices.size(); i++) {
	        for (int j = i + 1; j < vertices.size(); j++) {
	            Investigador vi = vertices.get(i);
	            Investigador vj = vertices.get(j);
	            
	            // Obtenemos la distancia (peso del camino) entre los dos investigadores
	            Double d = a.getPathWeight(vi, vj);
	            
	            // Si encontramos una distancia mayor a la guardada, actualizamos el par
	            if (p == null || d > a.getPathWeight(p.first(), p.second())) {
	                p = Pair.of(vi, vj);
	            }
	        }
	    }
	    
	    // Una vez hallado el par más lejano, obtenemos el camino entre ellos para colorearlo
	    GraphPath<Investigador, Colaboracion> path = a.getPath(p.first(), p.second());
	    
	    // Generamos el archivo .gv resaltando el camino en magenta y el resto en verde
	    GraphColors.toDot(g, "ficheros/gvs/3apartadoD.gv",
	        v -> v.toString(),
	        e -> e.getNColaboraciones().toString(),
	        v -> GraphColors.colorIf(Color.magenta, Color.green, path.getVertexList().contains(v)),
	        e -> GraphColors.colorIf(Color.magenta, Color.green, path.getEdgeList().contains(e))
	    );
	    
	    return p;
	}
	
	/*
//	 * EJERCICIO 3 APARTADO E
//	 */
//	public static List<Set<Investigador>> getReuniones_E3E (Graph<Investigador,Colaboracion> g) {
//		List<Pair<Investigador,Investigador>> ls = mismaUniversidad(g);
//		Graph<Investigador, Colaboracion> g2 = Graphs.undirectedGraph(g);
//		
//		var res = (new GreedyColoring<>(g2)).getColoring().getColorClasses().stream().collect(Collectors.toList());
//		return res;
//	}

}