package ar.sviera.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Product {

	public Product(Long productId, String detail, Float productPrice,
			Float productIndecPrice) {
		super();
		this.productId = productId;
		this.detail = detail;
		this.productPrice = productPrice;
		this.productIndecPrice = productIndecPrice;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long productId;
	
	@Persistent
	private String detail;
	
	@Persistent
	private Float productPrice;
	
	@Persistent
	private Float productIndecPrice;
	
	
	

	
	
	

	
//	public JournalDTO setJournalDTO() {
		
//	}
	
//setters y getters



	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	

//fin setters y getters
	
	

}
