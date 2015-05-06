package entity;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
@Api(name="ecishopApi",version="v1")
public class EcishopApi {
	
	public static List<User> users = new ArrayList<User>();

	public static List<Product> products = new ArrayList<Product>();
	
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
			return Validator.Validar(email) && tid.equals("cc") ? id.toString().length()>7 : 
				id.toString().length()>4;
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
			if(!passwordValidator(pass)) throw new Exception(" password must be eight "
					+ "characters including"
					+ " one special character and alphanumeric characters.");
			user.setPassword(pass);
			return user;
		}
		
		private boolean passwordValidator(String pass) {
			String [] string = pass.split("");
			String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
			String[] charac = {"#", "$", "%", "&", "/", "(", ")", "=","?", "¿", "@", ".", "!", "¡", "+", "*", "-", "{", "}"};
			String[] lett = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o","p", "q", 
					"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
					"N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
			boolean ch = false;
			boolean num = false;
			boolean let = false;
			for (int i = 0; i< string.length && !(ch&&num&&let); i++){
				for (int j = 0; j < charac.length; j++) ch = charac[j].equals(string[i]) || ch;
				for (int j = 0; j < numbers.length; j++) num = numbers[j].equals(string[i]) || num;
				for (int j = 0; j < lett.length; j++) let = lett[j].equals(string[i]) || let;
			}
			return string.length > 7 && ch && num&&let; 
		}
		
		@ApiMethod(name="list")
		public List<User> getUsers(){
			return users;
		}
		
		@ApiMethod(name="userById")
		public User getUserbyId(@Named("id") Integer id) throws Exception{
			int index = users.indexOf(new User(id));
			if (index == -1)
				throw new Exception("Record does not exist");
			return users.get(index);
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
	
}
