package com.educandoweb.cursomc.dto;

import com.educandoweb.cursomc.domain.Client;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClientDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;

    @NotEmpty(message = "The name is required!")
    @Length(min = 5, max = 120, message = "The name must contain between 5 and 120 characters")
    private String name;

    @NotEmpty(message = "The e-mail is required!")
    @Email(message = "Invalid E-mail!")
    private String email;

    public ClientDTO(){
    }

    public ClientDTO(Client obj){
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
