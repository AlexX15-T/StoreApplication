package bll.validators;

import bll.ProductBLL;
import model.Product;
import model.Student;

public class PriceValidator implements Validator<Product> {
    private static final Double  MIN_PRICE = 0d;
    private static final Double MAX_PRICE = Double.MAX_VALUE;

    public void validate(Product t) {

        if (t.getPrice() < MIN_PRICE || t.getPrice() > MAX_PRICE) {
            throw new IllegalArgumentException("The Price limit is not respected!");
        }

    }
}
