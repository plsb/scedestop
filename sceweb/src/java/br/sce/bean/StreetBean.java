/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import br.sce.district.District;
import br.sce.district.DistrictDAO;
import br.sce.street.Street;
import br.sce.street.StreetDAO;
import br.sce.util.UsuarioAtivo;
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
@ManagedBean(name = "bstreet")
@RequestScoped
public class StreetBean {

    private Street street = new Street();
    private List<Street> list;
    private StreetDAO dQuarter = new StreetDAO();
    private FacesContext context = FacesContext.getCurrentInstance();

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    

        
    public String save() { 
        if(street.getDescription()==null || street.getDistrict()==null || street.getIdQuarter()==0){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Preencha os campos Obrigatórios (*)", ""));
            return "";
        }
        
        street.setCity(UsuarioAtivo.getUser().getCity());
        street.setDescription(street.getDescription().toUpperCase());
        if(street.getIdQuarter()==0){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Código do Quarteirão Não Pode Ser 0", ""));
            return "";
        }
        
        if (street.getId() == 0) {
            dQuarter.add(street);
            context.addMessage(null, new FacesMessage("Sucesso a Adicionar: "
                    + street.getDescription(), ""));
            
            
        } else {
            dQuarter.update(street);
            context.addMessage(null, new FacesMessage("Sucesso a Atualizar: "
                    + street.getDescription(), ""));
            
        }

        return "/limited/streetlist.jsf";
    }

    public String newQuarter() {
        street = new Street();
        return "/limited/street.jsf";
    }

    public List<Street> getList() {
        return dQuarter.list();
    }

    public String edit() {
        return "/limited/street.jsf";
    }

    public String remove() {
        dQuarter.remove(this.street);
        context.addMessage(null, new FacesMessage("Sucesso ao Excluir: "
                + street.getDescription(), ""));
        
        this.list = dQuarter.list();
        return null;
    }

            private List<SelectItem> districtSelect;
    
    public List<SelectItem> getDistrictSelect() {
        if (this.districtSelect == null) {
            this.districtSelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            DistrictDAO dDistrict = new DistrictDAO();
            List<District> categorias = dDistrict.list();
            this.showDataSelectArea(this.districtSelect, categorias, "");
        }

        return districtSelect;
    }

    private void showDataSelectArea(List<SelectItem> select, List<District> districts, String prefixo) {

        SelectItem item = null;
        if (districts != null) {
            for (District d : districts) {
                item = new SelectItem(d, d.getDescription());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

}
