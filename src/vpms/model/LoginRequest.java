/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author being
 */
public class LoginRequest {
    private String email;
    private String password;
    public LoginRequest(String email,String password){
        this.email=email;
        this.password=password;
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
    
}
