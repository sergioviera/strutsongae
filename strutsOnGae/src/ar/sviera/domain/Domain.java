package ar.sviera.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Domain {
	private static Map<String,Float> products;

	static{
		initialize();
	}
	
	private static void initialize(){
		products = new HashMap<String,Float>();
		
		products.put("asado", 12.7f);	//updated 18/09/12
		products.put("cafe", 6.8f);		//updated 03/09/12
		products.put("agua", 1.6f);
		products.put("pan", 3.9f);		//updated 18/09/12
		products.put("te", 0.9f);
		products.put("azucar", 2.9f);	//updated 03/09/12
		products.put("leche", 2.5f);	//updated 03/09/12
		products.put("mate", 4f);		//updated 03/09/12
	}
	
	public static Set<String> getProducts(){
		return products.keySet();
	}
	
	public static Float getPrice(String product){
		if(products.containsKey(product)){
			return products.get(product);
		}else{
			return 0f;
		}
	}
}
