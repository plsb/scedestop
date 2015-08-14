/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bmap")
@RequestScoped
public class MapBean {
    
    private MapModel emptyModel;
    
    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }
    
    public MapModel getEmptyModel() {
        return emptyModel;
    }
    
}
