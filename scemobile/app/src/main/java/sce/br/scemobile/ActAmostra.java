package sce.br.scemobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sce.br.dao.Database;
import sce.br.model.Amostra;
import sce.br.model.Mensagem;

/**
 * Created by Pedro Saraiva on 06/10/2015.
 */
public class ActAmostra extends Activity implements View.OnClickListener{
    private Button btConfirmar, btCancelar;
    private EditText codigo, numlavas;
    private Spinner SpTipoDeposito;
    private String deposito;
    private static String codVisita;

    public static void show(Context ctx, String codigo){
        ActAmostra.codVisita = codigo;
        Intent i = new Intent(ctx, ActAmostra.class);
        ctx.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_amostra);
        btConfirmar = (Button) findViewById(R.id.btnGravarAmostra);
        btConfirmar.setOnClickListener(this);
        btCancelar = (Button) findViewById(R.id.btnCancelarAmostra);
        btCancelar.setOnClickListener(this);
        codigo = (EditText) findViewById(R.id.EdtCodAmostra);
        numlavas = (EditText) findViewById(R.id.EdtNumLavas);
        SpTipoDeposito = (Spinner) findViewById(R.id.SpnTipoDeposito);

        preencheSpiner();
    }

    private Database db = new Database(ActAmostra.this);

    @Override
    public void onClick(View v) {
        if(v==btCancelar){
            finish();
        } else if(v==btConfirmar){
            if(codigo.getText().toString().equals("")){
                Mensagem.exibeMessagem(ActAmostra.this,"endemics","Informe o Código da Amostra");
            } else if(numlavas.getText().toString().equals("")){
                Mensagem.exibeMessagem(ActAmostra.this,"endemics","Informe o Número de Lavas");
            } else {
                try {
                    db.open();
                    ContentValues cvAmostra = new ContentValues();
                    cvAmostra.put("NUM_AMOSTRA", codigo.getText().toString().equals(""));
                    cvAmostra.put("DEPOSITO", deposito);
                    cvAmostra.put("ID_VISIT", codVisita);
                    cvAmostra.put("NUM_LAVAS", numlavas.getText().toString().equals(""));

                    Toast.makeText(ActAmostra.this, "Amostra Cadastrada Com Sucesso!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Mensagem.exibeMessagem(ActAmostra.this,"endemics", "Falha ao salvar Amostra!");
                }
                finish();

            }

        }
    }

    private ArrayList<String> TipoDeposito = new ArrayList<String>();

    public void preencheSpiner(){
        TipoDeposito.add("ARMADILHA");
        TipoDeposito.add("BARRIL");
        TipoDeposito.add("CACIMBA");
        TipoDeposito.add("CISTERNA");
        TipoDeposito.add("CX.DAGUA");
        TipoDeposito.add("DEP.PLASTICO");
        TipoDeposito.add("FILTRO");
        TipoDeposito.add("GARRAFA");
        TipoDeposito.add("LATA");
        TipoDeposito.add("MAT.CONSTRUCAO");
        TipoDeposito.add("OUTROS");
        TipoDeposito.add("PECA CARRO");
        TipoDeposito.add("PISCINA");
        TipoDeposito.add("PNEU");
        TipoDeposito.add("POCO");
        TipoDeposito.add("POOL");
        TipoDeposito.add("POTE");
        TipoDeposito.add("QUARTINHA");
        TipoDeposito.add("REC.NATURAIS");
        TipoDeposito.add("RALO");
        TipoDeposito.add("TANQUE");
        TipoDeposito.add("TAMBOR");
        TipoDeposito.add("TINA");
        TipoDeposito.add("VASO");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TipoDeposito);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpTipoDeposito.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        SpTipoDeposito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                deposito =
                    parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                //Toast.makeText(ExemploSpinner.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
            }


            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
