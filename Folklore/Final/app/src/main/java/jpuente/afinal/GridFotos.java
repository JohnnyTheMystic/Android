package jpuente.afinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GridFotos extends AppCompatActivity {

    private List<String> fotosdb;
    private List<String> nombresdb = new ArrayList<>();

    private GridView gridView;

    //private FotosAdapter fotosAdapter;
    //private RecyclerView recyclerView;

    private Hashtable<Integer, Integer> hijos = new Hashtable<Integer, Integer>();

    private final int PERMISION_READ_EXTERNAL_MEMORY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);

        fotosdb = getFoto();

        final FotosAdapter fotosAdapter = new FotosAdapter(this, fotosdb, nombresdb, R.layout.grid_fotos);
        //fotosAdapter = new FotosAdapter(this, fotosdb, R.layout.grid_fotos);

        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(fotosAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE); //Guardar con sharedpreferences los ids.
                SharedPreferences.Editor editor = prefe.edit();
                editor.putInt("id", hijos.get(position));
                editor.commit();

                Intent intentDeslizador = new Intent(GridFotos.this, Deslizador.class);
                intentDeslizador.putExtra("id", hijos.get(position));
                startActivity(intentDeslizador);

                //fotosAdapter.notifyDataSetChanged();
            }
        });

        //recyclerView = findViewById(R.id.recyclerView);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setAdapter(fotosAdapter);

        //recyclerView.setOnClickListener(new FotosAdapter());

        //--------- Capturamos la pulsacion sobre una de las fotos -----------------------
        /*recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    int position = recyclerView.getChildAdapterPosition(child);

                    SharedPreferences prefe = getSharedPreferences("datos",Context.MODE_PRIVATE); //Guardar con sharedpreferences los ids.
                    SharedPreferences.Editor editor = prefe.edit();
                    editor.putInt("id", hijos.get(position));
                    editor.commit();

                    Intent intentDeslizador = new Intent(GridFotos.this, Deslizador.class);
                    intentDeslizador.putExtra("id", hijos.get(position));
                    startActivity(intentDeslizador);

                    //Toast.makeText(GridFotos.this,"The Item Clicked is: " + hijos.get(position), Toast.LENGTH_SHORT).show();

                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) { }
        });*/
    }

//------------------------------------------- Cogemos Datos de la BBDD y crear Picasso ---------------------------------------

    public List<String> getFoto(){

        Folcloricas dbreadable = new Folcloricas(this, "Folcloricas", null, 1);
        final SQLiteDatabase db = dbreadable.getReadableDatabase();
        List<String> listado = new ArrayList<>();
        if (hasPermission()) {

            String[] valores = {"id", "nombre", "urlfoto", "descripcion"};
            Cursor cursor = db.query("folcloricas", valores, null, null, null, null, "id", null);
            int i = 0;

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        nombresdb.add(cursor.getString(1));
                        String pathfotos = cursor.getString(2);
                        String descrip = cursor.getString(3);
                        //Toast.makeText(this, pathfotos, Toast.LENGTH_SHORT).show();
                        listado.add(pathfotos);
                        int identificador = cursor.getInt(0);
                        hijos.put(i, identificador);
                        i = i + 1;
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
        db.close();
        return listado;
    }

//----------------------------------------------------- Menu ----------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Intent intentLogin = new Intent(GridFotos.this, LoginMain.class);
                startActivity(intentLogin);
                return true;
            case R.id.datosautomatico:
                Intent intent = new Intent(GridFotos.this, DatosAutomatico.class);
                startActivity(intent);
                return true;
            case R.id.action_salir:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//-------------------------------------------------------- Permisos --------------------------------------------------------------

    private boolean hasPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISION_READ_EXTERNAL_MEMORY);
            return false;
        } else{
            return true;
        }
    }

    /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode){
                case PERMISION_READ_EXTERNAL_MEMORY:
                    if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                        fotosdb.clear();
                        fotosdb.addAll(getImagesPath());
                        f .notifyDataSetChanged();
                    }
            }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        fotos.clear();
        fotos.addAll(getImagesPath());
        fotosAdapter.notifyDataSetChanged();
    }*/
}
