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
import br.sce.sample.Sample;
import br.sce.sample.SampleDAO;
import br.sce.street.Street;
import br.sce.street.StreetDAO;
import br.sce.visit.Visit;
import br.sce.visit.VisitDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static String idVerification = "@endemics04_p_09M08k";

    @WebMethod
    public List<Cycle> listCycle(int idCity, @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            CityDAO cDAO = new CityDAO();
            City c = cDAO.checkExists("id", idCity).get(0);

            CycleDAO cyDAO = new CycleDAO();

            return cyDAO.listCyclesByCity(c, new Date());
        }

        return null;
    }

    @WebMethod
    public List<Street> listStreet(int idCity, @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            CityDAO cDAO = new CityDAO();
            City c = cDAO.checkExists("id", idCity).get(0);

            StreetDAO sDAO = new StreetDAO();
            return sDAO.checkExists("city", c);
        }
        return null;
    }

    @WebMethod
    public List<Employee> listEmployee(int idCity, @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            CityDAO cDAO = new CityDAO();
            City c = cDAO.checkExists("id", idCity).get(0);

            EmployeeDAO eDAO = new EmployeeDAO();
            return eDAO.listActivesByCity(c);
        }
        return null;
    }

    @WebMethod
    public List<City> listCity(int idIBGE, @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            CityDAO cDAO = new CityDAO();
            return cDAO.listActivesByCity(idIBGE);
        }
        return null;
    }

    @WebMethod
    public List<Visit> listImoFechados(int idCycle, @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            CycleDAO cDAO = new CycleDAO();
            Cycle c = cDAO.checkExists("id", idCycle).get(0);

            VisitDAO vDAO = new VisitDAO();
            return vDAO.listImoFechados(c);
        }
        return null;
    }

    @WebMethod
    public Boolean insertSample(@WebParam(name = "idvisita") String idvisita,
            @WebParam(name = "deposito") String deposito,
            @WebParam(name = "numLavas") int numLavas,
            @WebParam(name = "numAmostras") String numAmostra,
            @WebParam(name = "city") int idcity,
            @WebParam(name = "cycle") int idcycle,
            @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            SampleDAO sDAO = new SampleDAO();
            Sample s = new Sample();
            s.setDeposito(deposito);
            s.setConfirmada(2);
            s.setNumAmostra(numAmostra);
            s.setNumLavas(numLavas);
            try {
                CityDAO cDAO = new CityDAO();
                City c = cDAO.checkExists("id", idcity).get(0);
                if (c != null) {
                    s.setCity(c);
                }
            } catch (Exception e) {

            }
            try {
                CycleDAO cDAO = new CycleDAO();
                Cycle c = cDAO.checkExists("id", idcycle).get(0);
                if (c != null) {
                    s.setCycle(c);
                }
            } catch (Exception e) {

            }
            Visit v = null;
            try {
                VisitDAO vDAO = new VisitDAO();
                v = vDAO.checkExists("id", idvisita).get(0);

            } catch (Exception e) {

            }

            if (v != null) {
                s.setVisit(v);
                if (sDAO.add(s)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
        return false;
    }

    @WebMethod
    public Boolean insertVisit(@WebParam(name = "id") String id, @WebParam(name = "idrua") int idrua, @WebParam(name = "numeroResidencia") int numResidencia,
            @WebParam(name = "idciclo") int idciclo, @WebParam(name = "complemento") String complemento, @WebParam(name = "tipoImovel") String tipoImovel,
            @WebParam(name = "tipoVisita") String tipoVisita, @WebParam(name = "codDoenca") String codDoenca, @WebParam(name = "hora") String hora,
            @WebParam(name = "pneu") int pneu, @WebParam(name = "tanque") int tanque, @WebParam(name = "tambor") int tambor,
            @WebParam(name = "barril") int barril, @WebParam(name = "tina") int tina, @WebParam(name = "pote") int pote,
            @WebParam(name = "filtro") int filtro, @WebParam(name = "quartinha") int quartinha, @WebParam(name = "vaso") int vaso,
            @WebParam(name = "matConstrucao") int matConstrucao, @WebParam(name = "pecaCarro") int pecaCarro,
            @WebParam(name = "garrafa") int garrafa, @WebParam(name = "lata") int lata, @WebParam(name = "depPlastico") int depPlastico,
            @WebParam(name = "poco") int poco, @WebParam(name = "cisterna") int cisterna, @WebParam(name = "cacimba") int cacimba,
            @WebParam(name = "cxDagua") int cxDagua, @WebParam(name = "recNatural") int recNatural, @WebParam(name = "outros") int outros,
            @WebParam(name = "armadilha") int armadilha, @WebParam(name = "pool") int pool, @WebParam(name = "ralo") int ralo,
            @WebParam(name = "piscina") int piscina, @WebParam(name = "depTratadoFocal") int depTratadoFocal,
            @WebParam(name = "detpTratadoPerifocal") int detpTratadoPerifocal, @WebParam(name = "depEliminados") int depEliminados,
            @WebParam(name = "tipoAtividade") String tipoAtividade, @WebParam(name = "data") String data,
            @WebParam(name = "dataRecuperada") String dataRecuperada,
            @WebParam(name = "responsavel") String responsavel, @WebParam(name = "tipoLarvicida") String tipoLarvicida,
            @WebParam(name = "obs") String obs, @WebParam(name = "larvicidaGt") String larvicidaGt,
            @WebParam(name = "larvicidaMl") String larvicidaMl, @WebParam(name = "idcidade") int idcidade,
            @WebParam(name = "idagente") int idagente,
            @WebParam(name = "latitude") double latitude,
            @WebParam(name = "longitude") double longitude,
            @WebParam(name = "codVerificacao") String codVerificacao) {
        if (codVerificacao.equals(idVerification)) {
            VisitDAO vDAO = new VisitDAO();
            Visit v = new Visit();
            v.setId(id);

            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date date = new java.sql.Date(format.parse(data).getTime());
                v.setData(date);
            } catch (Exception e) {

            }

            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date dateRecup = new java.sql.Date(format.parse(dataRecuperada).getTime());
                v.setDataRecuperada(dateRecup);
            } catch (Exception e) {

            }

            try {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                java.sql.Date hour = new java.sql.Date(format.parse(hora).getTime());
                v.setHora(hour);
            } catch (Exception e) {

            }
            try {
                CycleDAO cycDAO = new CycleDAO();
                Cycle cycle = cycDAO.checkExists("id", idciclo).get(0);
                if (cycle != null) {
                    v.setCiclo(cycle);
                }
            } catch (Exception e) {

            }

            try {
                EmployeeDAO empDAO = new EmployeeDAO();
                Employee emp = empDAO.checkExists("id", idagente).get(0);
                if (emp != null) {
                    v.setAgente(emp);
                }
            } catch (Exception e) {

            }

            v.setNumero(numResidencia);
            v.setComplemento(complemento);
            v.setTipoImovel(tipoImovel);
            v.setTipoVisita(tipoVisita);
            v.setCodDoenca(codDoenca);
            v.setPneu(pneu);
            v.setTanque(tanque);
            v.setTambor(tambor);
            v.setBarril(barril);
            v.setTina(tina);
            v.setPote(pote);
            v.setFiltro(filtro);
            v.setQuartinha(quartinha);
            v.setVaso(vaso);
            v.setMatConstrucao(matConstrucao);
            v.setPecaCarro(pecaCarro);
            v.setGarrafa(garrafa);
            v.setLata(lata);
            v.setDepPlastico(depPlastico);
            v.setPoco(poco);
            v.setCisterna(cisterna);
            v.setCacimba(cacimba);
            v.setCxDagua(cxDagua);
            v.setRecNatural(recNatural);
            v.setRecNatural(recNatural);
            v.setOutros(outros);
            v.setArmadilha(armadilha);
            v.setPool(pool);
            v.setRalo(ralo);
            v.setPiscina(piscina);
            v.setDepTratadoFocal(depTratadoFocal);
            v.setDetpTratadoPerifocal(detpTratadoPerifocal);
            v.setDepEliminados(depEliminados);
            v.setTipoAtividade(tipoAtividade);
            v.setResponsavel(responsavel);
            v.setTipoLarvicida(tipoLarvicida);
            v.setObs(obs);
            try {
                v.setLarvicidaMl(Double.parseDouble(larvicidaMl));
            } catch (Exception e) {
                v.setLarvicidaMl(0);
            }
            try {
                v.setLatitude(Double.parseDouble(String.valueOf(latitude)));
            } catch (Exception e) {
                v.setLarvicidaMl(0);
            }
            try {
                v.setLongitude(Double.parseDouble(String.valueOf(longitude)));
            } catch (Exception e) {
                v.setLarvicidaMl(0);
            }
            try {
                v.setLarvicidaGt(Double.parseDouble(obs));
            } catch (Exception e) {
                v.setLarvicidaGt(0);
            }
            try {
                CityDAO cDAO = new CityDAO();
                City c = cDAO.checkExists("id", idcidade).get(0);
                if (c != null) {
                    v.setCity(c);
                }
            } catch (Exception e) {

            }

            try {
                StreetDAO sDAO = new StreetDAO();
                Street street = sDAO.checkExists("id", idrua).get(0);
                if (street != null) {
                    v.setStreet(street);
                }
            } catch (Exception e) {

            }

            List<Visit> visitCadastrada = vDAO.checkExists("id", v.getId());
            if (visitCadastrada.size()==0) {
                if (vDAO.add(v)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (vDAO.update(v)) {
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;
    }

}
