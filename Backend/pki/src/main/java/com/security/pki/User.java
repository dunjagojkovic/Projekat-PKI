package com.security.pki;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String description;
    private String type;
    private String status;
    private String registrationToken;
    private Long penalty;
    private String category;
    private String reasonForDelete;

    @Version
    private Integer version;

    public Integer getVersion() { return version;}
    public void setVersion(Integer version) { this.version = version; }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getAddress(){
        return  address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public  String getPhoneNumber(){
        return  phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public  String getDescription(){
        return  description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getType(){
        return  type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String  getRegistrationToken() {return registrationToken;}
    public void setRegistrationToken(String registrationToken) {this.registrationToken = registrationToken;}
    public String getRole() { return type.toString(); }
    public String getStatus() {return status;}
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getPenalty(){return penalty;}
    public void setPenalty(Long penalty){this.penalty = penalty;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}
    public String getReasonForDelete(){return reasonForDelete;}
    public void setReasonForDelete(String reasonForDelete){this.reasonForDelete = reasonForDelete;}
}
