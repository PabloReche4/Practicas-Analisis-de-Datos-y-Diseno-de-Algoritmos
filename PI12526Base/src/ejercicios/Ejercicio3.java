package ejercicios;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntTrio;

public class Ejercicio3 {

	public static Long iterativo(Integer a, Integer b, Integer c) {
		Long res=null;
		Map<IntTrio, Long> map= new HashMap<>();
		for(int i=0;i<=a;i++) {
			for(int j=0;j<=b;j++) {
				for(int k=0; k<=c; k++) {
					if(i<3||j<3||k<3) {
						res=(long) (i+Math.pow(j, 2)+k*2);
					}else if(i%j==0) {
						res = (long) (map.get(IntTrio.of(i-1, j/2, k/2)) + map.get(IntTrio.of(i-3, j/3, k/3)));
					}else {
						res = (long) (map.get(IntTrio.of(i/3, j-3, k-3))+map.get(IntTrio.of(i/2, j-2, k-2)));
					}
					map.put(IntTrio.of(i,j,k), res);
				}
			}
		}
		return map.get(IntTrio.of(a, b, c));
	}

	public static Long recursivo_sin_memoria(Integer a, Integer b, Integer c) {
		Long res= null;
		if(a<3||b<3||c<3) {
			res= (long) (a+b*b+c*2);
		}else if(a%b==0) {
			res= recursivo_sin_memoria(a-1, b/2, c/2)+recursivo_sin_memoria(a-3, b/3, c/3);
		}else {
			res= recursivo_sin_memoria(a/3, b-3, c-3)+recursivo_sin_memoria(a/2, b-2, c-2);
		}
		return res;
	}

	public static Long recursivo_con_memoria(Integer a, Integer b, Integer c) {
		Long res=null;
		return recursivaMem(a,b,c,res);
		
	}

	private static Long recursivaMem(Integer a, Integer b, Integer c, Long res) {
		if(a<3||b<3||c<3) {
			res= (long) (a+b*b+c*2);
		}else if(a%b==0) {
			res= recursivo_con_memoria(a-1, b/2, c/2)+recursivo_con_memoria(a-3, b/3, c/3);
		}else {
			res= recursivo_con_memoria(a/3, b-3, c-3)+recursivo_con_memoria(a/2, b-2, c-2);
		}
		return res;
	}

}
