package presentation;

import bll.OrderBLL;
import bll.validators.UnderStockException;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderFrame extends JFrame {
    JPanel mainPanel = new JPanel(new GridLayout(3, 1));
    JPanel buttonsPanel = new JPanel(new FlowLayout());
    JPanel displayPanel = new JPanel(new GridLayout(6,2));
    OrderBLL OrderBll = new OrderBLL();
    JButton showInformations = new JButton("Show Informations");
    JButton exitButton = new JButton("Exit");
    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");
    JButton addButton = new JButton("Add");
    JComboBox<String> orders = new JComboBox<String>();
    JTextField idField = new JTextField();
    JTextField idProductField = new JTextField();
    JTextField nameProductField = new JTextField();
    JTextField idClientField = new JTextField();
    JTextField nameClientField = new JTextField();
    JTextField quantityField = new JTextField();
    List<Order> c = new ArrayList<Order>();

    OrderFrame() {
        setContentPane(mainPanel);
        setTitle("OrderFrame");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(900, 600);

        c = OrderBll.findAll();

        for(Order  aux : c)
            orders.addItem(aux.getId() + "");

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(showInformations);
        buttonsPanel.add(exitButton);

        displayPanel.add(new JLabel("ID Order"));
        displayPanel.add(idField);

        displayPanel.add(new JLabel("ID Product"));
        displayPanel.add(idProductField);

        displayPanel.add(new JLabel("Name Product"));
        displayPanel.add(nameProductField);

        displayPanel.add(new JLabel("ID Client"));
        displayPanel.add(idClientField);

        displayPanel.add(new JLabel("Name Client"));
        displayPanel.add(nameClientField);

        displayPanel.add(new JLabel("Quantity"));
        displayPanel.add(quantityField);

        mainPanel.add(orders);
        mainPanel.add(displayPanel);
        mainPanel.add(buttonsPanel);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        showInformations.addActionListener(new OrderFrame.showInformationsActionListener());
        addButton.addActionListener(new OrderFrame.addActionListener());
        updateButton.addActionListener(new OrderFrame.updateActionListener());
        deleteButton.addActionListener(new OrderFrame.deleteActionListener());
    }

    class showInformationsActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = orders.getSelectedIndex();
            Order aux = OrderBll.findAll().get(selected);

            idField.setText(aux.getId() + "");
            idProductField.setText(aux.getIdProduct() + "");
            nameProductField.setText(aux.getNameProduct());
            idClientField.setText(aux.getIdClient() + "");
            nameClientField.setText(aux.getNameClient());
            quantityField.setText(aux.getQuantity() + "");
        }
    }

    class addActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Order newOrder = getOrder();

            try {
                OrderBll.insert(newOrder);
                JOptionPane.showMessageDialog(null, "Succesfully added");
                updateComboBox();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (UnderStockException underStockException) {
                JOptionPane.showMessageDialog(null, "Error: Understock");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    class updateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Order newOrder = getOrder();

            try {
                OrderBll.update(newOrder);
                JOptionPane.showMessageDialog(null, "Succesfully updated");
                updateComboBox();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (UnderStockException underStockException) {
                JOptionPane.showMessageDialog(null, "Error: Understock");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    class deleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                OrderBll.delete(Integer.parseInt(idField.getText()));
                JOptionPane.showMessageDialog(null, "Succesfully deleted");

                for(Order aux : c)
                    if(aux.getId() == Integer.parseInt(idField.getText()))
                    {
                        c.remove(aux);
                        break;
                    }

                updateComboBox();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    public Order getOrder()
    {
        int id = Integer.parseInt(idField.getText());
        int idP = Integer.parseInt(idProductField.getText());
        String nameP = nameProductField.getText();
        int idC = Integer.parseInt(idClientField.getText());
        String nameC = nameClientField.getText();
        double quantity = Double.parseDouble(quantityField.getText());

        return new Order(id, idP, nameP, idC, nameC, quantity);
    }

    public void updateComboBox()
    {
        orders.removeAllItems();
        c = OrderBll.findAll();
        for(Order  aux : c)
            orders.addItem(aux.getId() + "");
    }
}
