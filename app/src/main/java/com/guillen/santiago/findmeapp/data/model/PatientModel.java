package com.guillen.santiago.findmeapp.data.model;

public class PatientModel {
    private String id;
    private int number;
    private String name;
    private String surname;
    private String documentType = "DNI"; //set DNI as default
    private int documentNumber;
    private int age;
    private String sex;
    private String sickness;
    private String sicknessLevel;


    public PatientModel() {
    }

    public PatientModel(String id, int number, String name, String surname, String documentType, int documentNumber, int age, String sex, String sickness, String sicknessLevel) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.age = age;
        this.sex = sex;
        this.sickness = sickness;
        this.sicknessLevel = sicknessLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDocumentType() {
        return "DNI";
    }

//    public void setDocumentType(String documentType) {
//        this.documentType = documentType;
//    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getSicknessLevel() {
        return sicknessLevel;
    }

    public void setSicknessLevel(String sicknessLevel) {
        this.sicknessLevel = sicknessLevel;
    }
}
