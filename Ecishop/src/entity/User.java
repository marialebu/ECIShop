package entity;

import java.util.Random;
import java.util.UUID;

public class User {
	private Integer id; 
	private String tid;
	private String name;
	private String lastname;
	private Integer phone; 
	private String email;
	private String password;
	private String address;
	private String pmethod;
	private Integer cardnumber;
	
	public User(Integer id){
		super();
		this.id = id;
	}
	
	public User(Integer id, String tid, String name, String last, Integer phone, String email){
		super();
		this.id = id;
		this.name = name; 
		lastname = last;
		this.phone = phone; 
		this.email = email;
		setPassword();
	}
	
	
	private void setPassword() {
		String alfa = "#$%&?¿@";
		Random r = new Random();
		int i = r.nextInt(alfa.length()-1);
		while(!(i>0) || !(i<6))r.nextInt(alfa.length()-1);
		String uuid = UUID.randomUUID().toString().substring(0, 4)+alfa.substring(i-1, i)+
				UUID.randomUUID().toString().substring(0, 4);
		password = uuid;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail(){
		return email;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	@Override
	public String toString(){
		return name+" "+lastname+"\n"+
				email+" "+phone;
		 
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPaymentMethod(String paymentmethod) {
		this.pmethod = paymentmethod;
	}

	public void setCardNumber(Integer cardnumber) {
		this.cardnumber = cardnumber;
	} 

}
