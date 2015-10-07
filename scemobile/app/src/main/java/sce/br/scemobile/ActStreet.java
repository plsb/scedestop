package sce.br.scemobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

import sce.br.dao.Database;
import sce.br.model.Mensagem;

public class ActStreet extends ListActivity implements View.OnClickListener {

    TextView TvNome,TvBairro;
    private Button btnFiltrar;
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
        setContentView(R.layout.act_list_street);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
        btnFiltrar.setOnClickListener(this);
        edtFiltro = (EditText) findViewById(R.id.edtFiltro);

        _bd.open();
        logradouro =  _bd.consult("street", new String[]{"count(*)"}, null, null, null, null, null, null);
        logradouro.moveToFirst();
        if(logradouro.getCount()==0){
            Mensagem.exibeMessagem(ActStreet.this, "endmeics", "Não Existe Ruas!");
        }
        listarQuarteiroes(false);
    }

    public void listarQuarteiroes(boolean usaFiltro){
        _bd.open();
        HashMap<String, String> item;
        try {
            list.clear();
            Cursor _cursor = null;
            if (!usaFiltro) {
                _cursor = _bd
                        .consult("street", new String[]{"_ID", "description", "idquarter", "district","area"},
                                null,
                                null, null, null, "idquarter,description", null);
            } else {
                _cursor = _bd
                        .consult("street", new String[]{"_ID", "description", "idquarter", "district", "area"},
                                "idquarter="
                                        + edtFiltro.getText().toString()
                                ,
                                null, null, null, "idquarter,description", null);
            }
            _cursor.moveToFirst();
            item = new HashMap<String, String>();
            do{
                item = new HashMap<String, String>();
                item.put("line1", "Quart: " + _cursor.getString(_cursor.getColumnIndex("idquarter")).toString() +
                        ", Rua:" + _cursor.getString(_cursor.getColumnIndex("description")).toString());
                item.put("line2", "Cód.:"+_cursor.getString(_cursor.getColumnIndex("_ID")).toString()
                        +" | Bairro.:"+_cursor.getString(_cursor.getColumnIndex("district")).toString()+", Area: "+
                        _cursor.getString(_cursor.getColumnIndex("area")).toString());
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
            listarQuarteiroes(true);
        }
    }

    @Override
    public void onListItemClick(ListView l,View v,int position,long id){
        super.onListItemClick(l, v, position, id);

        o = this.getListAdapter().getItem(position);

        AlertDialog.Builder dialog = new AlertDialog.Builder(ActStreet.this);
        dialog.setMessage("");
        dialog.setPositiveButton("Iniciar Visita", new
                DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int arg) {
                        Log.i("Retorno", o.toString());
                        String id = o.toString();

                        int posIni,posFim;

                        posIni = id.indexOf("Cód.:")+2;
                        posFim = id.lastIndexOf(" |");

                        id = id.substring(posIni + 3, posFim);
                        Log.i("CodBAirro", id);
                        ActVisitIni.show(ActStreet.this, Integer.parseInt(id));
                        finish();
                    }
                });
        dialog.setTitle("Opções");
        dialog.show();
    }
}