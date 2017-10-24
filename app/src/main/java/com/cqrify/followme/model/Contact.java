package com.cqrify.followme.model;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class Contact {
    private String name;
    private String number;
    // private Uri imageUri;

    public Contact(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
