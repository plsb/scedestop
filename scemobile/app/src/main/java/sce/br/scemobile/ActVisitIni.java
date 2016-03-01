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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sce.br.dao.Database;
import sce.br.model.InsereVisita;
import sce.br.model.Mensagem;
import sce.br.model.Visit;
import sce.br.util.Ativo;
import sce.br.util.GPSTracker;


public class ActVisitIni extends Activity implements View.OnClickListener {

    private static int operacao=0;

    public static void recuperarImovel(Context ctx,Visit visit){
        ActVisitIni.visit = visit;
        visit.setTipoVisita("RECUPERADA");
        Intent i = new Intent(ctx, ActVisitIni.class);
        ctx.startActivity(i);
    }

    private static int idrua;
    private ArrayList<String> doenca = new ArrayList<String>();
    private ArrayList<String> atividade = new ArrayList<String>();
    private Button btContinuar, btClose;
    private EditText edtRua;
    private Database db = new Database(ActVisitIni.this);
    private static Visit visit = new Visit();

    private EditText num, complem, responsavel;
    private RadioGroup tipoImovel, tipoVisita;
    private RadioButton rbNormal, rbRecuperada, rbFechada, rbRecusada, rbResidencial,
            rbComercial, rbTB, rbPE, rbO;

    private Spinner SpcodDoenca, SpAtividade;

    public static void show(Context ctx, int rua) {
        visit = new Visit();
        visit.setCodigo("");
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

            rbRecuperada.setEnabled(false);

            if(!visit.getCodigo().equals("")){
                edtRua.setText(visit.getNomerua());
                rbRecuperada.setChecked(true);
                rbNormal.setEnabled(false);
                rbFechada.setEnabled(false);
                rbRecusada.setEnabled(false);
                switch (visit.getTipoImovel()){
                    case "R":rbResidencial.setChecked(true);
                        break;
                    case "C":rbComercial.setChecked(true);
                        break;
                    case "TB":rbTB.setChecked(true);
                        break;
                    case "PE":rbPE.setChecked(true);
                        break;
                    case "O":rbO.setChecked(true);
                        break;
                }
                rbResidencial.setEnabled(false);
                rbComercial.setEnabled(false);
                rbTB.setEnabled(false);
                rbPE.setEnabled(false);
                rbO.setEnabled(false);
                num.setText(visit.getNumeroResidencia());
                num.setEnabled(false);
                complem.setText(visit.getComplemento());
                complem.setEnabled(false);
                responsavel.setText(visit.getResponsavel());
                idrua = visit.getIdrua();
            }

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

                GPSTracker gps = gps = new GPSTracker(ActVisitIni.this);
                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    visit.setLatitude(latitude);
                    visit.setLongitude(longitude);
                }else{
                    // não pôde pegar a localização
                    // GPS ou a Rede não está habilitada
                    // Pergunta ao usuário para habilitar GPS/Rede em configurações
                    Toast.makeText(ActVisitIni.this,"Ative o GPS!",Toast.LENGTH_LONG).show();
                    gps.showSettingsAlert();
                }

                visit.setIdrua(idrua);
                visit.setNomerua(edtRua.getText().toString());
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
                if(visit.getTipoVisita().equals("RECUPERADA")){
                    ContentValues cv = new ContentValues();
                    cv.put("FOIIMPORTADO",0);
                    db.update("visit",cv,"_ID="+visit.getCodigo());
                    visit.setDataRecuperada(data.format(new Date(System.currentTimeMillis())));
                    operacao=1;
                } else {
                    operacao=0;
                    visit.setData(data.format(new Date(System.currentTimeMillis())));
                    String codigo = String.valueOf(Ativo.getCity().getId())+
                            String.valueOf(Ativo.getCycle().getId())+
                            String.valueOf(visit.getIdagente())+visit.getHora().replaceAll(":","")
                            +visit.getData().replace("/","");
                    visit.setCodigo(codigo);
                    visit.setDataRecuperada("");
                }


                if(visit.getData()==null){
                    visit.setData(visit.getDataRecuperada());
                }

                if(visit.getTipoVisita().equals("FECHADA") || visit.getTipoVisita().equals("RECUSADA")){
                    visit.setTipoLarvicida("");
                    PreparaInsercao();
                    ActMainActivity.mostraContagemAImportar(ActVisitIni.this);
                } else {
                    ActVisitLast.show(visit, ActVisitIni.this, operacao);
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
        db.open();
        TotalInspecionado();
        InsereVisita ins = new InsereVisita();
        if(ins.insereVisita(visit, ActVisitIni.this,operacao)!=-1){
            Toast.makeText(ActVisitIni.this, "(FEHCADA/RECUSADA) Visita Inserida Com Sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActVisitIni.this, "Não foi possível salvar a visita!", Toast.LENGTH_SHORT).show();
        }


        AlertDialog.Builder dialog = new AlertDialog.Builder(ActVisitIni.this);
        dialog.setMessage("Deseja Realizar outra visita para a mesma Rua? ");
        dialog.setPositiveButton("Sim", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        ActVisitIni.show(ActVisitIni.this, visit.getIdrua());
                        finish();
                    }
                });

        dialog.setNegativeButton("Não", new
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

    @Override
    protected void onPause() {
        super.onPause();
    }


}
