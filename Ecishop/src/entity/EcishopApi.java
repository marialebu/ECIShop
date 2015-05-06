package entity;

import java.util.*;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

@Api(name="eciShopApi",version="v1", description ="Services for EciShop Android Aplication")
public class EcishopApi {
	
	public static List<User> users = new ArrayList<User>();

	public static List<Product> products = new ArrayList<Product>();
	
	public static List<Sale> sales = new ArrayList<Sale>();
	
	@ApiMethod(name="addUser")
	public User addUser(@Named("id")Integer id, 
						@Named("tid") String tid, 
						@Named("name")String name, 
						@Named("lastname") String last,
						@Named("phone") Integer phone, 
						@Named("email")String email ) throws Exception{
			int index = users.indexOf(new User(id));
			if( index != -1) throw new Exception("The record already exists");
			if(!validator(id, tid, email))throw new Exception("Invalid fields");
			User user = new User(id, tid, name, last, phone, email);
			users.add(user);
			return user;
		}

		private boolean validator(Integer id, String tid, String email) {
			return Validator.validateEmail(email) && getUsersbyEmail(email).size()==0 && !existUserById(id) && tid.equals("cc") ? id.toString().length()>7 : 
				id.toString().length()>4;
		}
		
		
		@ApiMethod(name="setadditionalUserData")
		public User setadditionalUserData(@Named("id")Integer id, 
						@Named("adress")String address, 
						@Named("paymentmethod") String paymentmethod,
						@Named("cardnumber") Integer cardnumber) throws Exception{ 
			int index = users.indexOf(new User(id));
			if (index == -1) throw new Exception("Record does not exist");
			if(!Validator.validateCardNumber(cardnumber+"")) throw new Exception("Invalid card number");
			User user = users.get(index);
			user.setAddress(address);
			user.setPaymentMethod(paymentmethod);
			user.setCardNumber(cardnumber);
			return user;
		}
		
		
		@ApiMethod(name="removeUser")
		public void removeUser(@Named("id") Integer id) throws Exception{
			int index = users.indexOf(new User(id));
			if(index == -1) throw new Exception("The record doesn't exists");
			users.remove(index);
		}
		
		@ApiMethod(name="updateUser")
		public User updateUser(@Named("id")Integer id, 
						@Named("name")String name, 
						@Named("lastname") String last,
						@Named("phone") Integer phone, 
						@Named("password")String pass) throws Exception{ 
			int index = users.indexOf(new User(id));
			if (index == -1) throw new Exception("Record does not exist");
			User user = users.get(index);
			user.setName(name);
			user.setPhone(phone);
			user.setLastname(last);
			if(Validator.validatePassword(pass)) throw new Exception(" password must be eight "
					+ "characters including"
					+ " one special character and alphanumeric characters.");
			user.setPassword(pass);
			return user;
		}
		
		
		@ApiMethod(name="list")
		public List<User> getUsers(){
			return users;
		}
		
		private boolean existUserById(@Named("id") Integer id){
			boolean exist;
			try{
				getUserbyId(id);
				exist=true;
			}catch(Exception e){
				exist=false;
			}
			return exist;
		}
		
		@ApiMethod(name="userById", path="user_id")
		public User getUserbyId(@Named("id") Integer id) throws Exception{
			int index = users.indexOf(new User(id));
			if (index == -1)
				throw new Exception("Record does not exist");
			return users.get(index);
		}
		
		@ApiMethod(name="usersbyFullName", path="user_fname")
		public ArrayList<User> getUsersbyName(@Named("name")String name, @Named("lastName") String last) throws Exception{
			ArrayList<User> res = new ArrayList<User>();
			for(User u : users){
				if(u.getName().equals(name) && u.getLastname().equals(last)){
					res.add(u);
				}
			}
			
			return res;
		}
		
		@ApiMethod(name="usersbyLastName", path="user_lname")
		public ArrayList<User> getUsersbyLastName(@Named("lastName") String last) throws Exception{
			ArrayList<User> res = new ArrayList<User>();
			for(User u : users){
				if(u.getLastname().equals(last)){
					res.add(u);
				}
			}
			return res;
		}
		
		@ApiMethod(name="usersbyEmail", path="user_email")
		public ArrayList<User> getUsersbyEmail(@Named("email") String email){
			ArrayList<User> res = new ArrayList<User>();
			for(User u : users){
				if(u.getEmail().equals(email)){
					res.add(u);
				}
			}
			
			return res;
		}
		
		
		@ApiMethod(name="signUp")
			public User signUp(@Named("email")String email,
								  @Named("password") String pass) throws Exception{
			boolean r = false;
			User u = null; 
			for(int i = 0; i < users.size() && !r; i++){
				u = users.get(i);
				if(u.getEmail().equals(email)){
						r = true;
				}
			}
			if(r && !u.getPassword().equals(pass)) throw new Exception("Invalid user or password");
			return u;
		}
		

		//---------------------------------------------PRODUCTS-------------------------------------------------------------
			
			@ApiMethod(name="addProduct")
			public Product addProduct(@Named("id")Integer id, 
									@Named("type") String type, 
									@Named("name")String name, 
									@Named("desc") String desc,
									@Named("image")String image,
									@Named("units")Integer units,
									@Named("price")Integer price) throws Exception{
				int index = products.indexOf(new Product(id));
				if( index != -1) throw new Exception("The record already exists");
				if(!validator(id, type, name, desc, image, units, price))throw new Exception("Invalid fields");
				Product product = new Product(id, type, name, desc, image, units, price);
				products.add(product);
				return new Product(id);
			}
				
			private boolean validator(Integer id, String type, String name,
					String desc, String image, Integer units, Integer price) {
					return id!=null && type.length()>15 && name.length()>15 && desc.length()>15 && image.length()>15 && units!=null &&
							units>0 && price!=null && price>0;
			}
				@ApiMethod(name="updateProduct")
			public Product updateProduct(@Named("id")Integer id, 
							@Named("type") String type, 
							@Named("name")String name, 
							@Named("desc") String desc,
							@Named("image")String image,
							@Named("units")Integer units,
							@Named("price")Integer price) throws Exception{ 
				int index = products.indexOf(new Product(id));
				if (index == -1) throw new Exception("Record does not exist");
				if (units<=0 || price < 0) throw new Exception("Each number (units, price) must be a positive integer");
				Product product = products.get(index);
				product.setName(name);
				product.setType(type);
				product.setDesc(desc);
				product.setImage(image);
				product.setUnits(units);
				product.setPrice(price);
				return product;
			}
			
			@ApiMethod(name="removeProduct")
			public void removeProduct(@Named("id") Integer id) throws Exception{
				int index = products.indexOf(new Product(id));
				if(index == -1) throw new Exception("The record doesn't exists");
				products.remove(index);
			}
				
			@ApiMethod(name="sellProduct")
			public void sellProduct(@Named("id") Integer id) throws Exception{
				int index = products.indexOf(new Product(id));
				if(index == -1) throw new Exception("The record doesn't exists");
				boolean productExist = products.get(index).removeUnit();
				if(!productExist){
					removeProduct(id);
				}
			}
				
			@ApiMethod(name="listProducts")
			public List<Product> getProducts(){
				return products;
			}
			
			@ApiMethod(name="productById", path="product_id")
			public Product getProductById(@Named("id") Integer id) throws Exception{
				boolean existe = false;
				int index = -1;
				for(int i = 0 ; i < products.size() && !existe; i++){
					if(products.get(i).getId()==id){
						existe=true;
						index=i;
					}
				}
				if (!existe)
					throw new Exception("Record does not exist");
				return products.get(index);
			}
			
			@ApiMethod(name="productsByType", path="product_type")
			public List<Product> getProductByType(@Named("type") String type) throws Exception{
				List<Product> resp = new ArrayList<Product>();
				for(Product p : products){
					String tipo = p.getType();
					if(tipo.equals(type)){
						resp.add(p);
					}
				}
				if (resp.isEmpty())throw new Exception("Record does not exist");
				return resp;
			}
			
			@ApiMethod(name="productByName", path="product_name")
			public List<Product> getProductByName(@Named("name") String name) throws Exception{
				List<Product> resp = new ArrayList<Product>();
				for(Product p : products){
					String nombre = p.getName();
					if(name.equals(nombre)){
						resp.add(p);
					}
				}
				if (resp.isEmpty())throw new Exception("Record does not exist");
				return resp;
			}
			
			@ApiMethod(name="productsByUniquePrice", path="product_uprice")
			public List<Product> getProductsByUniquePrice(@Named("price") Integer price) throws Exception{
				List<Product> resp = new ArrayList<Product>();
				for(Product p : products){
					int precio = p.getPrice();
					if(precio==price){
						resp.add(p);
					}
				}
				if (resp.isEmpty())throw new Exception("Record does not exist");
				return resp;
			}
			
			@ApiMethod(name="productByRangePrice", path="product_rprice")
			public List<Product> getProductByRangePrice(@Named("min") Integer min, @Named("max") Integer max) throws Exception{
				if(min>max){
					int temp = min;
					min = max;
					max = temp;
				}
				List<Product> resp = new ArrayList<Product>();
				for(Product p : products){
					int precio = p.getPrice();
					if(precio>=min && precio<=max){
						resp.add(p);
					}
				}
				if (resp.isEmpty())throw new Exception("Record does not exist");
				return resp;
			}
			
			@ApiMethod(name="theMostSold")
			public Product getTheMostSold() throws Exception{
				int max = Integer.MIN_VALUE;
				Product pmax = null;
				for(Product p : products){
					int precio = p.unitsSold();
					if(precio>=max){
						pmax = p;
						max = precio;
					}
				}
				if (max==Integer.MIN_VALUE)throw new Exception("Record does not exist");
				return pmax;
			}
/*---------------------------------------------SALES-------------------------------------------------------------
			
			
			/*@ApiMethod(name="addSale")
			public Sale addUser(@Named("id")Integer id,
								@Named("user")User seller,
								@Named("products") ArrayList<Product> products
					) throws Exception{
					int index = users.indexOf(new Sale(id));
					Sale sale = new Sale(id, seller, products);
					sales.add(sale);
					return sale;	
			}
			
			@ApiMethod(name="listSales")
			public List<Sale> getSales(){
				return sales;zzz
			}
			
			@ApiMethod(name="salebyId")
			public Sale getSaleById(Integer id) throws Exception{
				int index = sales.indexOf(new Sale(id));
				if (index == -1)
					throw new Exception("Record does not exist");
				return sales.get(index);
			}
*/			
			
}
