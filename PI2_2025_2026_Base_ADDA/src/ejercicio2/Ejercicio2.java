package ejercicio2;

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


//2. Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto 
//si se cumple que, para todo nodo, el número total de vocales (incluyendo las repetidas) 
//contenidas en el subárbol izquierdo es igual al del subárbol derecho.  

//Proporcione una solución también para árboles n-arios, considerando que para todo nodo, 
//el número total de vocales (incluyendo las repetidas) contenidas en todos sus subárboles 
//debe coincidir.  

public class Ejercicio2 {
	
	public static Boolean solucion_recursiva(BinaryTree<String> tree) {
		
		return recursivaBinariaAux(tree, true, 0, new ArrayList<Integer>());   //devuelvo en la recursiva el arbol, el booleano que me piden, el nivel 
																			   //y la lista de vocales
	}

	private static Boolean recursivaBinariaAux(BinaryTree<String> tree, boolean b, int i,
			List<Integer> vocales) {
		return switch(tree) {
		case BEmpty()->false;   //si el arbol esta vacio es falso
		case BLeaf(var lb)->{
			Integer r= contarVocales(lb);         //contamos las vocales
			if(vocales.size()<=i)vocales.add(r);  //añadimos las vocales
			else if(vocales.get(i)!=r)b=false;    //si no coinciden es falso
			else b=true;						  //si coinciden es verdadero
			yield b;
		}
		case BTree(var lb, var lt, var rt)->{
			Integer r= contarVocales(lb);         //contamos las vocales
			if(vocales.size()<=i)vocales.add(r);  //las añadimos
			if(recursivaBinariaAux(lt, b, i+1, vocales)==false)yield false;   //comprobamos ambos hijos con la llamada recursiva
			else if(recursivaBinariaAux(rt, b, i+1, vocales)==false)yield false;
			else yield true;
		}
		};
	}


	public static Boolean solucion_recursiva(Tree<String> tree) {
		return recursivaNariaAux(tree, true, 0, new ArrayList<Integer>());
			
	}

	private static Boolean recursivaNariaAux(Tree<String> tree, boolean b, int i, 
			List<Integer> vocales) {
		
		return switch(tree) {
		case TEmpty()->false;
		case TLeaf(var lb)->{
			Integer r= contarVocales(lb);
			if(vocales.size()<=i)vocales.add(r);
			else if(vocales.get(i)!=r)b=false;     //igual que en la binaria
			else b=true;
			yield b;
		}
		case TNary(var lb, var ch)-> {
			Integer r= contarVocales(lb);
			if(vocales.size()<=i)vocales.add(r);  //añadimos las vocales
			for (Tree<String>c: ch) {
				if(recursivaNariaAux(c, b, i+1, vocales)==false) {    //comprobamos el hijo con la llamada recursiva
					b=false;
					break;
				}
			}
			yield b;
		}
		};
	}

	private static Integer contarVocales(String lb) {
		return (int) lb.toLowerCase().chars()
				.filter(ch->"aeiou".indexOf(ch)!=-1).count();    //metodo para contar vocales
	}
}
