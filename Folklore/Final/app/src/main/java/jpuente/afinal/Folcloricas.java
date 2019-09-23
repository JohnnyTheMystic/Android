package jpuente.afinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Folcloricas extends SQLiteOpenHelper {

    private String creacion = "CREATE TABLE Folcloricas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, urlfoto TEXT, descripcion TEXT, videos TEXT)";

    public Folcloricas(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bbdd) {
        bbdd.execSQL(creacion);
    }
    @Override
    public void onUpgrade(SQLiteDatabase bbdd, int versionantigua, int versionnueva) {
        bbdd.execSQL("DROP TABLE IF EXISTS Folcloricas");
        bbdd.execSQL(creacion);
    }
}
