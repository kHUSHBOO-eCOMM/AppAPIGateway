package com.app.accout.management.service.model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsersRequestModel {

    @NotNull(message = "FirstName cannot be null")
    @Size(min = 2, message = "First name can not be less than 2 char")
    private String firstName;

    @NotNull(message = "Lastname can`t be null")
    private String lastName;
    @NotNull(message = "Lastname can`t be null")
    @Size(min = 8, max = 16, message = "password should be 8 to16 char long")
    private String password;

    @NotNull(message = "please provide valid email")
    @Email(message = "provided email is not valid")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
