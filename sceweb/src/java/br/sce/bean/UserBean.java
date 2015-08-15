/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.bean;

import br.sce.city.City;
import br.sce.city.CityDAO;
import br.sce.user.User;
import br.sce.user.UserDAO;
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
@ManagedBean(name = "buser")
@RequestScoped
public class UserBean {

    private User user = new User();
    private String confirmPassword;
    private List<User> list;
    private List<SelectItem> citySelect;

    public List<SelectItem> getCitySelect() {
        if (this.citySelect == null) {
            this.citySelect = new ArrayList<SelectItem>();
            //ContextoBean contextoBean = scs.util.ContextoUtil.getContextoBean();

            CityDAO dCity = new CityDAO();
            List<City> categorias = dCity.list();
            this.showDataSelectCity(this.citySelect, categorias, "");
        }

        return citySelect;
    }
    
    private void showDataSelectCity(List<SelectItem> select, List<City> citys, String prefixo) {

        SelectItem item = null;
        if (citys != null) {
            for (City city : citys) {
                item = new SelectItem(city, city.getDescription()+"-"+city.getState());
                item.setEscape(false);

                select.add(item);

                //this.montaDadosSelect(select, usuario.getNome(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }


    public String assignsPermission(User u, String permission) {
        this.user = u;
        List<String> permissions = this.user.getPermission();
        if (permissions.contains(permission)) {
            permissions.remove(permission);
        } else {
            permissions.add(permission);
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String newUser() {
        this.user = new User();
        this.user.setActive(true);
        return "/admin/user";// "usuario";
    }

    public String edit() {
        this.confirmPassword = this.user.getPassword();
        return "/admin/user";
    }

    public String save() {
        FacesContext context = FacesContext.getCurrentInstance();

        String senha = this.user.getPassword();
        if (!senha.equals(this.confirmPassword)) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "A senha não foi confirmada corretamente", null);
            context.addMessage(null, facesMessage);
            return null;

        }
        UserDAO uDAO = new UserDAO();
        uDAO.add(this.user);

        return "/admin/userlist";// this.destinoSalvar;
    }

    public String delete() {
        UserDAO uDAO = new UserDAO();
        uDAO.remove(this.user);
        this.list = null;
        return null;
    }

    public String active() {
        if (this.user.isActive()) {
            this.user.setActive(false);
        } else {
            this.user.setActive(true);
        }

        UserDAO uDAO = new UserDAO();
        uDAO.add(this.user);
        return null;
    }

    public List<User> getList() {
        if (this.list == null) {
            UserDAO uDAO = new UserDAO();
            this.list = uDAO.list();
        }
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

}
