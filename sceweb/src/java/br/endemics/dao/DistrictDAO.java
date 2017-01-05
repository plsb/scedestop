/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.dao;

import br.endemics.model.District;
import br.endemics.model.Area;
import br.endemics.model.Cycle;
import br.endemics.util.GenericDAO;
import br.endemics.util.HibernateUtil;
import br.endemics.util.UsuarioAtivo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import br.endemics.bean.ContextBean;

/**
 *
 * @author Pedro Saraiva
 */
public class DistrictDAO extends GenericDAO<District>{

    public DistrictDAO() {
        super(District.class);
    }
    
    public List<District> list() {
        List<District> lista = null;
        try {
            this.setSessao(HibernateUtil.getSessionFactory().openSession());
            setTransacao(getSessao().beginTransaction());
            lista = this.getSessao().createCriteria(District.class).
                    add(Restrictions.eq("city", UsuarioAtivo.getUser().getCity())).
                    addOrder(Order.asc("description")).list();
            
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
