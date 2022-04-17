package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import bll.StudentBLL;
import model.Client;
import model.Order;
import model.Product;
import model.Student;
import presentation.Controller;
import presentation.View;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {

		ClientBLL ClientBll = new ClientBLL();
		StudentBLL StudentBll = new StudentBLL();
		ProductBLL ProductBll = new ProductBLL();
		OrderBLL OrderBll = new OrderBLL();

		Client Client1 = null;

		Student s1 = new Student(1234, "MTiugan Alex-Mihail", "Aleea Peana, nr.9", "alex.tiugan@yahoo.com", 20);
		Client newClient = new Client(1234, "Tiugan Alex-Mihail", "Aleea Peana, nr.9", "alex.tiugan@yahoo.com", 20);
		Client newClient2 = new Client(1112, "Mihailti", "Novaci fra", "mihalti@yahoo.com", 21);
		Product p = new Product(1111, "Mere", 22.5, "Kaufland", 3);
		Order o = new Order(1255, 1111, "Mere", 1234, "Ana", 2.5);

		try {

			View view = new View();
			Controller c = new Controller(view);

			//ClientBll.updateClient(newClient);
			//StudentBll.update(s1);
			//StudentBll.insert(s1);
			//ProductBll.insert(p);
			//OrderBll.insert(o);
			//ClientBll.insertClient(newClient2);
			//Client1 = ClientBll.findClientById(1112);
			//OrderBll.update(o);
			//for(Order o : OrderBll.findAll())
				//System.out.println(o);

		} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}

		// obtain field-value pairs for object through reflection
		//ReflectionExample.retrieveProperties(Client1);

	}

}
