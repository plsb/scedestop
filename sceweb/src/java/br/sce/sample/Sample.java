/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.sample;

import br.sce.city.City;
import br.sce.cycle.Cycle;
import br.sce.visit.Visit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Sample {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private String numAmostra;
    
    @ManyToOne
    private Visit visit;
    
    private String deposito;
    
    private int numLavas;
    
    @ManyToOne
    private City city;
    
    @ManyToOne
    private Cycle cycle;
    
    @Column(columnDefinition = "int default 2")
    private int confirmada;

    public int getConfirmada() {
        return confirmada;
    }

    public void setConfirmada(int confirmada) {
        this.confirmada = confirmada;
    }

    
    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumAmostra() {
        return numAmostra;
    }

    public void setNumAmostra(String numAmostra) {
        this.numAmostra = numAmostra;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public int getNumLavas() {
        return numLavas;
    }

    public void setNumLavas(int numLavas) {
        this.numLavas = numLavas;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
    
    
}
