/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.endemics.bean;

import br.endemics.model.Area;
import br.endemics.dao.AreaDAO;
import br.endemics.util.UsuarioAtivo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

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
    ;
    private String descricaoPesquisa;

    public AreaDAO getdArea() {
        return dArea;
    }

    public void setdArea(AreaDAO dArea) {
        this.dArea = dArea;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public String getDescricaoPesquisa() {
        return descricaoPesquisa;
    }

    public void setDescricaoPesquisa(String descricaoPesquisa) {
        this.descricaoPesquisa = descricaoPesquisa;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String save() {
        if (area.getDescription().equals("")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Preencha os campos Obrigatórios (*)", ""));
            return "";
        }
        area.setDescription(area.getDescription().toUpperCase());
        area.setCity(UsuarioAtivo.getUser().getCity());
        if (area.getId() == 0) {
            dArea.add(area);

        } else {
            dArea.update(area);

        }

        return "/limited/arealist.jsf";
    }

    public String newArea() {
        area = new Area();
        return "/limited/area.jsf";
    }

    public List<Area> getList() {
        List<Area> lista;
        if (descricaoPesquisa==null) {
            lista = dArea.list();
        } else {
            lista = dArea.pesquisarArea("description", descricaoPesquisa);
        }
        return lista;
    }

    public String edit() {
        return "/limited/area.jsf";
    }

    public String remove() {
        dArea.remove(this.area);
        this.list = dArea.list();
        return null;
    }
}
