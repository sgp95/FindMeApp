package com.guillen.santiago.findmeapp.view.model;

public enum UserType {
    ERROR("Tipo de usario no Encontrado"),
    PATIENT("Paciente"),
    CARE_TAKER("Cuidador");

    private String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserType getByType(String type){
        for(UserType userType : UserType.values()){
            if(userType.getValue().equals(type)){
                return userType;
            }
        }
        return ERROR;
    }
}
