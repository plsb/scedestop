package sce.br.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

    private static SQLiteDatabase bd;
    private static Context cx;
    private DatabaseHelper dbHelper;

    public Database(Context cx){
        this.cx = cx;
    }

    public Database open(){
        dbHelper = new DatabaseHelper(cx);
        bd = dbHelper.getWritableDatabase();
        return this;
    }

    public void fechaBanco(){
        dbHelper.close();
    }

    public long insert(String tabela, ContentValues valores) {
        return bd.insert(tabela, null, valores);

    }

    public long update(String tabela, ContentValues valores, String clausulaWhere) {
        return bd.update(tabela, valores, clausulaWhere, null);

    }

    public boolean execSql(String Sql){
        try{
            open();
            bd.execSQL(Sql);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Cursor consult(String table, String[] columns, String where,
                          String[] argumentosWhere, String groupby, String having,
                          String orderby, String limit) {
        return bd.query(table, columns, where, argumentosWhere, groupby,
                having, orderby, limit);
    }

    //classe DatabaseHelper

    private class DatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 24;
        private static final String DATABASE_NAME = "endemics";

        private static final String TB_USER = "CREATE TABLE if not exists USER " +
                "(_ID integer PRIMARY KEY autoincrement NOT NULL,"
                + "resgistration text NOT NULL,name text NOT NULL," +
                "password text NOT NULL, CPF TEXT, imeimobile text, type text);";

        private static final String TB_CITY = "CREATE TABLE if not exists city " +
                "(_ID integer PRIMARY KEY autoincrement NOT NULL,"
                + "description text NOT NULL," +
                "state text NOT NULL,idibge integer NOT NULL);";

        private static final String TB_STREET = "CREATE TABLE if not exists street " +
                "(_ID integer PRIMARY KEY autoincrement NOT NULL,"
                + "description text NOT NULL,idquarter integer," +
                "district text NOT NULL,area text NOT NULL);";

        private static final String TB_CYCLE = "CREATE TABLE if not exists cycle " +
                "(_ID integer PRIMARY KEY autoincrement NOT NULL,"
                + "description text NOT NULL," +
                "dtstart text NOT NULL,dtfim text NOT NULL);";

        private static final String TB_VISIT= "CREATE TABLE if not exists visit (_ID text PRIMARY KEY NOT NULL,"
                + "VIS_IDRUA INTEGER ," + "VIS_NOMERUA TEXT ,"+ "VIS_NUMERO INTEGER ," + "CICLO string ,"
                + "VIS_COMPLEMENTO TEXT,"			 + "VIS_TIPO_IMOVEL TEXT,"+ "VIS_TIPO_VISITA TEXT,"
                + "VIS_HORA TEXT,"
                + "VIS_PNEU integer," 					 + "VIS_TANQUE integer,"
                + "VIS_TAMBOR integer," 				 + "VIS_BARRIL integer,"
                + "VIS_TINA integer,"					 + "VIS_POTE integer,"
                + "VIS_FILTRO integer,"				 + "VIS_QUARTINHA integer,"
                + "VIS_VASO integer,"					 + "VIS_MAT_CONSTRUCAO integer,"
                + "VIS_PECA_CARRO integer,"			 + "VIS_GARRAFA integer,"
                + "VIS_LATA integer,"					 + "VIS_DEP_PLASTICO integer,"
                + "VIS_POCO integer,"					 + "VIS_CISTERNA integer,"
                + "VIS_CACIMBA integer,"				 + "VIS_CX_DAGUA integer,"
                + "VIS_REC_NATURAL integer,"			 + "VIS_OUTROS integer,"
                + "VIS_ARMADILHA integer,"				 + "VIS_POOL integer,"
                + "VIS_TIPO_ATIVIDADE TEXT,"		 + "VIS_COD_DOENCA TEXT ,"
                + "VIS_RESPONSAVEL TEXT,"   		 + "VIS_LARVICIDAGT TEXT,"+ "VIS_LARVICIDAML TEXT,"
                + "VIS_DATA TEXT,"+ "VIS_DATA_RECUPERADA TEXT," + "VIS_AGENTE integer, "
                + "VIS_DEP_TRATADOS_FOCAL integer,"
                + "VIS_DEP_TRATADOS_PERIFOCAL integer," + "VIS_TIPO_LARVICIDA TEXT,"
                + "VIS_RALO integer,"					  + "VIS_PISCINA integer,"  + "VIS_OBS TEXT,"
                + "VIS_LATITUDE TEXT,"
                + "VIS_LONGITUDE TEXT,"
                + "VIS_DEP_ELIMINADOS integer, FOIIMPORTADO INTEGER DEFAULT 0);";

        private static final String TB_AMOSTRA = "CREATE TABLE if not exists amostra (_ID integer PRIMARY KEY autoincrement NOT NULL," +
                "NUM_AMOSTRA INTEGER NOT NULL, ID_VISIT text NOT NULL,ID_CICLO int NOT NULL," +
                " DEPOSITO TEXT NOT NULL, " +
                "NUM_LAVAS INTEGER NOT NULL, FOIEXPORTADO INTEGER DEFAULT 0);";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TB_USER);
            db.execSQL(TB_CITY);
            db.execSQL(TB_STREET);
            db.execSQL(TB_CYCLE);
            db.execSQL(TB_VISIT);
            db.execSQL(TB_AMOSTRA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase bd, int versaoAntiga, int versaoNova) {
            bd.execSQL("DROP TABLE IF EXISTS user");
            bd.execSQL("DROP TABLE IF EXISTS city");
            bd.execSQL("DROP TABLE IF EXISTS street");
            bd.execSQL("DROP TABLE IF EXISTS cycle");
            bd.execSQL("DROP TABLE IF EXISTS visit");
            bd.execSQL("DROP TABLE IF EXISTS amostra");

            onCreate(bd);
        }

    }
}
