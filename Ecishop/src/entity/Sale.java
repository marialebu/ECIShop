package entity;

import java.math.BigInteger;
import java.util.ArrayList;

public class Sale{
	
	private Integer id; 
	private User seller; 
	private ArrayList<Product> products;
	private long total; 
	private boolean active;
	
	public Sale(Integer id){
		super();
		this.id = id;
	}
	
	public Sale(Integer id, User user, ArrayList<Product> products){
		super();
		this.id = id;
		this.seller = user;
		this.products = products;
		price();
	}

	private void price() {
		for(Product p : getProducts()){
			total+=p.getPrice();
		}
	}

	public Integer getId() {
		return id;
	}

	public User getSeller() {
		return seller;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public long getTotal(){
		return total;
	}
	
	@Override
	public String toString(){
		return "number: "+id+"seller: "+seller+"Products: "+products.toString()+"Total: "+total;
		 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	} 
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	} 


	
	
	
	
	

}
