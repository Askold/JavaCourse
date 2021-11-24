package org.example.Models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class CarsList {
    @ElementList(inline = true)
    private List<Car> data;

    public CarsList(List<Car> data) {
        this.data = data;
    }

    public CarsList() { }

    public List<Car> getData() {
        return data;
    }

    public void setData(List<Car> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CarsList{" +
                "data=" + data +
                '}';
    }
}
