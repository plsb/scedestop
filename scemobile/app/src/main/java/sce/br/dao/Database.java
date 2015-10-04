package sce.br.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        private static final int DATABASE_VERSION = 4;
        private static final String DATABASE_NAME = "endemics";

        private static final String TB_USER = "CREATE TABLE if not exists USER " +
                "(_ID integer PRIMARY KEY autoincrement NOT NULL,"
                + "resgistration text NOT NULL,name text NOT NULL," +
                "password text NOT NULL, CPF TEXT, imeimobile text, type text);";

        private static final String TB_CITY = "CREATE TABLE if not exists city " +
                "(_ID integer PRIMARY KEY autoincrement NOT NULL,"
                + "description text NOT NULL," +
                "state text NOT NULL,idibge integer NOT NULL);";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TB_USER);
            db.execSQL(TB_CITY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase bd, int versaoAntiga, int versaoNova) {
            bd.execSQL("DROP TABLE IF EXISTS user");
            bd.execSQL("DROP TABLE IF EXISTS city");

            onCreate(bd);
        }

    }
}
