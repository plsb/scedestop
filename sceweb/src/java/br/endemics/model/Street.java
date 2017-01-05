/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.model;

import br.endemics.model.City;
import br.endemics.model.District;
import br.endemics.dao.DistrictDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Pedro Saraiva
 */
@Entity
public class Street implements Serializable{
    
    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    private City city;
    
    @Column(nullable = false)
    private int idQuarter;
    
    @ManyToOne
    private District district;
    
    @Column(nullable = false, length = 150)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuarter() {
        return idQuarter;
    }

    public void setIdQuarter(int idQuarter) {
        this.idQuarter = idQuarter;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        hash = 71 * hash + this.idQuarter;
        hash = 71 * hash + Objects.hashCode(this.district);
        hash = 71 * hash + Objects.hashCode(this.description);
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
        final Street other = (Street) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idQuarter != other.idQuarter) {
            return false;
        }
        if (!Objects.equals(this.district, other.district)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    private static final long serialVersionUID = -8605722466916892564L;
    
            
    
}
