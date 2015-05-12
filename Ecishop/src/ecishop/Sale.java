package ecishop;


public class Sale{
	
	private String id; 
	private User seller; 
	private Product product;
	
	public Sale(String id){
		super();
		this.id = id;
	}
	
	public Sale(String id, User user, Product product){
		super();
		this.id = id;
		this.seller = user;
		this.product = product;
	}

	public String getId() {
		return id;
	}

	public User getSeller() {
		return seller;
	}

	public Product getProduct() {
		return product;
	}
	
	
	@Override
	public String toString(){
		return "number: "+id+"seller: "+seller+"Product: "+product.toString()+"Total: "+product.getPrice();
		 
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
