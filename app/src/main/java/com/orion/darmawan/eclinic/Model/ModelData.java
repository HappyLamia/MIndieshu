package com.orion.darmawan.eclinic.Model;

public class ModelData {

    private String id, name, gender,email, birthday, phone;

    public ModelData(String id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }


}

