/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.ws;


import br.sce.area.Area;
import br.sce.area.AreaDAO;
import br.sce.cycle.Cycle;
import br.sce.cycle.CycleDAO;
import br.sce.district.District;
import br.sce.district.DistrictDAO;
import br.sce.employee.Employee;
import br.sce.employee.EmployeeDAO;
import br.sce.street.Street;
import br.sce.street.StreetDAO;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author 'Pedro
 */
@WebService
public class SCEWS {

    @WebMethod
    public List<Area> listAreas(){
        AreaDAO aDAO = new AreaDAO();
        return aDAO.list();
    } 
    
    @WebMethod
    public List<District> listDistrict(){
        DistrictDAO dDAO = new DistrictDAO();
        return dDAO.list();
    }
    
    @WebMethod
    public List<Cycle> listCycle(){
        CycleDAO cDAO = new CycleDAO();
        return cDAO.list();
    }

    @WebMethod
    public List<Street> listStreet(){
        StreetDAO sDAO = new StreetDAO();
        return sDAO.list();
    }
    
    @WebMethod
    public List<Employee> listEmployee(){
        EmployeeDAO eDAO = new EmployeeDAO();
        return eDAO.list();
    }
        
}
