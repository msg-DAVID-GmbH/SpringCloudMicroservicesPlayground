package mafoe.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * JPA entity modeling an order.
 */
@Table(name = "ORDER_TABLE")
@Entity
public class Order extends DemoEntity {

    @NotNull
    private String brand;
    @NotNull
    private String country;
    @NotNull
    private String color;
    @NotNull
    private int doors;

    public Order() {
    }

    public Order(String brand, String country, String color, int doors) {
        this.brand = brand;
        this.country = country;
        this.color = color;
        this.doors = doors;
    }

    public String getCountry() {
        return country;
    }

    public String getColor() {
        return color;
    }

    public int getDoors() {
        return doors;
    }

    public String getBrand() {
        return brand;
    }
}
