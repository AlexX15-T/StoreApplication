package bll.validators;

import javax.swing.*;

public class UnderStockException extends Exception {
    private String msg;

    public UnderStockException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void display()
    {
        JOptionPane.showMessageDialog(null, this.msg);
    }
}
