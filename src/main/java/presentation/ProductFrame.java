package presentation;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductFrame extends JFrame {

    JPanel mainPanel = new JPanel(new GridLayout(3, 1));
    JPanel buttonsPanel = new JPanel(new FlowLayout());
    JPanel displayPanel = new JPanel(new GridLayout(5,2));
    ProductBLL ProductBll = new ProductBLL();
    JButton showInformations = new JButton("Show Informations");
    JButton displayTable = new JButton("Table Display");
    JButton exitButton = new JButton("Exit");
    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");
    JButton addButton = new JButton("Add");
    JComboBox<String> products = new JComboBox<String>();
    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField stockField = new JTextField();
    JTextField manufacturerField = new JTextField();
    List<Product> c = new ArrayList<Product>();

    ProductFrame() {
        setContentPane(mainPanel);
        setTitle("ProductFrame");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(900, 600);

        c = ProductBll.findAll();

        for(Product  aux : c)
            products.addItem(aux.getName());

        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(showInformations);
        buttonsPanel.add(displayTable);
        buttonsPanel.add(exitButton);

        displayPanel.add(new JLabel("ID"));
        displayPanel.add(idField);

        displayPanel.add(new JLabel("Name"));
        displayPanel.add(nameField);

        displayPanel.add(new JLabel("Price"));
        displayPanel.add(priceField);

        displayPanel.add(new JLabel("Manufacturer"));
        displayPanel.add(manufacturerField);

        displayPanel.add(new JLabel("Stock"));
        displayPanel.add(stockField);

        mainPanel.add(products);
        mainPanel.add(displayPanel);
        mainPanel.add(buttonsPanel);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        showInformations.addActionListener(new ProductFrame.showInformationsActionListener());
        addButton.addActionListener(new ProductFrame.addActionListener());
        displayTable.addActionListener(new DisplayTableActionListener());
        updateButton.addActionListener(new ProductFrame.updateActionListener());
        deleteButton.addActionListener(new ProductFrame.deleteActionListener());
    }

    class showInformationsActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = products.getSelectedIndex();
            Product aux = ProductBll.findAll().get(selected);

            idField.setText(aux.getId() + "");
            nameField.setText(aux.getName());
            priceField.setText(aux.getPrice() + "");
            manufacturerField.setText(aux.getManufacturer());
            stockField.setText(aux.getStock() + "");
        }
    }

    class addActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Product newProduct = getProduct();

            try {
                ProductBll.insert(newProduct);
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
            }

        }
    }

    class DisplayTableActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Table pt = new Table("product");
            pt.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    class updateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Product newProduct = getProduct();

            try {
                ProductBll.update(newProduct);
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
            }

        }
    }

    class deleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ProductBll.delete(Integer.parseInt(idField.getText()));
                JOptionPane.showMessageDialog(null, "Succesfully deleted");

                for(Product aux : c)
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

    public Product getProduct()
    {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        String manufacturer = manufacturerField.getText();
        double stock = Double.parseDouble(stockField.getText());

        return new Product(id, name, price, manufacturer, stock);
    }

    public void updateComboBox()
    {
        products.removeAllItems();
        c = ProductBll.findAll();
        for(Product  aux : c)
            products.addItem(aux.getName());
    }
    
}
