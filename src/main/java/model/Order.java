package model;

public class Order {
    private int id;
    private int idProduct;
    private String nameProduct;
    private int idClient;
    private String nameClient;
    private double quantity;

    public Order() { }

    public Order(int id, int idProduct, String nameProduct, int idClient, String nameClient, double quantity) {
        this.id = id;
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.idClient = idClient;
        this.nameClient = nameClient;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idProduct=" + idProduct +
                ", nameProduct='" + nameProduct + '\'' +
                ", idClient=" + idClient +
                ", nameClient='" + nameClient + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
