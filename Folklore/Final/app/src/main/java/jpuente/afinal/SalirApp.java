package jpuente.afinal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SalirApp extends Activity implements View.OnTouchListener {

    private float presure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salirapp);

        TextView texto = findViewById(R.id.TextView);
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.animacion);
        texto.startAnimation(animacion);

        ImageView imagen= findViewById(R.id.imageView1);
        Animation animacion2 =AnimationUtils.loadAnimation(this, R.anim.animacion2);
        imagen.startAnimation(animacion2);

        View vista = findViewById(R.id.view);

        vista.setOnTouchListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                presure = event.getPressure();
                if (presure != 0.0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Bye Bye", Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
                break;
        }
        return true;
    }
}

