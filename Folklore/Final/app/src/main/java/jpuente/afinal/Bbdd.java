package jpuente.afinal;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Bbdd extends Activity {

    private static final int READ_REQUEST_CODE = 42;
    private static final int WIKI_REQUEST_CODE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbdd);

        final EditText nombre = (EditText) findViewById(R.id.nombre);
        final ImageButton selectFoto = (ImageButton) findViewById(R.id.imageButton);
        final ImageButton selectTxt = (ImageButton)findViewById(R.id.imageButton2);
        final EditText videos = (EditText) findViewById(R.id.videos);

        final TextView urlFoto = findViewById(R.id.urlfoto);
        final TextView wiki = findViewById(R.id.descripcion);

        Folcloricas folcloricas = new Folcloricas(Bbdd.this, "Folcloricas", null, 1);
        final SQLiteDatabase bbdd = folcloricas.getWritableDatabase();

        selectFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performImagenSearch();
            }
        });

        selectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performTxtSearch();
            }
        });

        final Button insertar = (Button) findViewById(R.id.btinsertar);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues valores = new ContentValues();
                valores.put("nombre", nombre.getText().toString());
                valores.put("urlfoto", urlFoto.getText().toString());
                valores.put("descripcion", wiki.getText().toString());
                valores.put("videos", videos.getText().toString());

                bbdd.insert("Folcloricas", null, valores);

                nombre.setText("");
                urlFoto.setText("");
                wiki.setText("");
                videos.setText("");
            }
        });

        final Button btupdate = (Button) findViewById(R.id.btupdate);
        btupdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ContentValues valores = new ContentValues();
                valores.put("descripcion", wiki.getText().toString());
                valores.put("videos", videos.getText().toString());
                String where = nombre.getText().toString();
                bbdd.update("Folcloricas",valores, "nombre='" + where + "'", null);

                nombre.setText("");
                wiki.setText("");
                videos.setText("");
            }
        });

        final Button btborrar = (Button) findViewById(R.id.btborrar);
        btborrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bbdd.delete("Folcloricas", "nombre='" + nombre.getText().toString()+"'",null);

                nombre.setText("");
                urlFoto.setText("");
                wiki.setText("");
                videos.setText("");
            }
        });

       /* Button btmostrar = (Button)findViewById(R.id.btmostrar);
        final TextView label = (TextView)findViewById(R.id.label);

        btmostrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                label.setText("");
                String campos[]= new String[] {"nombre","telefono","ciudad"};
                Cursor cursorbbdd = bbdd.query("Amigos", campos, null, null, null, null, null);

                if (cursorbbdd.moveToFirst()){
                    do{
                        String nombre = cursorbbdd.getString(0);
                        String telefono = cursorbbdd.getString(1);
                        String ciudad = cursorbbdd.getString(2);
                        label.append(nombre +"     "+ telefono +"     "+ciudad+"\n");
                    }while (cursorbbdd.moveToNext());
                }

                nombre.setText("");
                telefono.setText("");
                ciudad.setText("");
            }
        });
*/

        Button btsalir = (Button) findViewById(R.id.btsalir);
        btsalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bbdd.close();//finalmente, cerramos.
                finish();
            }
        });

    }

    public void performImagenSearch() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void performTxtSearch() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, WIKI_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                TextView url = findViewById(R.id.urlfoto);
                url.setText(uri.toString());
            }
        }
        if (requestCode == WIKI_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                TextView wiki = findViewById(R.id.descripcion);
                wiki.setText(uri.toString());
            }
        }
    }
}
