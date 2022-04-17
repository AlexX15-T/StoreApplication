package presentation;
import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import bll.StudentBLL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    View startFrame;
    ClientBLL ClientBll = new ClientBLL();
    StudentBLL StudentBll = new StudentBLL();
    ProductBLL ProductBll = new ProductBLL();
    OrderBLL OrderBll = new OrderBLL();

    public Controller(View view)
    {
        startFrame = view;
        startFrame.setVisible(true);
        startFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        startFrame.addAllButtons(new ClientActionListener(), new ProductActionListener(), new OrderActionListener(), new ExitActionListener());



    }

    public class ExitActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            startFrame.dispose();
        }
    }
    
    public class ClientActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame clientFrame = new ClientFrame();
            clientFrame.setVisible(true);
            clientFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    public class ProductActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame productFrame = new ProductFrame();
            productFrame.setVisible(true);
            productFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    public class OrderActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame orderFrame = new OrderFrame();
            orderFrame.setVisible(true);
            orderFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }


}
