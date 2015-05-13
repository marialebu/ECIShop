package entity;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Purchase {
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private String id;
	@Persistent
	private User buyer;
	@Persistent
	private ArrayList<Product> products;
	
	public Purchase(String id){
		super();
		this.id = id;
	}
	
	public Purchase(String id, User user, ArrayList<Product> products){
		super();
		this.id = id;
		this.buyer = user;
		this.products = products;
	}

	public String getId() {
		return id;
	}


	public ArrayList<Product> getProducts() {
		return products;
	}
	
	
	@Override
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append("Number: ");
		b.append(id);
		b.append(" Buyer: ");
		b.append(getBuyer());
		b.append("\n Products: ");
		double suma = 0;
		for(Product p : products){
			b.append("Name: ");
			b.append(p.toString());
			suma+=p.getPrice();
		}
		b.append(" Total: ");
		b.append(suma);
		return b.toString();
		 
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
		Purchase other = (Purchase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public User getBuyer() {
		return buyer;
	}
	

	public boolean contieneProducto(String id2) {
		boolean contiene = false;
		for(int i = 0 ; i < products.size() && !contiene ; i++){
			contiene = products.get(i).getId().equals(id2);
		}
		return contiene;
	}

	public boolean contieneVendedor(String id2) {
		boolean contiene = false;
		for(int i = 0 ; i < products.size() && !contiene ; i++){
			contiene = products.get(i).getSeller().getId().equals(id2);
		}
		return contiene;
	}

}
