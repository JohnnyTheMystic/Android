package jpuente.afinal;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import java.io.File;
import java.util.List;

public class FotosAdapter extends BaseAdapter {
        //RecyclerView.Adapter<FotosAdapter.ViewHolder>

    private Context context;
    private List<String> fotosdb;
    private List<String> nombresdb;
    private int layout;

    public FotosAdapter(Context context, List<String> fotos, List<String> nombres, int layout){
        this.context = context;
        this.fotosdb = fotos;
        this.nombresdb = nombres;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return fotosdb.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //    He tenido que aplicar un doble filtro, para identificar las imagenes que se han guardado automaticamente usando la carpeta folcloricas
        //    de los registros que se han introducido desde la aplicacion manualmente, que contienen Content.

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.grid_fotos, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.name);

        String direccion = fotosdb.get(position);
        String filtro = "content";
        String carpeta = "folcloricas";

        if (direccion.contains(filtro)) {
            Picasso.get()
                    .load(fotosdb.get(position))
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .resize(400, 500)
                    .into(imageView);

        }else if (direccion.contains(carpeta)){
            File fichero = new File(Environment.getExternalStorageDirectory(),fotosdb.get(position));
            //File fichero = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fotosdb.get(position));
            Picasso.get()
                    .load(fichero)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .resize(400, 500)
                    .into(imageView);
        }

        nameTextView.setText(nombresdb.get(position));

        return convertView;
    }







    /*public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(v);
    }
    */
    /*@Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        //    He tenido que aplicar un doble filtro, para identificar las imagenes que se han guardado automaticamente usando la carpeta folcloricas
        //    de los registros que se han introducido desde la aplicacion manualmente, que contienen Content.

        String direccion = fotosdb.get(position);
        String filtro = "content";
        String carpeta = "folcloricas";

        if (direccion.contains(filtro)) {
            System.out.println("Encontrado");
            Picasso.get().load(fotosdb.get(position)).fit().placeholder(R.drawable.spinner).into(holder.imagen, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }else if (direccion.contains(carpeta)){
            //File fichero = new File(Environment.getExternalStorageDirectory(), fotosdb.get(position));

            File fichero = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fotosdb.get(position));
            Picasso.get().load(fichero).fit().placeholder(R.drawable.spinner).into(holder.imagen, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    */
    /*@Override
    public int getItemCount() { return fotosdb.size(); }
    */

/*
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imagen = itemView.findViewById(R.id.imageView);
        }
    }*/
}
