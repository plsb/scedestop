package sce.br.model;


public class Employee {

    private Integer id;

    private String name;

    private String cpf;

    private String registration;

    private String imeiMobile;

    private String passwordMobile;

    private char type;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getImeiMobile() {
        return imeiMobile;
    }

    public void setImeiMobile(String imeiMobile) {
        this.imeiMobile = imeiMobile;
    }

    public String getPasswordMobile() {
        return passwordMobile;
    }

    public void setPasswordMobile(String passwordMobile) {
        this.passwordMobile = passwordMobile;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
