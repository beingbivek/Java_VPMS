/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author being
 */
public class UserData {
    private int id;
    private String name;
    private String type;
    private String email;
    private String password;
    private String phone;
    private  byte[] image;
    private String status;
    
    public UserData(String name,String type, String email, String password, String phone, byte[] image, String status){
        this.name= name;
        this.type = type;
        this.email=email;
        this.password=password;
        this.phone = phone;
        this.image=image;
        this.status = status;
    }
    public UserData(int id,String name,String type,String email, String password, String phone, byte[] image, String status){
        this.id=id;
        this.name= name;
        this.type = type;
        this.email=email;
        this.password=password;
        this.phone = phone;
        this.image=image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}