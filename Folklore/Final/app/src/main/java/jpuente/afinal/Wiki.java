package jpuente.afinal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class Wiki extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam2;
    private TextView texto;
    private TextView titulo;
    private InputStreamReader ficherolectura;		//Para leer el fichero

    private OnFragmentInteractionListener mListener;

    public Wiki() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Wiki newInstance(String param1, String param2) {
        Wiki fragment = new Wiki();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate el layout en este fragment
        View view = inflater.inflate(R.layout.wiki, container, false);
        texto = view.findViewById(R.id.wiki);
        titulo = view.findViewById(R.id.titulo);
        SharedPreferences prefe = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        int condicion = prefe.getInt("id",400);
        //texto.setText("Atento a lo que es esto " + condicion);

        Folcloricas dbreadable = new Folcloricas(getContext(), "Folcloricas", null, 1);
        final SQLiteDatabase db = dbreadable.getReadableDatabase();

        String[] valores = {"id", "nombre", "descripcion"};
        Cursor cursor = db.query("folcloricas", valores, "id = " + condicion, null, null, null, null);
        cursor.moveToFirst();
        String ficheroWiki = cursor.getString(2);

        titulo.setText("Wiki de " + cursor.getString(1));

    //********************************************** Filtros *******************************

        String filtro = "content";
        String carpeta = "folcloricas";

        //*************************** 8 horas para conseguir resolver este problema de URI ****************
        //      Los Fragments no tienen la funcion getContextResolver que necesitaba. Hay que crearla en el Activity que
        //      lanza el fragment y ahora desde el fragment ya podríamos acceder a ella.
        //
        if (ficheroWiki.contains(filtro)) {
            //Toast.makeText(getActivity(), ficheroWiki, Toast.LENGTH_LONG).show();
            Context applicationContext = Deslizador.getContextOfApplication();
            ContentResolver cr = applicationContext.getContentResolver();

            applicationContext.getContentResolver();
            try {
                InputStream is = cr.openInputStream(Uri.parse(ficheroWiki));
                StringBuilder stringBuilder = new StringBuilder();
                String cadena;
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroWiki)));
                while((cadena = buffer.readLine())!=null){
                    stringBuilder.append(cadena).append("\n");
                }
                texto.setText(stringBuilder);
                buffer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (ficheroWiki.contains(carpeta)){
            File fichero = new File(Environment.getExternalStorageDirectory(), ficheroWiki);
            //Toast.makeText(getActivity(), fichero.toString(),Toast.LENGTH_LONG).show();
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
            textoFichero(fichero.toString());
        }
        cursor.close();
        db.close();
        return view;
    }

    // ********** Como extraer el texto del fichero con la siguiente funcion. Para el caso de Content
    //          no he podido usar una funcion extra.
    public void textoFichero(String ficheroWiki){
        StringBuilder stringBuilder = new StringBuilder();
        String cadena;

        try{
            ficherolectura = new InputStreamReader(new FileInputStream(ficheroWiki));
            BufferedReader br = new BufferedReader(ficherolectura);
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroWiki)));
            while((cadena = br.readLine())!=null){
                stringBuilder.append(cadena).append("\n");
            }
            br.close();
        }catch (Exception ex){
            Log.e("Fichero Erróneo", "Error al leer fichero");
        }
        texto.setText(stringBuilder);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
