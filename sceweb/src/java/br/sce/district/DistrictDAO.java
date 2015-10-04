/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.district;

import br.sce.area.Area;
import br.sce.cycle.Cycle;
import br.sce.util.GenericDAO;
import br.sce.util.HibernateUtil;
import br.sce.util.UsuarioAtivo;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import scs.web.ContextBean;

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
