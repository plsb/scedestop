/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.street;

import br.sce.util.GenericDAO;

/**
 *
 * @author Pedro Saraiva
 */
public class StreetDAO extends GenericDAO<Street>{

    public StreetDAO() {
        super(Street.class);
    }
    
}
