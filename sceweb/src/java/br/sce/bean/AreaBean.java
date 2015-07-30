/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import br.sce.area.Area;
import br.sce.area.AreaDAO;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Pedro Saraiva
 */
@ManagedBean(name = "barea")
@RequestScoped
public class AreaBean {

    private Area area = new Area();
    private List<Area> list;
    private AreaDAO dArea = new AreaDAO();
    private FacesContext context = FacesContext.getCurrentInstance();

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String save() {
        area.setDescription(area.getDescription().toUpperCase());
        if (area.getId() == 0) {
            context.addMessage(null, new FacesMessage("Sucesso a Adicionar: "
                    + area.getDescription(), ""));
            
            dArea.add(area);
        } else {
            context.addMessage(null, new FacesMessage("Sucesso a Atualizar: "
                    + area.getDescription(), ""));
            dArea.update(area);
        }

        return "/faces/limited/arealist.xhtml";
    }

    public String newArea() {
        area = new Area();
        return "/faces/limited/area.xhtml";
    }

    public List<Area> getList() {
        return dArea.list();
    }

    public String edit() {
        return "/faces/limited/area.xhtml";
    }

    public String remove() {
        context.addMessage(null, new FacesMessage("Sucesso ao Excluir: "
                + area.getDescription(), ""));

        dArea.remove(this.area);
        this.list = dArea.list();
        return null;
    }
}
