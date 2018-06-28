package co.com.passrecovery.db.claves;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClavesDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "passrecovery.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CLAVES = "tbl_claves";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE_SERVICIO = "ds_nombre_servicio";
    public static final String COLUMN_PASS = "ds_pass";
    public static final String COLUMN_NOMBRE_USUARIO = "ds_nombre_usuario";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CLAVES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE_SERVICIO + " TEXT, " +
                    COLUMN_NOMBRE_USUARIO + " TEXT," +
                    COLUMN_PASS + " TEXT)";

    public ClavesDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLAVES);
        db.execSQL(TABLE_CREATE);
    }

}
