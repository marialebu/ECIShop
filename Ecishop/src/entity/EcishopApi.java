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
	
	public static List<Purchase> purchases = new ArrayList<Purchase>();
	
	private static String[] categories = {"All Categories", "Automotive & Industrial", "Beauty, Health & Grocery", "Books", "Clothing, Shoes & Jewelry", "Home, Garden & Tools", "Movies, Music & Games", "Sports & Outdoors","Toys, Kids & Baby", "Electronics & Computers"};
	
	@ApiMethod(name="addUser")
	public User addUser(@Named("id")String id, 
					@Named("tid") String tid, 
					@Named("name")String name, 
					@Named("lastname") String last,
					@Named("phone") String phone,
					@Named("gender") String gen, 
					@Named("birthday") String birth,
					@Named("email")String email 
					) throws Exception{
		int index = users.indexOf(new User(id));
		if( index != -1) throw new Exception("The record already exists");
		if(!validateUser(id, tid, email, birth, gen))throw new Exception("Invalid fields");
		String[] r = birth.split("/");
		User user = new User(id, tid, name, last, phone, gen, email, Integer.parseInt(r[0]), Integer.parseInt(r[1]), Integer.parseInt(r[2]));
		users.add(user);
		return user;
	}
	
	private boolean validateUser(String id, String tid, String email, String birth, String gen) {
		try{
			Integer.parseInt(id);
			Calendar c = new GregorianCalendar();
			String[] r = birth.split("/");
			int y = Integer.parseInt(r[2]);
			int m = Integer.parseInt(r[1]);
			int d = Integer.parseInt(r[0]);
			return Validator.validateEmail(email) &&  gUserByEmail(email) == null && !existUserById(id) && (tid.equals("CC") || tid.equals("CE")) ? id.length()>7 : 
				id.length()>4 && (gen.equals("F") || gen.equals("M")) && r.length == 3 && c.get(Calendar.YEAR)-y >= 18 && c.get(Calendar.YEAR) > y && m>=0 && m <=12 && d>=0 && d<=31;
		}catch (Exception e){
			return false;
		}
		
	}
	
	@ApiMethod(name="setadditionalUserData")
	public User setadditionalUserData(@Named("id")String id, 
					@Named("adress")String address, 
					@Named("paymentmethod") String paymentmethod,
					@Named("cardnumber") String cardnumber) throws Exception{ 
		int index = users.indexOf(new User(id));
		if (index == -1) throw new Exception("Record does not exist");
		if(cardnumber.length()<16 || !Validator.validateCardNumber(cardnumber)) throw new Exception("Invalid card number");
		User user = users.get(index);
		user.setAddress(address);
		user.setPaymentMethod(paymentmethod);
		user.setCardNumber(cardnumber);
		return user;
	}
	
	
	@ApiMethod(name="removeUser")
	public void removeUser(@Named("id") String id) throws Exception{
		int index = users.indexOf(new User(id));
		if(index == -1) throw new Exception("The record doesn't exists");
		users.remove(index);
	}
	
	@ApiMethod(name="updateUser")
	public User updateUser(@Named("id")String id, 
					@Named("name")String name, 
					@Named("lastname") String last, 
					@Named("password")String pass) throws Exception{
		
		int index = users.indexOf(new User(id));
		if (index == -1) throw new Exception("Record does not exist");
		User user = users.get(index);
		if(!validateUserUpdate(name, last)) throw new Exception("Invalid fields");
		user.setName(name);
		user.setLastname(last);
		if(!Validator.validatePassword(pass)) throw new Exception(" password must be eight "
				+ "characters including"
				+ " one special character and alphanumeric characters.");
		user.setPassword(pass);
		return user;
	}
	
	private boolean validateUserUpdate(String name, String last) {
		return name != null && last !=null && !name.isEmpty() && !last.isEmpty();
	}
	
	@ApiMethod(name="list")
	public List<User> getUsers(){
		return users;
	}
	
	private boolean existUserById(@Named("id") String id){
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
	public User getUserbyId(@Named("id") String id) throws Exception{
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
	
	@ApiMethod(name="userbyEmail", path="user_email")
	public User getUserbyEmail(@Named("email") String email) throws Exception{
		User res = gUserByEmail(email);
		if(res == null) throw new Exception("The user doesn't exists");
		return res;
	}
	
	private User gUserByEmail(String email){
		User res = null; 
		for(User u : users){
			if(u.getEmail().equals(email)){
				res = u;
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
		if(!r) throw new Exception("User doesnt exist");
		if(r && !u.getPassword().equals(pass)) throw new Exception("Invalid user or password");
		return u;
	}
	
//---------------------------------------------PRODUCTS-------------------------------------------------------------
		
		
	@ApiMethod(name="addProduct")
	public Product addProduct(@Named("type")String type, 
					@Named("name")String name, 
					@Named("desc") String desc,
					@Named("image")String image,
					@Named("units")Integer units,
					@Named("price")Float price, 
					@Named("seller") String idse) throws Exception{
		String id = products.size()+"";
		if(!validator(id, type, name, desc, image, units, price))throw new Exception("Invalid fields");
		int index = users.indexOf(new User(idse)); 
		if( index == -1) throw new Exception("The seller doesn't exists");
		Product product = new Product(id, type, name, desc, image, units, price, users.get(index));
		products.add(product);
		return new Product(id);
	}
	
	private boolean validator(String id, String type, String name,
			String desc, String image, Integer units, Float price) {
		return id!=null && containsCategory(type) && name != null && desc != null && image != null  && price!=null && price>0;
	}
	
	private boolean containsCategory(String type){
		boolean sePuede = true;
		for(int i = 0 ; i < categories.length && !sePuede; i++){
			sePuede = categories[i].equals(type);
		}
		return sePuede;
	}
	
	
	@ApiMethod(name="updateProduct")
	public Product updateProduct(@Named("id")String id, 
					@Named("type") String type, 
					@Named("name")String name, 
					@Named("desc") String desc,
					@Named("image")String image,
					@Named("units")Integer units,
					@Named("price")Float price) throws Exception{ 
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
	public void removeProduct(@Named("id") String id) throws Exception{
		int index = products.indexOf(new Product(id));
		if(index == -1) throw new Exception("The record doesn't exists");
		products.remove(index);
	}
		
	@ApiMethod(name="sellProduct")
	public void sellProduct(@Named("id") String id) throws Exception{
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
	public Product getProductById(@Named("id") String id) throws Exception{
		boolean existe = false;
		int index = -1;
		for(int i = 0 ; i < products.size() && !existe; i++){
			if(products.get(i).getId().equals(id)){
				existe=true;
				index=i;
			}
		}
		if (!existe) throw new Exception("Record does not exist");
		return products.get(index);
	}
	
	@ApiMethod(name="productsByType", path="product_type")
	public List<Product> getProductByType(@Named("type") String type) throws Exception{
		List<Product> resp = new ArrayList<Product>();
		for(Product p : products){
			String tipo = p.getType();
			if(tipo.equals(type) || type.equals("All Categories")){
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
			if(name.equals(nombre) || name.contains(nombre)){
				resp.add(p);
			}
		}
		if (resp.isEmpty())throw new Exception("Record does not exist");
		return resp;
	}
	
	@ApiMethod(name="productsByUniquePrice", path="product_uprice")
	public List<Product> getProductsByUniquePrice(@Named("price") Float price) throws Exception{
		List<Product> resp = new ArrayList<Product>();
		for(Product p : products){
			float precio = p.getPrice();
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
			float precio = p.getPrice();
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
	
	@ApiMethod(name="productsByMostSold", path="products_msold")
	public ArrayList<Product> getProductsByMostSold() throws Exception{
		ArrayList<Product> resp = new ArrayList<Product>();
		ArrayList<Product> temp;
		for(Sale s : sales){
			temp = s.getProducts();
			for(Product p : temp){
				if(!resp.contains(p)){
					resp.add(p);
				}
			}
		}
		Collections.sort(resp);
		return resp;
	}

	
//--------------------------------------------SALES-------------------------------------------------------------
	/*
	 * Agregar una venta.
	 * @params	userId	Comprador
	 * 			prods	Productos
	 */
	@ApiMethod(name="addSale")
	public Sale addSale(@Named("userId")String userId,
						@Named("productId") String prodsIds
		) throws Exception{
		String id = Integer.toString(sales.size());
		int index = users.indexOf(new User(userId));
		if( index == -1) throw new Exception("The user doesnt exists");
		User user = users.get(index);
		ArrayList<Product> productsSale = new ArrayList<Product>();
		String[] prods = prodsIds.split("-");
		for(String productId : prods){
			index = products.indexOf(new Product(productId));
			if( index == -1) throw new Exception("The product doesnt exists");
			Product product = products.get(index);
			boolean existenUnidades = product.removeUnit();
			if(!existenUnidades){
				products.remove(index);
			}
			productsSale.add(product);
		}
		Sale sale = new Sale(id, user, productsSale);
		sales.add(sale);
		return sale;	
	}
	
	@ApiMethod(name="listSales")
	public List<Sale> getSales(){
		return sales;
	}
	
	@ApiMethod(name="saleById")
	public Sale getSaleById(@Named("id") String id) throws Exception{
		int index = sales.indexOf(new Sale(id));
		if( index == -1) throw new Exception("The record doesn't exists");
		return sales.get(index);
	}
	
	@ApiMethod(name="salesbyUserId", path="sales_uid")
	public ArrayList<Sale> getSalesbyUserId(@Named("id") String id) throws Exception{
		ArrayList<Sale> resp = new ArrayList<Sale>();
		for(Sale s : sales){
			if(s.getId().equals(id)){
				resp.add(s);
			}
		}
		return resp;
	}
	
	@ApiMethod(name="salesbyProductId", path="sales_pid")
	public ArrayList<Sale> getSalesbyProductId(@Named("id") String id) throws Exception{
		int index = sales.indexOf(new Sale(id));
		if( index == -1) throw new Exception("The user doesnt exists");
		ArrayList<Sale> resp = new ArrayList<Sale>();
		for(Sale s : sales){
			if(s.contieneProducto(id)){
				resp.add(s);
			}
		}
		return resp;
	}
	
	/*
	 * Buscar ventas por vendedor. 
	 */
	@ApiMethod(name="salesbySeller")
	public ArrayList<Sale> getSalesBySellerId(@Named("id") String id) throws Exception{
		ArrayList<Sale> resp = new ArrayList<Sale>();
		for(Sale s : sales){
			if(s.contieneVendedor(id)){
				resp.add(s);
			}
		}
		return resp;
	}
	
	//----------------------------------------------PURCHASES-------------------------------------------------------------------
	
	/*
	 * Agregar una venta.
	 * @params	userId	Comprador
	 * 			prods	Productos
	 */
	@ApiMethod(name="addPurchase")
	public Purchase addPurchase(@Named("userId")String userId,
						@Named("productId") String prodsIds
		) throws Exception{
		String id = Integer.toString(purchases.size());
		int index = users.indexOf(new User(userId));
		if( index == -1) throw new Exception("The user doesnt exists");
		User user = users.get(index);
		ArrayList<Product> productsSale = new ArrayList<Product>();
		String[] prods = prodsIds.split("-");
		for(String productId : prods){
			index = products.indexOf(new Product(productId));
			if( index == -1) throw new Exception("The product doesnt exists");
			Product product = products.get(index);
			productsSale.add(product);
		}
		Purchase purch = new Purchase(id, user, productsSale);
		purchases.add(purch);
		return purch;	
	}
	
	@ApiMethod(name="listPurchases")
	public List<Purchase> getPurchases(){
		return purchases;
	}
	
	@ApiMethod(name="purchaseById")
	public Purchase getPurchaseById(@Named("id") String id) throws Exception{
		int index = purchases.indexOf(new Purchase(id));
		if( index == -1) throw new Exception("The record doesn't exists");
		return purchases.get(index);
	}
	
	@ApiMethod(name="purchasesByUserId", path="purchases_uid")
	public ArrayList<Purchase> getPurchasesbyUserId(@Named("id") String id) throws Exception{
		ArrayList<Purchase> resp = new ArrayList<Purchase>();
		for(Purchase s : purchases){
			if(s.getId().equals(id)){
				resp.add(s);
			}
		}
		return resp;
	}
	
	@ApiMethod(name="purchasesByProductId", path="purchases_pid")
	public ArrayList<Purchase> getPurchasesbyProductId(@Named("id") String id) throws Exception{
		int index = purchases.indexOf(new Purchase(id));
		if( index == -1) throw new Exception("The user doesnt exists");
		ArrayList<Purchase> resp = new ArrayList<Purchase>();
		for(Purchase p : purchases){
			if(p.contieneProducto(id)){
				resp.add(p);
			}
		}
		return resp;
	}
	
	/*
	 * Buscar compras por vendedor. 
	 */
	@ApiMethod(name="purchasesBySeller")
	public ArrayList<Purchase> getPurchasesBySellerId(@Named("id") String id) throws Exception{
		ArrayList<Purchase> resp = new ArrayList<Purchase>();
		for(Purchase s : purchases){
			if(s.contieneVendedor(id)){
				resp.add(s);
			}
		}
		return resp;
	}
	
	//----------------------------Others-------------------------------------------------------
	
	@ApiMethod(name="getCategories")
	public String[] getCategories(){
		return categories;
	}
}