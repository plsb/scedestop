/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.dao;

import br.endemics.model.Sample;
import br.endemics.model.Cycle;
import br.endemics.util.GenericDAO;
import br.endemics.util.HibernateUtil;
import br.endemics.model.Visit;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pedro Saraiva
 */
public class SampleDAO extends GenericDAO<Sample>{

    public SampleDAO() {
        super(Sample.class);
    }
    
    public List<Sample> list(Visit visit) {
        List<Sample> lista = null;
        try {
            
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Sample.class).
                    add(Restrictions.eq("visit", visit)).
                    addOrder(Order.asc("numAmostra")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            System.out.println("Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }

    public List<Sample> list(Cycle cycle) {
        List<Sample> lista = null;
        try {
            VisitDAO vDAo = new VisitDAO();
            List<Visit> lVisit = vDAo.list(cycle);
            
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Sample.class).
                    add(Restrictions.in("visit", lVisit)).
                    addOrder(Order.desc("id")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            System.out.println("Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
        
    }
    
}
