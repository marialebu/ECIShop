package ecishop;

public class Product {
	private String id; 
	private String type;
	private String name;
	private String desc;
	private String image;
	private int units;
	private int price;
	private int original_units;
	private User seller;
	
	public Product(String id){
		super();
		this.id = id;
	}
	
	public Product(String id, String type, String name, String desc, String image, Integer units, Integer price, User seller){
		super();
		this.id = id;
		this.type = type;
		this.desc = desc;
		this.image = image;
		this.name = name; 
		this.units = units;
		original_units = units;
		this.price = price;
		this.seller= seller;
	}
	
	@Override
	public String toString(){
		return name+" "+type+"\n"+
				desc+" "+id;
		 
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	} 
	
	
	public boolean removeUnit(){
		units--;
		return units>0; 
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	} 
	
	public int unitsSold(){
		return original_units - units;
	}

	public User getSeller() {
		return seller;
	}

}
