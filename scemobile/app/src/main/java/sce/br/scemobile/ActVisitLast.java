package sce.br.scemobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import sce.br.dao.Database;
import sce.br.model.Amostra;
import sce.br.model.Mensagem;
import sce.br.model.Visit;

public class ActVisitLast extends Activity implements View.OnClickListener{

    private static Visit visit;
    private Button btSalvar, btCancelar, btnAmostras;
    private Spinner SpTipoLarvicida;
    private ArrayList<String> TipoLarvicida = new ArrayList<String>();

    private EditText pneu, tanque, tambor, barril, tina, pote, filtro, quartinha, outrosDesc,
            vaso, matConst, pecaCarro, garrafa, lata, depPlast,poco, cisterna,
            cxDagua, cacimba, depEliminados,larvicida, larvicidaML,recNatural,armadilha,POOL,
            depTratadosFocal, depTratadosPerifocal, outros, ralo, piscina, edtObs;

    private int Tpneu, Ttanque, Ttambor, Tbarril, Ttina, Tpote, Tfiltro,
            Tquartinha, Tvaso, TmatConst, TpecaCarro, Tgarrafa, Tlata,
            TdepPlast, Tpoco, Tcisterna, TcxDagua, Tcacimba,TrecNatural,Tarmadilha,TPOOL,Toutros,TRalo, TPiscina, SomaTotal;

    private Database db = new Database(ActVisitLast.this);

    public static void show(Visit v, Context ctx){
        ActVisitLast.visit = v;
        Intent i = new Intent(ctx, ActVisitLast.class);
        ctx.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_visit_last);

        inicizalizaObjetos();
        preencheSpinner();
    }

    public void inicizalizaObjetos(){
        try {
            btSalvar = (Button) findViewById(R.id.btnGravar);
            btSalvar.setOnClickListener(this);
            btCancelar = (Button) findViewById(R.id.btnCancelar);
            btCancelar.setOnClickListener(this);
            SpTipoLarvicida = (Spinner) findViewById(R.id.SpnTipoLarvicida);
            recNatural  = (EditText) findViewById(R.id.EdtRecNaturais);
            armadilha   = (EditText) findViewById(R.id.EdtArmadilha);
            POOL        = (EditText) findViewById(R.id.EdtPOOL);
            outros      = (EditText) findViewById(R.id.EdtOutro);
            pneu        = (EditText) findViewById(R.id.EdtPneu);
            tanque      = (EditText) findViewById(R.id.EdtTanque);
            tambor      = (EditText) findViewById(R.id.EdtTambor);
            barril      = (EditText) findViewById(R.id.EdtBarril);
            tina        = (EditText) findViewById(R.id.EdtTina);
            pote        = (EditText) findViewById(R.id.EdtPote);
            filtro      = (EditText) findViewById(R.id.EdtFiltro);
            quartinha   = (EditText) findViewById(R.id.EdtQuartinha);
            vaso        = (EditText) findViewById(R.id.EdtVaso);
            matConst    = (EditText) findViewById(R.id.EdtMatConstrucao);
            pecaCarro   = (EditText) findViewById(R.id.EdtPecaCarro);
            garrafa     = (EditText) findViewById(R.id.EdtGarrafa);
            lata        = (EditText) findViewById(R.id.EdtLata);
            depPlast    = (EditText) findViewById(R.id.EdtDepPlasticos);
            poco        = (EditText) findViewById(R.id.EdtPoco);
            cisterna    = (EditText) findViewById(R.id.EdtCisterna);
            cacimba     = (EditText) findViewById(R.id.EdtCacimba);
            cxDagua     = (EditText) findViewById(R.id.EdtCxAgua);
            depEliminados = (EditText) findViewById(R.id.EdtDepElimVisi);
            larvicida   = (EditText) findViewById(R.id.EdtLarvicidas);
            larvicidaML   = (EditText) findViewById(R.id.EdtLarvicidasML);
            ralo = (EditText) findViewById(R.id.EdtRalo);
            piscina = (EditText) findViewById(R.id.EdtPiscina);
            depTratadosFocal   = (EditText) findViewById(R.id.EdtDep_Tratados_Focal);
            depTratadosPerifocal   = (EditText) findViewById(R.id.EdtDep_Tratados_Perifocal);
            edtObs = (EditText) findViewById(R.id.EdtObservacoes);
            btnAmostras = (Button) findViewById(R.id.btnCadAmostra);
            btnAmostras.setOnClickListener(this);
        } catch (Exception e)  {
            Mensagem.exibeMessagem(ActVisitLast.this, "Erro",
                    "Erro ao tentar inicializar os objetos.");
        }

    }

    public void preencheSpinner(){
        TipoLarvicida.add("L1");
        TipoLarvicida.add("L2");
        TipoLarvicida.add("L3");
        TipoLarvicida.add("L4");
        TipoLarvicida.add("L5");
        TipoLarvicida.add("L6");
        TipoLarvicida.add("L7");
        TipoLarvicida.add("L8");
        TipoLarvicida.add("L9");
        TipoLarvicida.add("L10");

        //Identifica o Spinner no layout
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TipoLarvicida);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpTipoLarvicida.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        SpTipoLarvicida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                visit.setTipoLarvicida(parent.getItemAtPosition(posicao).toString());
                //imprime um Toast na tela com o nome que foi selecionado
                //Toast.makeText(ExemploSpinner.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
            }


            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void TotalInspecionado() {
        try {
            TPiscina = Integer.valueOf(piscina.getText().toString());
        } catch (Exception e) {
            TPiscina = 0;
            piscina.setText("0");
        }

        visit.setTPiscina(TPiscina);
        try {
            Tpneu = Integer.valueOf(pneu.getText().toString());
        } catch (Exception e) {
            Tpneu = 0;
            pneu.setText("0");
        }
        visit.setTpneu(Tpneu);
        try {
            Toutros = Integer.valueOf(outros.getText().toString());
        } catch (Exception e) {
            Toutros = 0;
            outros.setText("0");
        }
        visit.setToutros(Toutros);
        try {
            TrecNatural = Integer.valueOf(recNatural.getText().toString());
        } catch (Exception e) {
            TrecNatural = 0;
            recNatural.setText("0");
        }
        visit.setTrecNatural(TrecNatural);
        try {
            Tarmadilha = Integer.valueOf(armadilha.getText().toString());
        } catch (Exception e) {
            Tarmadilha = 0;
            armadilha.setText("0");
        }
        visit.setTarmadilha(Tarmadilha);
        try {
            TPOOL = Integer.valueOf(POOL.getText().toString());
        } catch (Exception e) {
            TPOOL = 0;
            POOL.setText("0");
        }
        visit.setTPOOL(TPOOL);
        try {
            Ttanque = Integer.valueOf(tanque.getText().toString());
        } catch (Exception e) {
            Ttanque = 0;
            tanque.setText("0");
        }
        visit.setTtanque(Ttanque);
        try {
            Ttambor = Integer.valueOf(tambor.getText().toString());
        } catch (Exception e) {
            Ttambor = 0;
            tambor.setText("0");
        }
        visit.setTtambor(Ttambor);
        try {
            Tbarril = Integer.valueOf(barril.getText().toString());
        } catch (Exception e) {
            Tbarril = 0;
            barril.setText("0");
        }
        visit.setTbarril(Tbarril);
        try {
            Ttina = Integer.valueOf(tina.getText().toString());
        } catch (Exception e) {
            Ttina = 0;
            tina.setText("0");
        }
        visit.setTtina(Ttina);
        try {
            Tpote = Integer.valueOf(pote.getText().toString());
        } catch (Exception e) {
            Tpote = 0;
            pote.setText("0");
        }
        visit.setTpote(Tpote);
        try {
            Tfiltro = Integer.valueOf(filtro.getText().toString());
        } catch (Exception e) {
            Tfiltro = 0;
            filtro.setText("0");
        }
        visit.setTfiltro(Tfiltro);
        try {
            Tquartinha = Integer.valueOf(quartinha.getText().toString());
        } catch (Exception e) {
            Tquartinha = 0;
            quartinha.setText("0");
        }
        visit.setTquartinha(Tquartinha);
        try {
            Tvaso = Integer.valueOf(vaso.getText().toString());
        } catch (Exception e) {
            Tvaso = 0;
            vaso.setText("0");
        }
        visit.setTvaso(Tvaso);
        try {
            TmatConst = Integer.valueOf(matConst.getText().toString());
        } catch (Exception e) {
            TmatConst = 0;
            matConst.setText("0");
        }
        visit.setTmatConst(TmatConst);
        try {
            TpecaCarro = Integer.valueOf(pecaCarro.getText().toString());
        } catch (Exception e) {
            TpecaCarro = 0;
            pecaCarro.setText("0");
        }
        visit.setTpecaCarro(TpecaCarro);
        try {
            Tgarrafa = Integer.valueOf(garrafa.getText().toString());
        } catch (Exception e) {
            Tgarrafa = 0;
            garrafa.setText("0");
        }
        visit.setTgarrafa(Tgarrafa);
        try {
            Tlata = Integer.valueOf(lata.getText().toString());
        } catch (Exception e) {
            Tlata = 0;
            lata.setText("0");
        }
        visit.setTlata(Tlata);
        try {
            TdepPlast = Integer.valueOf(depPlast.getText().toString());
        } catch (Exception e) {
            TdepPlast = 0;
            depPlast.setText("0");
        }
        visit.setTdepPlast(TdepPlast);
        try {
            Tpoco = Integer.valueOf(poco.getText().toString());
        } catch (Exception e) {
            Tpoco = 0;
            poco.setText("0");
        }
        visit.setTpoco(Tpoco);
        try {
            Tcisterna = Integer.valueOf(cisterna.getText().toString());
        } catch (Exception e) {
            Tcisterna = 0;
            cisterna.setText("0");
        }
        visit.setTcisterna(Tcisterna);
        try {
            Tcacimba = Integer.valueOf(cacimba.getText().toString());
        } catch (Exception e) {
            Tcacimba = 0;
            cacimba.setText("0");
        }
        visit.setTcacimba(Tcacimba);
        try {
            TcxDagua = Integer.valueOf(cxDagua.getText().toString());
        } catch (Exception e) {
            TcxDagua = 0;
            cxDagua.setText("0");
        }
        visit.setTcxDagua(TcxDagua);
        try {
            TRalo = Integer.valueOf(ralo.getText().toString());
        } catch(Exception e) {
            TRalo = 0;
            ralo.setText("0");
        }
        visit.setTRalo(TRalo);
        //if (amostras.getText().toString().equals("")) {
        //	amostras.setText("0");
        //}
        if (larvicida.getText().toString().equals("")) {
            larvicida.setText("0");
        }
        visit.setLarvicidaGT(larvicida.getText().toString());
        if (larvicidaML.getText().toString().equals("")) {
            larvicidaML.setText("0");
        }
        visit.setLarvicidaML(larvicidaML.getText().toString());
        if (depTratadosFocal.getText().toString().equals("")){
            depTratadosFocal.setText("0");
        }
        visit.setDepTratadosFocal(depTratadosFocal.getText().toString());
        if(depTratadosPerifocal.getText().toString().equals("")){
            depTratadosPerifocal.setText("0");
        }
        visit.setDepTratadosPerifocal(depTratadosPerifocal.getText().toString());
        if (depEliminados.getText().toString().equals("")) {
            depEliminados.setText("0");
        }
        visit.setDepEliminados(depEliminados.getText().toString());
        visit.setEdtObs(edtObs.getText().toString());

//        SomaTotal = (Tpneu + Ttanque + Ttambor + Tbarril + Ttina + Tpote+ Tfiltro + Toutros+
//                Tquartinha + Tvaso + TmatConst + TpecaCarro + Tgarrafa + Tlata + TdepPlast +
//                Tpoco + Tcisterna + TcxDagua + Tcacimba+TPOOL+TrecNatural+Tarmadilha+TRalo+TPiscina);
    }

    public void PreparaInsercao() {
        TotalInspecionado();
        db.open();
        try {
            ContentValues cvVisit = new ContentValues();
            cvVisit.put("VIS_IDRUA", visit.getIdrua());
            cvVisit.put("VIS_NUMERO", Integer.parseInt(visit.getNumeroResidencia()));
            cvVisit.put("VIS_COMPLEMENTO", visit.getComplemento());
            cvVisit.put("VIS_TIPO_IMOVEL", visit.getTipoImovel());
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
            cvVisit.put("VIS_AGENTE", visit.getIdagente());
            cvVisit.put("VIS_DEP_TRATADOS_FOCAL", visit.getDepTratadosFocal());
            cvVisit.put("VIS_DEP_TRATADOS_PERIFOCAL", visit.getDepTratadosPerifocal());
            cvVisit.put("VIS_TIPO_LARVICIDA", visit.getTipoLarvicida());
            cvVisit.put("VIS_RALO", visit.getTRalo());
            cvVisit.put("VIS_PISCINA", visit.getTPiscina());
            cvVisit.put("VIS_OBS", visit.getEdtObs());
            cvVisit.put("VIS_DEP_ELIMINADOS", visit.getDepEliminados());
            cvVisit.put("CICLO", visit.getIdciclo());
            cvVisit.put("FOIIMPORTADO", 0);

            db.insert("visit", cvVisit);
            Mensagem.exibeMessagem(ActVisitLast.this, "endemics", "Visita salva com sucesso!");
        } catch (Exception e){
            Mensagem.exibeMessagem(ActVisitLast.this,"endemics","Não foi possível salvar a visita!");
        }


        AlertDialog.Builder dialog = new AlertDialog.Builder(ActVisitLast.this);
        dialog.setMessage("Deseja Realizar outra visita para a mesma Rua? ");
        dialog.setNegativeButton("Sim", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        ActVisitIni.show(ActVisitLast.this, visit.getIdrua());
                        finish();
                    }
                });

        dialog.setPositiveButton("Não", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        Intent i = new Intent(ActVisitLast.this, ActMainActivity.class);
                        finish();
                        startActivity(i);

                    }
                });
        dialog.setTitle("endemics");
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if(btCancelar==v){
            Intent i = new Intent(ActVisitLast.this, ActMainActivity.class);
            finish();
            startActivity(i);
        } else if(btSalvar==v) {
            PreparaInsercao();
        } else if(btnAmostras==v){
            ActAmostra.show(ActVisitLast.this,visit.getCodigo());
        }
    }
}
