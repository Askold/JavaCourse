package org.example.Models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

@Root(name = "Car")
public class Car implements Serializable {

    @CsvBindByName
    @CsvBindByPosition(position = 0)
    @Attribute
    private long id = System.currentTimeMillis() + count;

    @CsvBindByName
    @CsvBindByPosition(position = 1)
    @Element(name = "Brand")
    private String brand;

    @CsvBindByName
    @CsvBindByPosition(position = 2)
    @Element(name = "Model")
    private String model;

    @CsvBindByName
    @CsvBindByPosition(position = 3)
    @Element(name = "Price")
    private int price;

    private static int count = 0;

    public Car(String Brand, String Model, int Price) {
        this.brand = Brand;
        this.model = Model;
        this.price = Price;
        count++;
    }

    public Car(long id, String brand, String model, int price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        count++;
    }

    public Car() {
        count++;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && price == car.price && Objects.equals(brand, car.brand) && Objects.equals(model, car.model);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, price);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
