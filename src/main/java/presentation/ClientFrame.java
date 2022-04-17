package presentation;
import bll.ClientBLL;
import model.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientFrame extends JFrame {

    JPanel mainPanel = new JPanel(new GridLayout(3, 1));
    JPanel buttonsPanel = new JPanel(new FlowLayout());
    JPanel displayPanel = new JPanel(new GridLayout(5,2));
    ClientBLL ClientBll = new ClientBLL();
    JButton displayTable = new JButton("Table Display");
    JButton showInformations = new JButton("Show Informations");
    JButton exitButton = new JButton("Exit");
    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Delete");
    JButton addButton = new JButton("Add");
    JComboBox<String> clients = new JComboBox<String>();
    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField addressField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField ageField = new JTextField();
    List<Client> c = new ArrayList<Client>();

    ClientFrame() {
        setContentPane(mainPanel);
        setTitle("ClientFrame");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(900, 600);

        c = ClientBll.findAll();

        for(Client  aux : c)
            clients.addItem(aux.getName());

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

        displayPanel.add(new JLabel("Adress"));
        displayPanel.add(addressField);

        displayPanel.add(new JLabel("Email"));
        displayPanel.add(emailField);

        displayPanel.add(new JLabel("Age"));
        displayPanel.add(ageField);

        mainPanel.add(clients);
        mainPanel.add(displayPanel);
        mainPanel.add(buttonsPanel);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        displayTable.addActionListener(new DisplayTableActionListener());
        showInformations.addActionListener(new showInformationsActionListener());
        addButton.addActionListener(new addActionListener());
        updateButton.addActionListener(new updateActionListener());
        deleteButton.addActionListener(new deleteActionListener());
    }

    class showInformationsActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = clients.getSelectedIndex();
            Client aux = ClientBll.findAll().get(selected);

            idField.setText(aux.getId() + "");
            nameField.setText(aux.getName());
            addressField.setText(aux.getAddress());
            emailField.setText(aux.getEmail());
            ageField.setText(aux.getAge() + "");
        }
    }

    class DisplayTableActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Table ct = new Table("client");
            ct.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    class addActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Client newClient = getClient();

            try {
                ClientBll.insertClient(newClient);
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

    class updateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Client newClient = getClient();

            try {
                ClientBll.updateClient(newClient);
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
                ClientBll.delete(Integer.parseInt(idField.getText()));
                JOptionPane.showMessageDialog(null, "Succesfully deleted");

                for(Client aux : c)
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

    public Client getClient()
    {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        int age = Integer.parseInt(ageField.getText());

        return new Client(id, name, address, email, age);
    }

    public void updateComboBox()
    {
        clients.removeAllItems();
        c = ClientBll.findAll();
        for(Client  aux : c)
            clients.addItem(aux.getName());
    }


}
