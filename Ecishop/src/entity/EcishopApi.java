package entity;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
@Api(name="employeeapi",version="v1")
public class EcishopApi {
	
	public static List<User> users = new ArrayList<User>();
	
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
			return new User(id);	
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
						@Named("tid") String tid, 
						@Named("name")String name, 
						@Named("lastname") String last,
						@Named("phone") Integer phone, 
						@Named("email")String email, 
						@Named("password")String pass) throws Exception{ 
			int index = users.indexOf(new User(id));
			if (index == -1) throw new Exception("Record does not exist");
			User user = users.get(index);
			user.setName(name);
			user.setPhone(phone);
			user.setLastname(last);
			user.setTid(tid);
			if(passwordValidator(pass)) throw new Exception(" password must be eight "
					+ "characters including"
					+ " one special character and alphanumeric characters.");
			user.setPassword(pass);
			return user;
		}
		
		private boolean passwordValidator(String pass) {
			String PATTERN = "^[0-9a-zA-Z-\\+]+[*.!@#$%^&(){}[]:;<>,.?/~_+-=|]$";
			Pattern pattern = Pattern.compile(PATTERN);
		    Matcher matcher = pattern.matcher(pass);
			return matcher.matches();
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
		
	
}
