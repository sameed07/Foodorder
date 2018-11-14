package Model;

import java.util.List;

public class Request {

    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private List<com.example.sameedshah.foodorderserver.Model.Order> foods;



    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<com.example.sameedshah.foodorderserver.Model.Order> foods) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status = "0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<com.example.sameedshah.foodorderserver.Model.Order> getFoods() {
        return foods;
    }

    public void setFoods(List<com.example.sameedshah.foodorderserver.Model.Order> foods) {
        this.foods = foods;
    }
}
