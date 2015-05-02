package entity;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.server.spi.config.Api;
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
			return user;	
		}

		private boolean validator(Integer id, String tid, String email) {
			String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.{com, net, co})$";
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		    Matcher matcher = pattern.matcher(email);
			return matcher.matches() && tid.equals("cc") ? id.toString().length()>7 : 
				id.toString().length()>4;
		}
		
		@ApiMethod(name="removeUser")
		public void removeUser(@Named("id") Integer id) throws Exception{
			int index = users.indexOf(new User(id));
			if(index == -1) throw new Exception("The record doesn't exists");
			users.remove(index);
		}
		
		@ApiMethod(name="update")
		public User updateUser(User u) throws Exception{ 
			int index = users.indexOf(u);
			if (index == -1) throw new Exception("Record does not exist");
			User user = users.get(index);
			user.setName(u.getName());
			user.setPhone(u.getPhone());
			user.setLastname(u.getLastname());
			user.setTid(u.getTid());
			user.setPassword(u.getPassword());
			return u;
		}
		
		public List<User> getUsers(){
			return users;
		}
		
		
		public User getUserbyId(Integer id) throws Exception{
			int index = users.indexOf(new User(id));
			if (index == -1)
				throw new Exception("Record does not exist");
			return users.get(index);
		}
		
	
}
