package org.example.Models;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
    @CsvBindByPosition(position = 0)
    private static int ID;
    @CsvBindByPosition(position = 1)
    private static String Brand;
    @CsvBindByPosition(position = 2)
    private static String Model;
    @CsvBindByPosition(position = 3)
    private static int Price;

    public static String getBrand() {
        return Brand;
    }

    public static void setBrand(String brand) {
        Brand = brand;
    }

    public static String getModel() {
        return Model;
    }

    public static void setModel(String model) {
        Model = model;
    }

    public static int getPrice() {
        return Price;
    }

    public static void setID(int ID) {
        Car.ID = ID;
    }

    public static int getID() {
        return ID;
    }

    public static void setPrice(int price) {
        Car.Price = price;
    }

    public Car(int ID, String Brand, String Model, int Price) {
        Car.ID = ID;
        Car.Brand = Brand;
        Car.Model = Model;
        Car.Price = Price;
    }
}
