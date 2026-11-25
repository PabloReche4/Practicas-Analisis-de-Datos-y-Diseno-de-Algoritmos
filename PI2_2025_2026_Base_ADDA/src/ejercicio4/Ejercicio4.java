package ejercicio4;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.AsSubgraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;


public class Ejercicio4 {

	
	/*
	 * EJERCICIO 4 APARTADO A
	 * Camino de menor duracion, siendo duracion un peso de las aristas
	 */
	
	private static Interseccion monumento(String monumento, Graph<Interseccion, Calle> g) {
		return g.vertexSet().stream().filter(i -> i.getNombre().equals(monumento)).findFirst().get();
	}
	
	public static GraphPath<Interseccion,Calle> getSubgraph_EJ4A(String mIn, String mOut,Graph <Interseccion, Calle> g, String ftest) {
		// Modificar el peso de las aristas para que sea la duracion
		g.edgeSet().stream()
				.forEach(e -> g.setEdgeWeight(e, e.getDuracion()));
		
		var alg = new DijkstraShortestPath<>(g); // creacion del algoritmo para resolver el camino mas corto, teniendo en cuenta el peso de cada arista
		
		Interseccion inicio = monumento(mIn,g); // Identificamos la arista inicio que se corresponde con el String que nos dan como parametro
		Interseccion fin = monumento(mOut,g); // Identificamos la arista fin que se corresponde con el String que nos dan como parametro
		
		GraphPath<Interseccion, Calle> res = alg.getPath(inicio, fin); // Obtenemos el camino mas corto del grafo aplicando el Dijkstra
		
		GraphColors.toDot(g, 
				ftest, 
				v -> v.toString()+" "+ v.getNombre(), 
				e -> e.getDuracion()+"",
				v -> GraphColors.colorIf(Color.blue, res.getVertexList().contains(v)), 
				e -> GraphColors.colorIf(Color.blue, res.getEdgeList().contains(e)));
		
		return res;
	}
	
	/*
	 * EJERCICIO 4 APARTADO B
	 * Determine la ruta de menor esfuerzo que pase por todas las intersecciones exactamente una vez y vuelva al origen.
	 * 
	 * PROBLEMA DEL VIAJANTE
	 */
	public static GraphPath<Interseccion,Calle> getRecorrido_E4B(Graph <Interseccion, Calle> g) {
       HeldKarpTSP<Interseccion, Calle> alg = new HeldKarpTSP<>();
      
       g.edgeSet().stream()
		.forEach(e -> g.setEdgeWeight(e, e.getEsfuerzo()));
       
       GraphPath<Interseccion,Calle> res = alg.getTour(g);
      
       GraphColors.toDot(g,"ficheros/gvs/ejercicio4B.gv",
				v->v.toString() + ", " + v.getNombre(), 
				e->(int)e.getDuracion()+"",
				v->GraphColors.colorIf(Color.blue, res.getVertexList().contains(v)),
				e->GraphColors.colorIf(Color.blue, res.getEdgeList().contains(e)));
		return res;
	}
	
	
	
	/*
	 * EJERCICIO 4 APARTADO C
	 * Dado un conjunto de calles cortadas al paso, 
	 * determinar si se pueden seguir visitando todos los monumentos a pie comenzando en uno cualquiera de ellos. 
	 * En caso contrario, determinar cuál es el conjunto de monumentos a visitar si se deben elegir los monumentos 
	 * conectados entre sí que maximice la suma total de las relevancias. .
	 * 
	 * PROBLE A DE CONECTIVIDAD - SI NO ESTA CONECTADO DETERMINAR QUÉ GRAFO TIENE LOS MONUMENTOS MAS RELEVANTES
	 */
	public static Graph<Interseccion, Calle> getRecorridoMaxRelevante_E4C(
	        Set<Calle> cs, Graph<Interseccion, Calle> g, String ftest) {

	    // 1) Subgrafo sin las calles cortadas
	    Predicate<Calle> callesNoCortadas = c -> !cs.contains(c);
	    Graph<Interseccion, Calle> gc = SubGraphView.ofEdges(g, callesNoCortadas);

	    // 2) Componentes conexos
	    ConnectivityInspector<Interseccion, Calle> inspector =
	            new ConnectivityInspector<>(gc);
	    List<Set<Interseccion>> componentes = inspector.connectedSets();

	    // 3) Seleccionar el componente con mayor relevancia
	    Set<Interseccion> componenteElegido = componentes.stream()
	            .max(Comparator.comparingInt(comp ->
	                    comp.stream().mapToInt(Interseccion::getRelevancia).sum()))
	            .orElseGet(Collections::emptySet);

	    // 4) Pintado: rojo solo para el componente elegido, negro para el resto
	    GraphColors.toDot(gc, ftest,
	            v -> v.toString(),
	            e -> "",
	            v -> GraphColors.colorIf(Color.red, Color.black,
	                    componenteElegido.contains(v)),
	            e -> GraphColors.colorIf(Color.red, Color.black,
	                    componenteElegido.contains(gc.getEdgeSource(e)) &&
	                    componenteElegido.contains(gc.getEdgeTarget(e)))
	    );

	    return gc;
	}

}