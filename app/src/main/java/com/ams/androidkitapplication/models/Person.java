package com.ams.androidkitapplication.models;

import java.util.Date;

@SuppressWarnings("unused")
public class Person {
    private String firstName;
    private String lastName;
    private String date;
    private String contact;

    public Person(String firstName, String lastName, Date date, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date.toString();
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDate() {
        return date;
    }

    public String getContact() {
        return contact;
    }
    //
    @SuppressWarnings("NullableProblems")
    public String toString() {
        return "[" + firstName + " " + lastName + " \"" + date + "\" " +contact +"]";
    }
}