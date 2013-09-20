package ar.sviera.data;

import javax.jdo.PersistenceManager;

import ar.sviera.domain.Product;


public class ProductManager {
	
	public static  void save(Product prod) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(prod);
	}
}

