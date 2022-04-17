package bll;
import bll.validators.UnderStockException;
import bll.validators.Validator;
import dao.OrderDAO;
import model.*;
import model.Order;
import model.Order;
import model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO OrderDAO;
    private ProductBLL ProductBll;

    public OrderBLL() {
        validators = new ArrayList<Validator<Order>>();
        OrderDAO = new OrderDAO();
        ProductBll = new ProductBLL();
    }

    public Order findOrderById(int id) {
        Order st = OrderDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The Order with id = " + id + " was not found!");
        }
        return st;
    }

    public List <Order> findAll() {
        List < Order > st = OrderDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("The Order table is empty!");
        }
        return st;
    }

    public Order insert(Order t) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, UnderStockException, IOException {
        if(t.getQuantity() > ProductBll.findProductById(t.getIdProduct()).getStock())
            throw new UnderStockException("Error: Understock");

        Product newProduct = ProductBll.findProductById(t.getIdProduct());
        newProduct.setStock(newProduct.getStock() - t.getQuantity());
        Order st = OrderDAO.insert(t);
        ProductBll.update(newProduct);

        FileWriter myWritter = new FileWriter(t.getId() + ".txt");
        myWritter.write(createBill(t));
        myWritter.close();

        if (st == null) {
            throw new NoSuchElementException("The Order with id =" + t.getId() + " was not added!");
        }
        return st;
    }

    public Order update(Order t) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, UnderStockException, IOException {
        if(t.getQuantity() > ProductBll.findProductById(t.getIdProduct()).getStock())
            throw new UnderStockException("Error: Understock");

        Product newProduct = ProductBll.findProductById(t.getIdProduct());
        newProduct.setStock(newProduct.getStock() - t.getQuantity());
        Order st = OrderDAO.update(t);
        ProductBll.update(newProduct);

        FileWriter myWritter = new FileWriter(t.getId() + ".txt");
        myWritter.write(createBill(t));
        myWritter.close();

        if (st == null) {
            throw new NoSuchElementException("The Order with id =" + t.getId() + " was not updated!");
        }
        return st;
    }

    public void delete(int id) throws Exception {
        try {
            OrderDAO.delete(id);
        }
        catch (Exception e){
            throw new NoSuchElementException("The Order with id =" + id + " was not deleted!");
        }
    }

    public String createBill(Order t)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Product p = ProductBll.findProductById(t.getIdProduct());
        double totalPrice = p.getPrice() * t.getQuantity();

        StringBuilder sb = new StringBuilder();
        sb.append(t.getNameClient() + " " + t.getId() + '\n');
        sb.append(t.getNameProduct() + "\t\t" + t.getQuantity() + " x " + p.getPrice() + '\n');
        sb.append("Total" + "\t\t" + totalPrice + '\n');
        sb.append(dtf.format(now));

        return sb.toString();
    }

}
