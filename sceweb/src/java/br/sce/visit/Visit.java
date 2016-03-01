package br.sce.visit;

import br.sce.city.City;
import br.sce.cycle.Cycle;
import br.sce.employee.Employee;
import br.sce.sample.Sample;
import br.sce.street.Street;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Visit {
    
    @Id
    private String id;
    
    @ManyToOne
    private Street street;
    
    private int numero;
    
    @ManyToOne
    private Cycle ciclo;
       
    private String complemento;
    
    private String tipoImovel;
    
    private String tipoVisita;
    
    private String codDoenca;
    
    @Temporal(TemporalType.TIME)
    private Date hora;

    private int pneu, tanque, tambor, barril, tina, pote, filtro,
            quartinha, vaso, matConstrucao, pecaCarro, garrafa,
            lata, depPlastico, poco, cisterna, cacimba, cxDagua,
            recNatural, outros, armadilha, pool, 
            ralo, piscina, depTratadoFocal, detpTratadoPerifocal, depEliminados;
    
    private String tipoAtividade;
    
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @Temporal(TemporalType.DATE)
    private Date dataRecuperada;
    
    private String responsavel;
    
    private String tipoLarvicida;
    
    private String obs;
    
    private double larvicidaGt, larvicidaMl;
    
    private double latitude, longitude;
    
    @ManyToOne
    private City city;
    
    @ManyToOne
    private Employee agente;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double logitude) {
        this.longitude = logitude;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cycle getCiclo() {
        return ciclo;
    }

    public void setCiclo(Cycle ciclo) {
        this.ciclo = ciclo;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public int getPneu() {
        return pneu;
    }

    public void setPneu(int pneu) {
        this.pneu = pneu;
    }

    public int getTanque() {
        return tanque;
    }

    public void setTanque(int tanque) {
        this.tanque = tanque;
    }

    public int getTambor() {
        return tambor;
    }

    public void setTambor(int tambor) {
        this.tambor = tambor;
    }

    public int getBarril() {
        return barril;
    }

    public void setBarril(int barril) {
        this.barril = barril;
    }

    public int getTina() {
        return tina;
    }

    public void setTina(int tina) {
        this.tina = tina;
    }

    public int getPote() {
        return pote;
    }

    public void setPote(int pote) {
        this.pote = pote;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public int getQuartinha() {
        return quartinha;
    }

    public void setQuartinha(int quartinha) {
        this.quartinha = quartinha;
    }

    public int getVaso() {
        return vaso;
    }

    public void setVaso(int vaso) {
        this.vaso = vaso;
    }

    public int getMatConstrucao() {
        return matConstrucao;
    }

    public void setMatConstrucao(int matConstrucao) {
        this.matConstrucao = matConstrucao;
    }

    public int getPecaCarro() {
        return pecaCarro;
    }

    public void setPecaCarro(int pecaCarro) {
        this.pecaCarro = pecaCarro;
    }

    public int getGarrafa() {
        return garrafa;
    }

    public void setGarrafa(int garrafa) {
        this.garrafa = garrafa;
    }

    public int getLata() {
        return lata;
    }

    public void setLata(int lata) {
        this.lata = lata;
    }

    public int getDepPlastico() {
        return depPlastico;
    }

    public void setDepPlastico(int depPlastico) {
        this.depPlastico = depPlastico;
    }

    public int getPoco() {
        return poco;
    }

    public void setPoco(int poco) {
        this.poco = poco;
    }

    public int getCisterna() {
        return cisterna;
    }

    public void setCisterna(int cisterna) {
        this.cisterna = cisterna;
    }

    public int getCacimba() {
        return cacimba;
    }

    public void setCacimba(int cacimba) {
        this.cacimba = cacimba;
    }

    public int getCxDagua() {
        return cxDagua;
    }

    public void setCxDagua(int cxDagua) {
        this.cxDagua = cxDagua;
    }

    public int getRecNatural() {
        return recNatural;
    }

    public void setRecNatural(int recNatural) {
        this.recNatural = recNatural;
    }

    public int getOutros() {
        return outros;
    }

    public void setOutros(int outros) {
        this.outros = outros;
    }

    public int getArmadilha() {
        return armadilha;
    }

    public void setArmadilha(int armadilha) {
        this.armadilha = armadilha;
    }

    public int getPool() {
        return pool;
    }

    public void setPool(int pool) {
        this.pool = pool;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public int getRalo() {
        return ralo;
    }

    public void setRalo(int ralo) {
        this.ralo = ralo;
    }

    public int getPiscina() {
        return piscina;
    }

    public void setPiscina(int piscina) {
        this.piscina = piscina;
    }

    public int getDepTratadoFocal() {
        return depTratadoFocal;
    }

    public void setDepTratadoFocal(int depTratadoFocal) {
        this.depTratadoFocal = depTratadoFocal;
    }

    public int getDetpTratadoPerifocal() {
        return detpTratadoPerifocal;
    }

    public void setDetpTratadoPerifocal(int detpTratadoPerifocal) {
        this.detpTratadoPerifocal = detpTratadoPerifocal;
    }

    public int getDepEliminados() {
        return depEliminados;
    }

    public void setDepEliminados(int depEliminados) {
        this.depEliminados = depEliminados;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTipoLarvicida() {
        return tipoLarvicida;
    }

    public void setTipoLarvicida(String tipoLarvicida) {
        this.tipoLarvicida = tipoLarvicida;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public double getLarvicidaGt() {
        return larvicidaGt;
    }

    public void setLarvicidaGt(double larvicidaGt) {
        this.larvicidaGt = larvicidaGt;
    }

    public double getLarvicidaMl() {
        return larvicidaMl;
    }

    public void setLarvicidaMl(double larvicidaMl) {
        this.larvicidaMl = larvicidaMl;
    }

    public Employee getAgente() {
        return agente;
    }

    public void setAgente(Employee agente) {
        this.agente = agente;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getCodDoenca() {
        return codDoenca;
    }

    public void setCodDoenca(String codDoenca) {
        this.codDoenca = codDoenca;
    }

    public Date getDataRecuperada() {
        return dataRecuperada;
    }

    public void setDataRecuperada(Date dataRecuperada) {
        this.dataRecuperada = dataRecuperada;
    }
    
}
