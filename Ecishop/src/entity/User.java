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
	
	public User(Integer id){
		super();
		this.id = id;
	}
	
	public User(Integer id, String tid, String name, String last, Integer phone, String email, String pass ){
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
	public String getEmail() throws Exception {
		if(email != "") throw new Exception("You can't modify this field");
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
	private Integer getId() {
		return id;
	}
	private void setId(Integer id) {
		this.id = id;
	}

}
