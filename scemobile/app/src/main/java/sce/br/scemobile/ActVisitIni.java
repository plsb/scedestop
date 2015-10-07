package sce.br.scemobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sce.br.dao.Database;
import sce.br.model.Mensagem;
import sce.br.model.Visit;
import sce.br.util.Ativo;


public class ActVisitIni extends Activity implements View.OnClickListener {

    private static int idrua;
    private ArrayList<String> doenca = new ArrayList<String>();
    private ArrayList<String> atividade = new ArrayList<String>();
    private Button btContinuar, btClose;
    private EditText edtRua;
    private Database db = new Database(ActVisitIni.this);
    Visit visit = new Visit();

    private EditText num, complem, responsavel;
    private RadioGroup tipoImovel, tipoVisita;
    private RadioButton rbNormal, rbRecuperada, rbFechada, rbRecusada, rbResidencial,
            rbComercial, rbTB, rbPE, rbO;

    private Spinner SpcodDoenca, SpAtividade;

    public static void show(Context ctx, int rua) {
        ActVisitIni.idrua = rua;
        Intent i = new Intent(ctx, ActVisitIni.class);
        ctx.startActivity(i);
    }

    private void refenciaCamposXml() {
        try {
            edtRua = (EditText) findViewById(R.id.edtLogradouro);
            btClose = (Button) findViewById(R.id.BtnCancelarVisita);
            btClose.setOnClickListener(this);
            btContinuar = (Button) findViewById(R.id.BtnContinuaVisita);
            btContinuar.setOnClickListener(this);
            tipoImovel = (RadioGroup) findViewById(R.id.RgTipoImovel);
            tipoVisita = (RadioGroup) findViewById(R.id.RgTipoVisita);
            num = (EditText) findViewById(R.id.EdtNum);
            responsavel = (EditText) findViewById(R.id.EdtResponsavelImovel);
            complem = (EditText) findViewById(R.id.EdtComplemento);
            SpcodDoenca = (Spinner) findViewById(R.id.SpDoenca);
            SpAtividade = (Spinner) findViewById(R.id.SpAtividade);
            rbNormal = (RadioButton) findViewById(R.id.RbNormal);
            rbRecuperada = (RadioButton) findViewById(R.id.RbRecuperado);
            rbFechada = (RadioButton) findViewById(R.id.RbFechado);
            rbRecusada = (RadioButton) findViewById(R.id.RbRecusado);
            rbResidencial = (RadioButton) findViewById(R.id.RbResidencial);
            rbComercial = (RadioButton) findViewById(R.id.RbComercio);
            rbTB = (RadioButton) findViewById(R.id.RbTerrenoBaldio);
            rbPE = (RadioButton) findViewById(R.id.RbPontoEstrategico);
            rbO = (RadioButton) findViewById(R.id.RbOutros);

        } catch (Exception e) {
            Mensagem.exibeMessagem(ActVisitIni.this, "Erro", "Ocorreu erro ao tentar inicializar os objetos.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_visit_ini);
        db.open();
        refenciaCamposXml();

        prencheSpiner();

        //pega a rua
        Cursor cursor = db.consult("street", new String[]{"*"},
                " _ID = ?",
                new String[]{String.valueOf(idrua)}, null, null,
                null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            edtRua.setText(cursor.getString(cursor.getColumnIndex("description")).toString());
        }


    }

    @Override
    public void onClick(View v) {
        if (btClose == v) {
            Intent i = new Intent(ActVisitIni.this, ActMainActivity.class);
            finish();
            startActivity(i);
        } else if (btContinuar == v) {
            if (num.getText().toString().equals("")) {
                Mensagem.exibeMessagem(ActVisitIni.this, "Atenção", "O campo [Número] deve ser preenchido.");
            } else if (tipoImovel.getCheckedRadioButtonId() == -1) {
                Mensagem.exibeMessagem(ActVisitIni.this, "Atenção", "Selecione o tipo de imóvel.");
            } else if (tipoVisita.getCheckedRadioButtonId() == -1) {
                Mensagem.exibeMessagem(ActVisitIni.this, "Atenção", "Selecione o tipo de visita.");
            } else {

                visit.setIdrua(idrua);
                visit.setComplemento(complem.getText().toString());
                visit.setNumeroResidencia(num.getText().toString());
                visit.setIdciclo(Ativo.getCycle().getId());
                visit.setResponsavel(responsavel.getText().toString());
                visit.setTipoImovel(verificaTipoImovel());
                visit.setTipoVisita(verificaTipoVisita());
                visit.setIdrua(idrua);
                visit.setIdagente(Ativo.getUser().getId());
                SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
                visit.setHora(hora.format(new Date(System.currentTimeMillis())));
                SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                visit.setData(data.format(new Date(System.currentTimeMillis())));
                String codigo = String.valueOf(visit.getIdagente())+visit.getHora().replaceAll(":","")
                        +visit.getData().replace("/","");
                visit.setCodigo(codigo);

                if(visit.getTipoVisita().equals("FECHADA") || visit.getTipoVisita().equals("RECUSADA")){
                    PreparaInsercao();
                } else {
                    ActVisitLast.show(visit, ActVisitIni.this);
                }
            }

        }
    }

    public void prencheSpiner() {
        //Spinner Doenca

        doenca.add("A90 - Dengue");

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, doenca);
        ArrayAdapter<String> spinnerArrayAdapter2 = arrayAdapter2;
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpcodDoenca.setAdapter(spinnerArrayAdapter2);

        SpcodDoenca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {

                String coddoenca = parent.getItemAtPosition(posicao).toString();
                String coddoenca2 = coddoenca.substring(0, 3);

                visit.setDoenca(coddoenca2.toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Tipo Atividade

        atividade.add("1 - LI - Levantamento de Indice");
        atividade.add("2 - LI+T - Levantamento de Indice + Tratamento");
        atividade.add("3 - PE - Ponto Estratégico");
        atividade.add("4 - T - Tratamento");
        atividade.add("5 - DF - Delimitação de Foco");
        atividade.add("6 - PVE - Pesquisa Vetorial Espacial");

        ArrayAdapter<String> arrayAdapterAtividade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, atividade);
        ArrayAdapter<String> spinnerArrayAdapterAtividade = arrayAdapterAtividade;
        spinnerArrayAdapterAtividade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpAtividade.setAdapter(spinnerArrayAdapterAtividade);

        SpAtividade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {

                String codAtividade = parent.getItemAtPosition(posicao).toString();
                String codAtividade2 = codAtividade.substring(0, 1);

                visit.setTipoAtividade(codAtividade2.toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public String verificaTipoImovel() {
        try {
            switch (tipoImovel.getCheckedRadioButtonId()) {
                case R.id.RbResidencial /*2131099659*/:
                    return "R";

                //Mensagem.exibeMessagem(Imovel.this, "TIPO_IMOVEL", "R");

                case R.id.RbComercio/*2131099660*/ :
                    return "C";

                //Mensagem.exibeMessagem(Imovel.this, "TIPO_IMOVEL", "C");

                case R.id.RbTerrenoBaldio /*2131099661*/ :
                    return "TB";

                //Mensagem.exibeMessagem(Imovel.this, "TIPO_IMOVEL", "TB");

                case R.id.RbPontoEstrategico /*2131099662*/:
                    return "PE";

                //Mensagem.exibeMessagem(Imovel.this, "TIPO_IMOVEL", "PE");

                case R.id.RbOutros /*2131099663*/:
                    return "O";

                //Mensagem.exibeMessagem(Imovel.this, "TIPO_IMOVEL", "O");

            }
        } catch (Exception e) {
            Mensagem.exibeMessagem(ActVisitIni.this, "ERRO", "Erro tipo_imovel" + e.getMessage());
        }
        return null;

    }

    public String verificaTipoVisita() {
        try
        {
            switch (tipoVisita.getCheckedRadioButtonId()){
                case R.id.RbNormal :
                    return "NORMAL";

                    //Mensagem.exibeMessagem(Imovel.this, "normal", "");

                case R.id.RbFechado :
                    return "FECHADA";

                    //Mensagem.exibeMessagem(Imovel.this, "Fechada", "");

                case R.id.RbRecusado :
                    return "RECUSADA";

                    //Mensagem.exibeMessagem(Imovel.this, "recusada", "");

                case R.id.RbRecuperado :
                    return "RECUPERADA";

                    //Mensagem.exibeMessagem(Imovel.this, "recuperada", "");

            }
        }catch(Exception e){
            Mensagem.exibeMessagem(ActVisitIni.this, "ERRO","Erro tipo_visita" + e.getMessage());
        }
        return null;

    }

    public void TotalInspecionado() {
            visit.setTPiscina(0);
            visit.setTpneu(0);
            visit.setToutros(0);
            visit.setTrecNatural(0);
            visit.setTarmadilha(0);
            visit.setTPOOL(0);
            visit.setTtanque(0);
            visit.setTtambor(0);
            visit.setTbarril(0);
            visit.setTtina(0);
            visit.setTpote(0);
            visit.setTfiltro(0);
            visit.setTquartinha(0);
            visit.setTvaso(0);
            visit.setTmatConst(0);
            visit.setTpecaCarro(0);
            visit.setTgarrafa(0);
            visit.setTlata(0);
            visit.setTdepPlast(0);
            visit.setTpoco(0);
            visit.setTcisterna(0);
            visit.setTcacimba(0);
            visit.setTcxDagua(0);
            visit.setTRalo(0);
            visit.setLarvicidaGT("");
            visit.setLarvicidaML("");
            visit.setDepTratadosFocal("");
            visit.setDepTratadosPerifocal("");
            visit.setDepEliminados("");
            visit.setEdtObs("");
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
            Mensagem.exibeMessagem(ActVisitIni.this, "endemics", "Fechado/Recusado. Visita salva com sucesso!");
        } catch (Exception e){
            Mensagem.exibeMessagem(ActVisitIni.this,"endemics","Não foi possível salvar a visita!");
        }


        AlertDialog.Builder dialog = new AlertDialog.Builder(ActVisitIni.this);
        dialog.setMessage("Deseja Realizar outra visita para a mesma Rua? ");
        dialog.setNegativeButton("Sim", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        ActVisitIni.show(ActVisitIni.this, visit.getIdrua());
                        finish();
                    }
                });

        dialog.setPositiveButton("Não", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        Intent i = new Intent(ActVisitIni.this, ActMainActivity.class);
                        finish();
                        startActivity(i);

                    }
                });
        dialog.setTitle("endemics");
        dialog.show();
    }

}
