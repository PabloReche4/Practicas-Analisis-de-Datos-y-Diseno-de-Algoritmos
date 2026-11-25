package ejercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Ejercicio1 {

	// Del enunciado:
	public static Map<Integer,List<String>> solucionFuncional(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		UnaryOperator<EnteroCadena> nx = elem -> {
			return EnteroCadena.of(elem.a()+2, //nuevo a= viejo a+2
				elem.a()%3==0? //s cambia segun si a es multiplo de 3
					elem.s()+elem.a().toString(): // si a es multiplo de 3 concantena el numero a la cadena
					elem.s().substring(elem.a()%elem.s().length())); // Si no, recorta la cadena desde el índice a % longitud.
		};					
		return Stream.iterate(EnteroCadena.of(varA,varB), elem -> elem.a() < varC, nx) //crea un stream de EnteroCadena mientras a sea menor que varC
					.map(elem -> elem.s() + varD) //Convierte cada par (a,s) en un String, concatenando s con varD.
					.filter(nom -> nom.length() < varE) //Filtra: solo se quedan las cadenas cuya longitud sea menor que varE
					.collect(Collectors.groupingBy(String::length)); //Agrupa las cadenas por su longitud en un Map<Integer, List<String>>
	}
	
	public static Map<Integer,List<String>> solucionIterativa(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		EnteroCadena elem= EnteroCadena.of(varA, varB);
		Map<Integer, List<String>> ac= new HashMap<>();
		while(elem.a()<varC) {
			String s= elem.s()+varD;
			if (s.length()<varE) {
				if(ac.containsKey(s.length())) {
					ac.get(s.length()).add(s);
				}else {
					ac.put(s.length(), new ArrayList<>());
					ac.get(s.length()).add(s);
				}
			}
			elem = EnteroCadena.of(elem.a()+2, //nuevo a= viejo a+2
					elem.a()%3==0? //s cambia segun si a es multiplo de 3
					elem.s()+elem.a().toString(): // si a es multiplo de 3 concantena el numero a la cadena
					elem.s().substring(elem.a()%elem.s().length())); // Si no, recorta la cadena desde el índice a % longitud.		
		}
		return ac;
	}
	
	public static Map<Integer,List<String>> solucionRecursivaFinal(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		EnteroCadena elem= EnteroCadena.of(varA, varB);
		Map<Integer,List<String>>ac = new HashMap<>();
		return recursivaFinal(elem, varC, varD, varE, ac);
	}

	private static Map<Integer, List<String>> recursivaFinal(EnteroCadena elem, Integer varC, String varD, Integer varE,
			Map<Integer, List<String>> ac) {
		Map<Integer, List<String>> r= ac;
		if (elem.a()<varC) {
			String s= elem.s()+varD;
			if(s.length()<varE) {
				if(ac.containsKey(s.length())) {
					ac.get(s.length()).add(s);
				}else {
					ac.put(s.length(), new ArrayList<>());
					ac.get(s.length()).add(s);
				}
			}
			elem = EnteroCadena.of(elem.a()+2, //nuevo a= viejo a+2
					elem.a()%3==0? //s cambia segun si a es multiplo de 3
					elem.s()+elem.a().toString(): // si a es multiplo de 3 concantena el numero a la cadena
					elem.s().substring(elem.a()%elem.s().length())); // Si no, recorta la cadena desde el índice a % 
			r=recursivaFinal(elem, varC, varD, varE, ac);
		}
		return r;
	}
	
}


















