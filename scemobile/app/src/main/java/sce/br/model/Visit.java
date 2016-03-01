package sce.br.model;

/**
 * Created by Pedro Saraiva on 05/10/2015.
 */
public class Visit {
    private String codigo;
    private String tipoVisita;
    private String tipoImovel;
    private String tipoAtividade;
    private int idrua;
    private String nomerua;
    private int idciclo;
    private int idagente;
    private String numeroResidencia;
    private String complemento;
    private String doenca;
    private String responsavel;
    private String hora, data, dataRecuperada;
    private double latitude, longitude;

    private int Tpneu, Ttanque, Ttambor, Tbarril, Ttina, Tpote, Tfiltro,
    Tquartinha, Tvaso, TmatConst, TpecaCarro, Tgarrafa, Tlata,
    TdepPlast, Tpoco, Tcisterna, TcxDagua, Tcacimba,TrecNatural,Tarmadilha,TPOOL,Toutros,TRalo, TPiscina;

    private String depEliminados,larvicidaGT, larvicidaML,
            depTratadosFocal, depTratadosPerifocal,edtObs, tipoLarvicida;

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public int getIdrua() {
        return idrua;
    }

    public void setIdrua(int idrua) {
        this.idrua = idrua;
    }

    public int getIdciclo() {
        return idciclo;
    }

    public void setIdciclo(int idciclo) {
        this.idciclo = idciclo;
    }

    public String getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(String numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getDoenca() {
        return doenca;
    }

    public void setDoenca(String doenca) {
        this.doenca = doenca;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getTpneu() {
        return Tpneu;
    }

    public void setTpneu(int tpneu) {
        Tpneu = tpneu;
    }

    public int getTtanque() {
        return Ttanque;
    }

    public void setTtanque(int ttanque) {
        Ttanque = ttanque;
    }

    public int getTtambor() {
        return Ttambor;
    }

    public void setTtambor(int ttambor) {
        Ttambor = ttambor;
    }

    public int getTbarril() {
        return Tbarril;
    }

    public void setTbarril(int tbarril) {
        Tbarril = tbarril;
    }

    public int getTtina() {
        return Ttina;
    }

    public void setTtina(int ttina) {
        Ttina = ttina;
    }

    public int getTpote() {
        return Tpote;
    }

    public void setTpote(int tpote) {
        Tpote = tpote;
    }

    public int getTfiltro() {
        return Tfiltro;
    }

    public void setTfiltro(int tfiltro) {
        Tfiltro = tfiltro;
    }

    public int getTquartinha() {
        return Tquartinha;
    }

    public void setTquartinha(int tquartinha) {
        Tquartinha = tquartinha;
    }

    public int getTvaso() {
        return Tvaso;
    }

    public void setTvaso(int tvaso) {
        Tvaso = tvaso;
    }

    public int getTmatConst() {
        return TmatConst;
    }

    public void setTmatConst(int tmatConst) {
        TmatConst = tmatConst;
    }

    public int getTpecaCarro() {
        return TpecaCarro;
    }

    public void setTpecaCarro(int tpecaCarro) {
        TpecaCarro = tpecaCarro;
    }

    public int getTgarrafa() {
        return Tgarrafa;
    }

    public void setTgarrafa(int tgarrafa) {
        Tgarrafa = tgarrafa;
    }

    public int getTlata() {
        return Tlata;
    }

    public void setTlata(int tlata) {
        Tlata = tlata;
    }

    public int getTdepPlast() {
        return TdepPlast;
    }

    public void setTdepPlast(int tdepPlast) {
        TdepPlast = tdepPlast;
    }

    public int getTpoco() {
        return Tpoco;
    }

    public void setTpoco(int tpoco) {
        Tpoco = tpoco;
    }

    public int getTcisterna() {
        return Tcisterna;
    }

    public void setTcisterna(int tcisterna) {
        Tcisterna = tcisterna;
    }

    public int getTcxDagua() {
        return TcxDagua;
    }

    public void setTcxDagua(int tcxDagua) {
        TcxDagua = tcxDagua;
    }

    public int getTcacimba() {
        return Tcacimba;
    }

    public void setTcacimba(int tcacimba) {
        Tcacimba = tcacimba;
    }

    public int getTrecNatural() {
        return TrecNatural;
    }

    public void setTrecNatural(int trecNatural) {
        TrecNatural = trecNatural;
    }

    public int getTarmadilha() {
        return Tarmadilha;
    }

    public void setTarmadilha(int tarmadilha) {
        Tarmadilha = tarmadilha;
    }

    public int getTPOOL() {
        return TPOOL;
    }

    public void setTPOOL(int TPOOL) {
        this.TPOOL = TPOOL;
    }

    public int getToutros() {
        return Toutros;
    }

    public void setToutros(int toutros) {
        Toutros = toutros;
    }

    public int getTRalo() {
        return TRalo;
    }

    public void setTRalo(int TRalo) {
        this.TRalo = TRalo;
    }

    public int getTPiscina() {
        return TPiscina;
    }

    public void setTPiscina(int TPiscina) {
        this.TPiscina = TPiscina;
    }

    public String getDepEliminados() {
        return depEliminados;
    }

    public void setDepEliminados(String depEliminados) {
        this.depEliminados = depEliminados;
    }

    public String getLarvicidaGT() {
        return larvicidaGT;
    }

    public void setLarvicidaGT(String larvicidaGT) {
        this.larvicidaGT = larvicidaGT;
    }

    public String getLarvicidaML() {
        return larvicidaML;
    }

    public void setLarvicidaML(String larvicidaML) {
        this.larvicidaML = larvicidaML;
    }

    public String getDepTratadosFocal() {
        return depTratadosFocal;
    }

    public void setDepTratadosFocal(String depTratadosFocal) {
        this.depTratadosFocal = depTratadosFocal;
    }

    public String getDepTratadosPerifocal() {
        return depTratadosPerifocal;
    }

    public void setDepTratadosPerifocal(String depTratadosPerifocal) {
        this.depTratadosPerifocal = depTratadosPerifocal;
    }

    public String getEdtObs() {
        return edtObs;
    }

    public void setEdtObs(String edtObs) {
        this.edtObs = edtObs;
    }

    public String getTipoLarvicida() {
        return tipoLarvicida;
    }

    public void setTipoLarvicida(String tipoLarvicida) {
        this.tipoLarvicida = tipoLarvicida;
    }

    public int getIdagente() {
        return idagente;
    }

    public void setIdagente(int idagente) {
        this.idagente = idagente;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDataRecuperada() {
        return dataRecuperada;
    }

    public void setDataRecuperada(String dataRecuperada) {
        this.dataRecuperada = dataRecuperada;
    }

    public String getNomerua() {
        return nomerua;
    }

    public void setNomerua(String nomerua) {
        this.nomerua = nomerua;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
