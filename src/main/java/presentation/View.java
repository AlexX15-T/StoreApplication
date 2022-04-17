package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JPanel mainPanel = new JPanel(new GridLayout(4, 1));
    private JButton clientButton;
    private JButton productButton;
    private JButton orderButton;
    private JButton exitButton;

    public View() {
        setContentPane(mainPanel);
        setTitle("Menu");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 600);

        clientButton = new JButton("Client");
        productButton = new JButton("Product");
        orderButton = new JButton("Order");
        exitButton = new JButton("Exit");

        mainPanel.add(clientButton);
        mainPanel.add(productButton);
        mainPanel.add(orderButton);
        mainPanel.add(exitButton);
    }

    public void addAllButtons(ActionListener l1, ActionListener l2, ActionListener l3, ActionListener l4)
    {
        setClientButtonListener(l1);
        setProductButtonListener(l2);
        setOrderButtonListener(l3);
        setExitButtonListener(l4);
    }

    private void setClientButtonListener(ActionListener l)
    {
        clientButton.addActionListener(l);
    }

    private void setProductButtonListener(ActionListener l)
    {
        productButton.addActionListener(l);
    }

    private void setOrderButtonListener(ActionListener l)
    {
        orderButton.addActionListener(l);
    }

    private void setExitButtonListener(ActionListener l)
    {
        exitButton.addActionListener(l);
    }

}
