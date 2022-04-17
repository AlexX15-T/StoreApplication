package bll;

import bll.validators.PriceValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;
import model.Product;
import model.Product;
import model.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new PriceValidator());

        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The Product with id =" + id + " was not found!");
        }
        return st;
    }

    public List <Product> findAll() {
        List < Product > st = productDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("The Product table is empty!");
        }
        return st;
    }

    public Product insert(Product t) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        Product st = productDAO.insert(t);
        if (st == null) {
            throw new NoSuchElementException("The Product with id =" + t.getId() + " was not updated!");
        }
        return st;
    }

    public Product update(Product t) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Product st = productDAO.update(t);
        if (st == null) {
            throw new NoSuchElementException("The Product with id =" + t.getId() + " was not updated!");
        }
        return st;
    }

    public void delete(int id) throws Exception {
        try {
            productDAO.delete(id);
        }
        catch (Exception e){
            throw new NoSuchElementException("The client with id =" + id + " was not deleted!");
        }
    }

}
