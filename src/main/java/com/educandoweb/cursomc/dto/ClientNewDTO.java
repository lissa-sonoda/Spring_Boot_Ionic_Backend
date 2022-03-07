package com.educandoweb.cursomc.dto;

import com.educandoweb.cursomc.services.validation.ClientInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClientInsert
public class ClientNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "The name is required!")
    @Length(min = 5, max = 120, message = "The name must contain between 5 and 120 characters")
    private String name;

    @NotEmpty(message = "The e-mail is required!")
    @Email(message = "Invalid E-mail!")
    private String email;

    @NotEmpty(message = "SSN/EIN is required!")
    private String ssnOrEin;

    private Integer type;

    @NotEmpty(message = "A password is required!")
    private String password;

    @NotEmpty(message = "An address is required!")
    private String streetAddress;

    @NotEmpty(message = "The number is required!")
    private String number;

    private String addressLine;

    private String region;

    @NotEmpty(message = "Postal code is required!")
    private String postalCode;

    @NotEmpty(message = "At least one phone number is required!")
    private String phoneNumber1;

    private String phoneNumber2;

    private String phoneNumber3;

    private Integer cityId;

    public ClientNewDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsnOrEin() {
        return ssnOrEin;
    }

    public void setSsnOrEin(String ssnOrEin) {
        this.ssnOrEin = ssnOrEin;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(String phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
