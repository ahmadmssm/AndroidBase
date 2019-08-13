package ams.androidbase.androidbase;

import java.util.Date;

public class Person {
    public String firstName;
    public String lastName;
    public String date;
    public String contact;
    public Person(String firstName, String lastName,
                  Date date, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date.toString();
        this.contact = contact;
    }
    public String toString() {
        return "[" + firstName + " " + lastName +
                " \"" + date.toString() + "\" " +contact +"]";
    }
}