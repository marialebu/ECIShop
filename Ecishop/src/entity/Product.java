package entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Product implements Comparable<Product> {
	@Persistent( valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private String id;
	@Persistent
	private String type;
	@Persistent
	private String name;
	@Persistent
	private String desc;
	@Persistent
	private String image;
	@Persistent
	private int units;
	@Persistent
	private float price;
	@Persistent
	private int original_units;
	@Persistent
	private User seller;
	
	public Product(String id){
		super();
		this.id = id;
	}
	
	public Product(String id, String type, String name, String desc, String image, Integer units, Float price, User seller){
		super();
		this.id = id;
		this.type = type;
		this.desc = desc;
		this.image = image;
		this.name = name; 
		this.setUnits(units);
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
		setUnits(getUnits() - 1);
		return getUnits()>0; 
	}	

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(!type.isEmpty())this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(!name.isEmpty())this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if(!desc.isEmpty())this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		if(!image.isEmpty())this.image = image;
	}

	public void setUnits(Integer units) {
		if(units>0)this.units = units;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		if(price>0)this.price = price;
	}
	
	public int unitsSold(){
		return original_units - getUnits();
	}

	public User getSeller() {
		return seller;
	}

	public int compareTo(Product o) {
		int res = 0;
		if(unitsSold()>o.unitsSold()){
			res = 1;
		}else if(unitsSold()<o.unitsSold()){
			res = -1;
		}
		return res;
	}

	public int getUnits() {
		return units;
	}


}
