/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.employee;

import br.sce.city.City;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Pedro Saraiva
 */
@Entity
public class Employee implements Serializable{
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(length = 1, nullable = false)
    private char genre;
    
    @Column(length = 150, nullable = false)
    private String name;
    
    @Column(length = 11, nullable = false)
    private String cpf;
    
    @Column(length = 20, nullable = false)
    private String rg;
    
    @Column(length = 150, nullable = false)
    private String address;
    
    @Column(length = 150, nullable = false)
    private String city;
    
    @Column(length = 150, nullable = false)
    private String district;
    
    @Column(length = 2, nullable = false)
    private String state;
    
    @Column(length = 10, nullable = false)
    private String cep;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Column(length = 15, nullable = false)
    private String phone;
    
    @Column(length = 10, nullable = false)
    private String registration;
    
    @Column(length = 100, nullable = false)
    private String imeiMobile;
    
    @Column(length = 100, nullable = false)
    private String passwordMobile;
    
    @Column(length = 1, nullable = false)
    private char type;
    
    @ManyToOne
    private City cityRegister;

    public City getCityRegister() {
        return cityRegister;
    }

    public void setCityRegister(City cityRegister) {
        this.cityRegister = cityRegister;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        hash = 37 * hash + this.genre;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.cpf);
        hash = 37 * hash + Objects.hashCode(this.rg);
        hash = 37 * hash + Objects.hashCode(this.address);
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.district);
        hash = 37 * hash + Objects.hashCode(this.state);
        hash = 37 * hash + Objects.hashCode(this.cep);
        hash = 37 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 37 * hash + Objects.hashCode(this.phone);
        hash = 37 * hash + Objects.hashCode(this.registration);
        hash = 37 * hash + Objects.hashCode(this.imeiMobile);
        hash = 37 * hash + Objects.hashCode(this.passwordMobile);
        hash = 37 * hash + this.type;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.genre != other.genre) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.rg, other.rg)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.district, other.district)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.registration, other.registration)) {
            return false;
        }
        if (!Objects.equals(this.imeiMobile, other.imeiMobile)) {
            return false;
        }
        if (!Objects.equals(this.passwordMobile, other.passwordMobile)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    private static final long serialVersionUID = -6805671368330593327L;
    
}
