/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import br.sce.area.Area;
import br.sce.area.AreaDAO;
import br.sce.cycle.Cycle;
import br.sce.cycle.CycleDAO;
import br.sce.district.District;
import br.sce.district.DistrictDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "bcycle")
@RequestScoped
public class CycleBean {

    private Cycle cycle = new Cycle();
    private CycleDAO dCycle = new CycleDAO();
    private FacesContext context = FacesContext.getCurrentInstance();
    private List<Cycle> list;
    
    public List<Cycle> getList() {
        return dCycle.list();
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    

    public String save() {
        cycle.setDescription(cycle.getDescription().toUpperCase());
        
        if (cycle.getId() == 0) {
             dCycle.add(cycle);
            context.addMessage(null, new FacesMessage("Sucesso a Adicionar: "
                    + cycle.getDescription(), ""));

           
        } else {
            dCycle.update(cycle);
            context.addMessage(null, new FacesMessage("Sucesso a Atualizar: "
                    + cycle.getDescription(), ""));
            
        }

        return "/limited/cyclelist.jsf";
    }

    public String newCycle() {
        cycle = new Cycle();
        return "/limited/cycle.jsf";
    }

    public String edit() {
        return "/limited/cycle.jsf";
    }

    public String remove() {
        context.addMessage(null, new FacesMessage("Sucesso ao Excluir: "
                + cycle.getDescription(), ""));

        dCycle.remove(this.cycle);
        this.list = dCycle.list();
        return null;
    }

}
