package bll;

import bll.validators.*;
import dao.ClientDAO;
import model.Client;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new ClientEmailValidator());
        validators.add(new ClientAgeValidator());
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

    public List < Client > findAll() {
        List < Client > st = clientDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("The client table is empty!");
        }
        return st;
    }

    public Client updateClient(Client t) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Client st = clientDAO.update(t);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + t.getId() + " was not upated!");
        }
        return st;
    }

    public Client insertClient(Client t) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Client st = clientDAO.insert(t);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + t.getId() + " was not added!");
        }
        return st;
    }

    public void delete(int id) throws Exception {
        try {
            clientDAO.delete(id);
        }
        catch (Exception e){
            throw new NoSuchElementException("The client with id =" + id + " was not deleted!");
        }
    }

}
