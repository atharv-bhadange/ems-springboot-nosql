package com.employwise.assignment.employeeservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;



import java.util.UUID;

@Document
public class Employee {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotEmpty(message = "The full name is required.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotEmpty(message = "The phone number is required.")
    @Pattern(regexp = "\\d{11}", flags = { Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE }, message = "The phone number is invalid.")
    private String phoneNo;

    // reportsTo is the id of the employee to whom the current employee reports to
    // reportsTo is null for the top level employee
    private String reportsTo;

    private String profileImg;

    public Employee() {
        this.id = UUID.randomUUID().toString();
    }

    public Employee(String name, String email, String phoneNo, String reportsTo, String profileImg) {
        super();
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.reportsTo = reportsTo;
        this.profileImg = profileImg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public String getProfileImg() {
        return profileImg;
    }
}
