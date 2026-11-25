package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Ejercicio2 {
	
	public static List<Integer> f_RNF (Integer a, Integer b) {
		List<Integer> res= new ArrayList<>();
		if(a<2||b<2) {
			res.add(a*b);
		}else if(a>b) {
			res=f_RNF(a%b, b-1);
			res.add(a);
		}else {
			res=f_RNF(a-2, b/2);
			res.add(b);
		}
		return res;
	}
	
	public static List<Integer> f_it(Integer a, Integer b) {
	    List<Integer> res = new ArrayList<>();

	    while (!(a < 2 || b < 2)) {
	        if (a > b) {
	            res.add(0,a);
	            a = a % b;
	            b -=1;
	        } else {
	            res.add(0,b);
	            a -=2;
	            b = b / 2;
	        }
	    }

	    res.add(0,a * b);
	    return res;
	}

	
	public static List<Integer> f_RF (Integer a, Integer b) {
		List<Integer>ac = new ArrayList<>();
		return recFinal(ac,a, b);
	}
	

	private static List<Integer> recFinal(List<Integer> ac, Integer a, Integer b) {
		if(a<2||b<2) {
			ac.add(a*b);
		}else if(a>b) {
			ac=recFinal(ac, a%b, b-1);
			ac.add(a);
		}else {
			ac=recFinal(ac, a-2, b/2);
			ac.add(b);
		}
		return ac;
	}

	public static List<Integer> f_funcional (Integer a, Integer b) {
		Tupla t=Stream.iterate(Tupla.first(a, b), e->e.next()).filter(e->e.a()<2||e.b()<2).findFirst().get();
		List<Integer>res=new ArrayList<>(t.ls());
		res.add(0, t.a()*t.b());
		return res;	
	}
	private static record Tupla(List<Integer>ls,Integer a, Integer b) {
		public static Tupla of(List<Integer>ls, Integer a, Integer b) {
			return new Tupla (ls,a, b);
		}
		public static Tupla first(Integer a, Integer b) {
			return of(new ArrayList<>(),a,b);
		}
		public Tupla next() {
			if(!(a<2||b<2)) {
				if(a>b) {
					ls.addFirst(a);
					return Tupla.of(ls, a%b, b-1);
				}else {
					ls.addFirst(b);
					return Tupla.of(ls, a-2, b/2);
				}
			}else {
				return null;
			}
		}
		
	}

}
