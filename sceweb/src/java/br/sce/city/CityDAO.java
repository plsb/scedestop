/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.city;

import br.sce.util.GenericDAO;

/**
 *
 * @author Pedro Saraiva
 */
public class CityDAO extends GenericDAO<City>{

    public CityDAO() {
        super(City.class);
    }
    
}
