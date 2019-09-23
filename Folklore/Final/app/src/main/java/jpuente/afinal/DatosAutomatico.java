package jpuente.afinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class DatosAutomatico extends Activity{

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Folcloricas folcloricas = new Folcloricas(DatosAutomatico.this, "Folcloricas", null, 1);
        final SQLiteDatabase bbdd = folcloricas.getWritableDatabase();

        if (bbdd != null) {
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Carmen Sevilla', 'folcloricas/carmensevilla.jpg', 'folcloricas/carmensevilla.txt', 'yM__pTz9_bI')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Estrellita Castro', 'folcloricas/estrellitacastro.jpg', 'folcloricas/estrellitacastro.txt','XzxYeWZg_Hk')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Imperio Argentina', 'folcloricas/imperioargentina.jpg', 'folcloricas/imperioargentina.txt', '9JbO3nhfLOk')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Isabel Pantoja', 'folcloricas/isabelpantoja.jpg', 'folcloricas/isabelpantoja.txt', '-9PJ_ShDaDg')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Lola Flores', 'folcloricas/lolaflores.jpg', 'folcloricas/lolaflores.txt', 'JF-dTBA3ywY')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Marife de Triana', 'folcloricas/marifedetriana.jpg', 'folcloricas/marifedetriana.txt', 'ukrvs1g51v8')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Marisol', 'folcloricas/marisol.jpg', 'folcloricas/marisol.txt', 'QDmu3UpDtZE')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Marujita Diaz', 'folcloricas/marujitadiaz.jpg', 'folcloricas/marujitadiaz.txt', 'tZTr-EVsQvw')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Paquita Rico', 'folcloricas/paquitarico.jpg', 'folcloricas/paquitarico.txt', 'R74twMsk0EM')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Rocio Dúrcal', 'folcloricas/rociodurcal.jpg', 'folcloricas/rociodurcal.txt', 'Y91nwd9I8_c')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Rocio Jurado', 'folcloricas/rociojurado.jpg', 'folcloricas/rociojurado.txt', 'qMxkjeHN1O4')");
            bbdd.execSQL("INSERT INTO Folcloricas (nombre,urlfoto,descripcion,videos) VALUES ('Sara Montiel', 'folcloricas/saramontiel.jpg', 'folcloricas/saramontiel.txt', 'I1Rll1KKSB8')");
        }
        bbdd.close();//finalmente, cerramos.
        //finish();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Carga de Datos Satisfactoria")
                .setTitle("DATOS AUTOMÁTICO")
                .setCancelable(false)
                .setIcon(R.drawable.ok)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
