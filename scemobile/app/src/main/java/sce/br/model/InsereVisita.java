package sce.br.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import sce.br.dao.Database;

/**
 * Created by Pedro Saraiva on 08/10/2015.
 */
public class InsereVisita {


    public long insereVisita(Visit visit, Context ctx, int operacao){
        Database db = new Database(ctx);
        db.open();

        try {
            ContentValues cvVisit = new ContentValues();
            cvVisit.put("_ID", visit.getCodigo());
            cvVisit.put("VIS_IDRUA", visit.getIdrua());
            cvVisit.put("VIS_NOMERUA", visit.getNomerua());
            cvVisit.put("VIS_NUMERO", Integer.parseInt(visit.getNumeroResidencia()));
            cvVisit.put("VIS_COMPLEMENTO", visit.getComplemento());
            cvVisit.put("VIS_TIPO_IMOVEL", visit.getTipoImovel());
            cvVisit.put("VIS_TIPO_VISITA", visit.getTipoVisita());
            cvVisit.put("VIS_HORA", visit.getHora());
            cvVisit.put("VIS_PNEU", visit.getTpneu());
            cvVisit.put("VIS_TANQUE", visit.getTtanque());
            cvVisit.put("VIS_TAMBOR", visit.getTtambor());
            cvVisit.put("VIS_BARRIL", visit.getTbarril());
            cvVisit.put("VIS_TINA", visit.getTtina());
            cvVisit.put("VIS_POTE", visit.getTpote());
            cvVisit.put("VIS_FILTRO", visit.getTfiltro());
            cvVisit.put("VIS_QUARTINHA", visit.getTquartinha());
            cvVisit.put("VIS_VASO", visit.getTvaso());
            cvVisit.put("VIS_MAT_CONSTRUCAO", visit.getTmatConst());
            cvVisit.put("VIS_PECA_CARRO", visit.getTpecaCarro());
            cvVisit.put("VIS_GARRAFA", visit.getTgarrafa());
            cvVisit.put("VIS_LATA", visit.getTlata());
            cvVisit.put("VIS_DEP_PLASTICO", visit.getTdepPlast());
            cvVisit.put("VIS_POCO", visit.getTpoco());
            cvVisit.put("VIS_CISTERNA", visit.getTcisterna());
            cvVisit.put("VIS_CACIMBA", visit.getTcacimba());
            cvVisit.put("VIS_CX_DAGUA", visit.getTcxDagua());
            cvVisit.put("VIS_REC_NATURAL", visit.getTrecNatural());
            cvVisit.put("VIS_OUTROS", visit.getToutros());
            cvVisit.put("VIS_ARMADILHA", visit.getTarmadilha());
            cvVisit.put("VIS_POOL", visit.getTPOOL());
            cvVisit.put("VIS_TIPO_ATIVIDADE", visit.getTipoAtividade());
            cvVisit.put("VIS_COD_DOENCA", visit.getDoenca());
            cvVisit.put("VIS_RESPONSAVEL", visit.getResponsavel());
            cvVisit.put("VIS_LARVICIDAGT", visit.getLarvicidaGT());
            cvVisit.put("VIS_LARVICIDAML", visit.getLarvicidaML());
            cvVisit.put("VIS_DATA", visit.getData());
            cvVisit.put("VIS_DATA_RECUPERADA", visit.getDataRecuperada());
            cvVisit.put("VIS_AGENTE", visit.getIdagente());
            cvVisit.put("VIS_DEP_TRATADOS_FOCAL", visit.getDepTratadosFocal());
            cvVisit.put("VIS_DEP_TRATADOS_PERIFOCAL", visit.getDepTratadosPerifocal());
            cvVisit.put("VIS_TIPO_LARVICIDA", visit.getTipoLarvicida());
            cvVisit.put("VIS_RALO", visit.getTRalo());
            cvVisit.put("VIS_PISCINA", visit.getTPiscina());
            cvVisit.put("VIS_OBS", visit.getEdtObs());
            cvVisit.put("VIS_DEP_ELIMINADOS", visit.getDepEliminados());
            cvVisit.put("CICLO", visit.getIdciclo());
            cvVisit.put("VIS_LATITUDE", String.valueOf(visit.getLatitude()));
            cvVisit.put("VIS_LONGITUDE", String.valueOf(visit.getLongitude()));
            cvVisit.put("FOIIMPORTADO", 0);
            long d;
            if(operacao==0){
                d = db.insert("visit", cvVisit);
            } else {
                d = db.update("visit", cvVisit, "_ID="+visit.getCodigo());
            }

            return d;

        } catch (Exception e){
            Toast.makeText(ctx, "Erro ao Inserir Visita", Toast.LENGTH_SHORT).show();
            Log.i("erroinsere", e.getMessage());
            return -1;
        }
    }
}
