package ar.sviera.action;


import ar.sviera.data.ProductManager;
import ar.sviera.domain.Product;

import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private Long productId;
	
	private Float productPrice;
	
	private Float productIndecPrice; 	
	
	private String detail;
	
	public String execute() {
		ProductManager.save(new Product(productId,  detail,  productPrice,
				 productIndecPrice));
		return SUCCESS;
	}
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	public Float getProductIndecPrice() {
		return productIndecPrice;
	}
	public void setProductIndecPrice(Float productIndecPrice) {
		this.productIndecPrice = productIndecPrice;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}



}
