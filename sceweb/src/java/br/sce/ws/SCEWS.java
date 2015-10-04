/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sce.ws;


import br.sce.area.Area;
import br.sce.area.AreaDAO;
import br.sce.city.City;
import br.sce.city.CityDAO;
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
    public List<Area> listAreas(int idCity){
        CityDAO cDAO = new CityDAO();
        City c = cDAO.checkExists("id", idCity).get(0);
        
        AreaDAO aDAO = new AreaDAO();
        return aDAO.checkExists("city", c);
    } 
    
    @WebMethod
    public List<District> listDistrict(int idCity){
        CityDAO cDAO = new CityDAO();
        City c = cDAO.checkExists("id", idCity).get(0);
        
        DistrictDAO dDAO = new DistrictDAO();
        return dDAO.checkExists("city", c);
    }
    
    @WebMethod
    public List<Cycle> listCycle(int idCity){
        CityDAO cDAO = new CityDAO();
        City c = cDAO.checkExists("id", idCity).get(0);
        
        CycleDAO cyDAO = new CycleDAO();
        return cyDAO.checkExists("city", c);
    }

    @WebMethod
    public List<Street> listStreet(int idCity){
        CityDAO cDAO = new CityDAO();
        City c = cDAO.checkExists("id", idCity).get(0);
        
        StreetDAO sDAO = new StreetDAO();
        return sDAO.checkExists("city", c);
    }
    
    @WebMethod
    public List<Employee> listEmployee(int idCity){
        CityDAO cDAO = new CityDAO();
        City c = cDAO.checkExists("id", idCity).get(0);
        
        EmployeeDAO eDAO = new EmployeeDAO();
        return eDAO.checkExists("cityRegister", c);
    }
    
    @WebMethod
    public List<City> listCity(int idIBGE){
        CityDAO cDAO = new CityDAO();
        return cDAO.checkExists("idIBGE", idIBGE);
    }
        
}
