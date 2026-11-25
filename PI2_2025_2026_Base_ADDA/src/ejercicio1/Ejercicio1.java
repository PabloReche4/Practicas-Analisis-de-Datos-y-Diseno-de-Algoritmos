package ejercicio1;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BLeaf;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;
import us.lsi.tiposrecursivos.Tree;

public class Ejercicio1 {	
	//1. Dado un árbol binario de enteros distintos de 0, 
	//determine el camino del árbol desde la 
	//raíz a una hoja no vacía tal que el producto de sus etiquetas sea máximo.   
	//Proporcione una solución también para árboles n-arios. 
	
	public static List<Integer> caminoMaximo (BinaryTree<Integer> tree) {
		return switch (tree) {
		case BEmpty()->null;
		case BLeaf(var lb)->{
			List<Integer>camino= new ArrayList<>();  //si es una hoja creo la lista 
			camino.add(lb);							//y le añado la hoja
			yield camino;
		}
		case BTree(var lb, var lt, var rt) -> {
			List<Integer> caminoIzq = caminoMaximo(lt); //creo la lista del hijo izquierdo
			List<Integer> caminoDer = caminoMaximo(rt); //creo la lista de hijo derecho
			
			Long productoIzq = calcularProducto(caminoIzq); //calculo el producto del izquierdo
			Long productoDer = calcularProducto(caminoDer); //calculo el producto del derecho
			
			List<Integer>caminoMaximo;
			if (productoIzq>=productoDer) {				//si el producto izquierdo es mayor que el derecho
				caminoMaximo = caminoIzq;				//el camino maximo es el izquierdo
			}else {
				caminoMaximo = caminoDer;				//sino es el derecho
			}
			if (caminoMaximo == null) {					// si la lista esta vacia la creo
				caminoMaximo = new ArrayList<>();
			}
			caminoMaximo.add(0,lb); //agregar el valor del nodo actual al inicial del camino
		
			yield caminoMaximo;
		}
		
		};
		
	}

	public static Long calcularProducto(List<Integer> camino) {
		if(camino==null||camino.isEmpty()) {
			return Long.MIN_VALUE; //sera el minimo si no hay camino, asi que cuando se compare siempre sera el menor
		}
		
		long producto = 1;
		for (Integer valor:camino) {   //si el valor esta en la lista de camino
			producto*=valor;			//calculamos el producto
		}
		return producto;
	}

	public static List<Integer> caminoMaximo (Tree<Integer> tree) {
		return switch(tree) {
		case TEmpty()->null;
		case TLeaf(var lb)->{
			List<Integer>camino= new ArrayList<>();    //si es una hoja creo la lista vacia y le añado la hoja
			camino.add(lb);
			yield camino;
		}
		case TNary(var lb, var hijos)->{
			List<Integer> caminoMax= null;       //creo la lista del camino maximo
			long productoMax= Long.MIN_VALUE;	//el producto maximo lo inicializo al valor mas bajo
			for (Tree<Integer>hijo: hijos) {	//si el hijo esta entre los hijos
				List<Integer> caminoHijo= caminoMaximo(hijo); //le añado el hijo a la lista del camino maximo
				long productoHijo= calcularProducto(caminoHijo); //calculo el producto del hijo
				
				if(productoHijo > productoMax) { //si el producto hijo es mayor que el producto maximo
					productoMax= productoHijo; //actualizo los valores
					caminoMax= caminoHijo;
				}
			}
			if (caminoMax==null) {  //si el caminomaximo esta a null pues creo la lista
				caminoMax = new ArrayList<>();
			}
			caminoMax.add(0,lb); //añadir el valor del nodo inicial al camino
			yield caminoMax;
		}
		};
	}
	

}
