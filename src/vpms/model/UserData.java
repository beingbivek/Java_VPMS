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
    public UserData(String name,String type, String email, String password, String phone, byte[] image){
        this.name= name;
        this.type = type;
        this.email=email;
        this.password=password;
        this.phone = phone;
        this.image=image;
    }
    public UserData(int id,String name,String type,String email, String password, String phone, byte[] image){
        this.id=id;
        this.name= name;
        this.type = type;
        this.email=email;
        this.password=password;
        this.phone = phone;
        this.image=image;
    }
    public UserData(){
        
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setImage(byte[] image){
        this.image=image;
    }
    public byte[] getImage(){
        return this.image;
    }
    
}