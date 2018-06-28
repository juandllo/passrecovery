package co.com.passrecovery.db.claves;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.com.passrecovery.model.Claves;

public class ClavesOperations {
    public static final String LOG = "CLA_MNGMNT_SYS";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
          ClavesDBHandler.COLUMN_ID,
          ClavesDBHandler.COLUMN_NOMBRE_SERVICIO,
          ClavesDBHandler.COLUMN_NOMBRE_USUARIO,
          ClavesDBHandler.COLUMN_PASS
    };

    public ClavesOperations(Context context) {
        dbHandler = new ClavesDBHandler(context);
    }

    public void open() {
        Log.i(LOG, "Base de datos abierta");
        database = dbHandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOG, "Base de datos cerrada");
        dbHandler.close();
    }

    public Claves addClave(Claves claves) {
        ContentValues values = new ContentValues();
        values.put(ClavesDBHandler.COLUMN_NOMBRE_SERVICIO, claves.getNombreServicio());
        values.put(ClavesDBHandler.COLUMN_NOMBRE_USUARIO, claves.getNombreUsuario());
        values.put(ClavesDBHandler.COLUMN_PASS, claves.getPass());
        long insertId = database.insert(ClavesDBHandler.TABLE_CLAVES, null, values);
        claves.set_id(insertId);
        return claves;
    }

    // Getting single Employee
    public Claves getClave(long id) {

        Cursor cursor = database.query(ClavesDBHandler.TABLE_CLAVES,allColumns,ClavesDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Claves c = new Claves();
        c.set_id(Long.parseLong(cursor.getString(0)));
        c.setNombreServicio(cursor.getString(1));
        c.setNombreUsuario(cursor.getString(2));
        c.setPass(cursor.getString(3));

        return c;
    }

    public List<Claves> getAllClaves() {
        Cursor cursor = database.query(ClavesDBHandler.TABLE_CLAVES, allColumns, null, null, null, null, null);
        List<Claves> claves = new ArrayList<>();
        Claves clave = new Claves();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                clave = new Claves();
                clave.set_id(cursor.getLong(cursor.getColumnIndex(ClavesDBHandler.COLUMN_ID)));
                clave.setNombreServicio(cursor.getString(cursor.getColumnIndex(ClavesDBHandler.COLUMN_NOMBRE_SERVICIO)));
                clave.setNombreUsuario(cursor.getString(cursor.getColumnIndex(ClavesDBHandler.COLUMN_NOMBRE_USUARIO)));
                clave.setPass(cursor.getString(cursor.getColumnIndex(ClavesDBHandler.COLUMN_PASS)));
                claves.add(clave);
            }
        }

        return claves;
    }

    public int updateClave(Claves clave) {
        ContentValues values = new ContentValues();
        values.put(ClavesDBHandler.COLUMN_NOMBRE_SERVICIO, clave.getNombreServicio());
        values.put(ClavesDBHandler.COLUMN_NOMBRE_USUARIO, clave.getNombreUsuario());
        values.put(ClavesDBHandler.COLUMN_PASS, clave.getPass());

        return database.update(ClavesDBHandler.TABLE_CLAVES, values,
                ClavesDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(clave.get_id())});
    }

    public void removeClave(Claves clave) {
        database.delete(ClavesDBHandler.TABLE_CLAVES, ClavesDBHandler.COLUMN_ID + "=" + clave.get_id(), null);
    }
}
