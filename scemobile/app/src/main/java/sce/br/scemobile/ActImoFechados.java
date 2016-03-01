package sce.br.scemobile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sce.br.dao.Database;
import sce.br.model.Mensagem;
import sce.br.model.Visit;
import sce.br.util.Ativo;

public class ActImoFechados extends ListActivity implements View.OnClickListener {

    TextView TvNome,TvBairro;
    private Button btnFiltrar, btVolta;
    private EditText edtFiltro;
    Object o;
    public Database _bd = new Database(this);
    Cursor logradouro;

    public static String quarteirao, CodBairro;

    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    private SimpleAdapter sa;
    private String nomeBairro="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list_pesq);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
        btnFiltrar.setOnClickListener(this);
        edtFiltro = (EditText) findViewById(R.id.edtFiltro);
        btVolta = (Button) findViewById(R.id.btnVoltar);
        btVolta.setOnClickListener(this);
        edtFiltro.setHint("Nome da Rua");

        _bd.open();
        logradouro =  _bd.consult("visit", new String[]{"count(*)"}, null, null, null, null, null, null);
        logradouro.moveToFirst();
        if(logradouro.getCount()==0){
            Mensagem.exibeMessagem(ActImoFechados.this, "endemics", "Não Existe Imóveis Fechados!");
        }
        listarImoFechados(false);
    }

    public void listarImoFechados(boolean usaFiltro){
        _bd.open();
        HashMap<String, String> item;
        try {
            list.clear();
            Cursor _cursor = null;
            if (!usaFiltro) {
                _cursor = _bd
                        .consult("visit", new String[]{"_ID", "VIS_IDRUA", "VIS_NOMERUA", "VIS_NUMERO", "VIS_COMPLEMENTO"},
                                "CICLO=? and VIS_TIPO_VISITA=?",
                                new String[] {String.valueOf(Ativo.getCycle().getId()), "FECHADA"}, null, null,
                                "VIS_IDRUA,VIS_NUMERO,VIS_COMPLEMENTO", null);
            } else {
                _cursor = _bd
                        .consult("visit", new String[]{"_ID", "VIS_IDRUA", "VIS_NOMERUA", "VIS_NUMERO", "VIS_COMPLEMENTO"},
                                "CICLO=? and VIS_TIPO_VISITA=? and VIS_NOMERUA like '%"+edtFiltro.getText().toString()+"%'",
                                new String[] {String.valueOf(Ativo.getCycle().getId()), "FECHADA"}, null, null,
                                "VIS_IDRUA,VIS_NUMERO,VIS_COMPLEMENTO", null);
            }
            _cursor.moveToFirst();
            item = new HashMap<String, String>();
            do{
                Cursor cRua = _bd.consult("street", new String[]{"_ID", "description", "idquarter", "district", "area"},
                        "_ID="+_cursor.getString(_cursor.getColumnIndex("VIS_IDRUA")).toString() ,
                        null, null, null, "idquarter,description", null);
                cRua.moveToFirst();


                item = new HashMap<String, String>();
                item.put("line1", "Rua: " + cRua.getString(cRua.getColumnIndex("description")).toString() +
                        ", " + _cursor.getString(_cursor.getColumnIndex("VIS_NUMERO")).toString()+"-"+
                        _cursor.getString(_cursor.getColumnIndex("VIS_COMPLEMENTO")).toString());
                item.put("line2", "Cód.:" + _cursor.getString(_cursor.getColumnIndex("_ID")).toString()
                        + " | Bairro.:" + cRua.getString(cRua.getColumnIndex("district")).toString() + ", Area: " +
                        cRua.getString(cRua.getColumnIndex("area")).toString());
                list.add(item);
            } while(_cursor.moveToNext());
             _cursor.close();
            _bd.fechaBanco();
        } catch (Exception e) {
            //Toast.makeText(this, "Exceção:" + e.getMessage(), Toast.LENGTH_LONG)
            //	.show();
        }

        sa = new SimpleAdapter(this, list, R.layout.act_list,
                new String[] { "line1", "line2" }, new int[] { R.id.line_a,
                R.id.line_b });
        if (!sa.isEmpty()) {
            setListAdapter(sa);
        } else {
            setListAdapter(sa);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == btnFiltrar) {
            listarImoFechados(true);
        } else if(v==btVolta){
//            ActMainActivity.mostraContagemAImportar(ActImoFechados.this);
//            Intent i = new Intent(ActImoFechados.this,ActMainActivity.class);
            finish();
//            startActivity(i);
        }
    }

    @Override
    public void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l, v, position, id);

        o = this.getListAdapter().getItem(position);

        AlertDialog.Builder dialog = new AlertDialog.Builder(ActImoFechados.this);
        dialog.setMessage("");
        dialog.setPositiveButton("Recuperar Visita", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        Log.i("Retorno", o.toString());
                        String id = o.toString();

                        int posIni,posFim;

                        posIni = id.indexOf("Cód.:")+2;
                        posFim = id.lastIndexOf(" |");

                        id = id.substring(posIni + 3, posFim);

                        Database db = new Database(ActImoFechados.this);
                        db.open();
                        Cursor c = db.consult("visit", new String[]{"_ID", "VIS_NOMERUA", "VIS_TIPO_IMOVEL", "VIS_NUMERO",
                                "VIS_COMPLEMENTO", "VIS_COD_DOENCA", "VIS_TIPO_ATIVIDADE","VIS_RESPONSAVEL","VIS_IDRUA",
                                "VIS_HORA","VIS_HORA","VIS_DATA"},
                                "_ID="+id ,
                                null, null, null,null, null);
                        c.moveToFirst();

                        Visit v = new Visit();
                        v.setCodigo(c.getString(c.getColumnIndex("_ID")).toString());
                        v.setNomerua(c.getString(c.getColumnIndex("VIS_NOMERUA")).toString());
                        v.setTipoImovel(c.getString(c.getColumnIndex("VIS_TIPO_IMOVEL")).toString());
                        v.setTipoVisita("RECUPERADA");
                        v.setNumeroResidencia(String.valueOf(c.getInt(c.getColumnIndex("VIS_NUMERO"))));
                        v.setComplemento(c.getString(c.getColumnIndex("VIS_COMPLEMENTO")).toString());
                        v.setDoenca(c.getString(c.getColumnIndex("VIS_COD_DOENCA")).toString());
                        v.setTipoAtividade(c.getString(c.getColumnIndex("VIS_TIPO_ATIVIDADE")).toString());
                        v.setResponsavel(c.getString(c.getColumnIndex("VIS_RESPONSAVEL")).toString());
                        v.setIdrua(c.getInt(c.getColumnIndex("VIS_IDRUA")));
                        v.setHora(c.getString(c.getColumnIndex("VIS_HORA")).toString());
                        v.setData(c.getString(c.getColumnIndex("VIS_DATA")).toString());

                        ActVisitIni.recuperarImovel(ActImoFechados.this, v);
                        finish();
                    }
                });
        dialog.setTitle("Opções");
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}