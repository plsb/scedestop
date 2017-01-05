/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.dao;

import br.endemics.model.Visit;
import br.endemics.model.City;
import br.endemics.model.Cycle;
import br.endemics.dao.CycleDAO;
import br.endemics.model.Employee;
import br.endemics.util.GenericDAO;
import br.endemics.util.HibernateUtil;
import br.endemics.util.UsuarioAtivo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Pedro Saraiva
 */
public class VisitDAO extends GenericDAO<Visit>{

    public VisitDAO() {
        super(Visit.class);
    }
     
    public List<Visit> list() {
        List<Visit> lista = null;
        try {
           
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.desc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }
    
    public List<Visit> list(Cycle c) {
        List<Visit> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    add(Restrictions.eq("ciclo", c)).
                    addOrder(Order.desc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }
    
    public List<Visit> listImoFechados(Cycle cycle) {
        List<Visit> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("tipoVisita", "FECHADA")).
                    add(Restrictions.eq("ciclo", cycle)).
                    addOrder(Order.asc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;
    }

    public List<Visit> list(Employee agente) {
                List<Visit> lista = null;
        try {
           
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(Visit.class).
                    add(Restrictions.eq("agente", agente)).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.asc("data")).list();

        } catch (Throwable e) {
            if (getTransacao().isActive()) {
                getTransacao().rollback();
            }
            JOptionPane.showMessageDialog(null, "Não foi possível listar: " + e.getMessage());
        } finally {
            getSessao().close();
        }
        return lista;


    }
    
    
    
    
    
}
