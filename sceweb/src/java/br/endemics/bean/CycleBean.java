/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.bean;

import br.endemics.model.Area;
import br.endemics.dao.AreaDAO;
import br.endemics.model.Cycle;
import br.endemics.dao.CycleDAO;
import br.endemics.model.District;
import br.endemics.dao.DistrictDAO;
import br.endemics.util.UsuarioAtivo;
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
        if (cycle.getDescription()==null || cycle.getEndDate()==null || cycle.getStartDate()==null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Preencha os campos Obrigatórios (*)", ""));
            return "";
        }

        cycle.setDescription(cycle.getDescription().toUpperCase());
        cycle.setCity(UsuarioAtivo.getUser().getCity());
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
