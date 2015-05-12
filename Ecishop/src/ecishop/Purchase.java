package ecishop;

public class Purchase {
	
	private String id; 
	private User buyer; 
	private Product product;
	
	public Purchase(String id){
		super();
		this.id = id;
	}
	
	public Purchase(String id, User user, Product product){
		super();
		this.id = id;
		this.buyer = user;
		this.product = product;
	}

	public String getId() {
		return id;
	}


	public Product getProduct() {
		return product;
	}
	
	
	@Override
	public String toString(){
		return "Number: "+id+"Buyer: "+getBuyer()+"Product: "+product.toString()+"Total: "+product.getPrice();
		 
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


}
