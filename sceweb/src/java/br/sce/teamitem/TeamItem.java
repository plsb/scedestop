/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.teamitem;

import br.sce.employee.Employee;
import br.sce.team.Team;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Pedro Saraiva
 */
@Entity
public class TeamItem implements Serializable{
    
    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    private Team team;
    
    @ManyToOne
    private Employee supervised;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Employee getSupervised() {
        return supervised;
    }

    public void setSupervised(Employee supervised) {
        this.supervised = supervised;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.team);
        hash = 47 * hash + Objects.hashCode(this.supervised);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TeamItem other = (TeamItem) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.team, other.team)) {
            return false;
        }
        if (!Objects.equals(this.supervised, other.supervised)) {
            return false;
        }
        return true;
    }
    
    private static final long serialVersionUID = -7954634921410552097L;
           
}
