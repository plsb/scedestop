package sce.br.model;

/**
 * Created by Pedro Saraiva on 03/10/2015.
 */
public class City {

    private Integer id;

    private int idIBGE;

    private String description;

    private String state;

    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIdIBGE() {
        return idIBGE;
    }

    public void setIdIBGE(int idIBGE) {
        this.idIBGE = idIBGE;
    }
}
