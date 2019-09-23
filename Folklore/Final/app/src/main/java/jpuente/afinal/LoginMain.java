package jpuente.afinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginMain extends Activity {

        private LoginControl controldelogin; //Creamos un objeto de nuestra clase ControlLogin
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_main);

            final int ID = 1;
            final Button bentrar = findViewById(R.id.botonentrar);

            controldelogin = findViewById(R.id.Cntrollogin); //Instanciamos el objeto
            controldelogin.setOnLoginListener(new OnLoginListener(){
                @Override
                public void onLogin(String usuario, String password){ // Sobreescribimos el método de la Interface
                    //se valida si coinciden los usuarios
                    if (usuario.equals("UCAM") && password.equals("1234")) {
                        Toast.makeText(LoginMain.this,"Acceso Concedido", Toast.LENGTH_SHORT).show();
                        bentrar.setEnabled(true);
                    }
                    else {
                        Toast.makeText(LoginMain.this,"Login Incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button bsalir= findViewById(R.id.botonsalir);
            bsalir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            bentrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginMain.this, Bbdd.class);
                    startActivity(intent);
                }
            });

            final Button btacerca = (Button)findViewById(R.id.botonacercade);
            btacerca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent acerca = new Intent(LoginMain.this, Acercade.class);
                    startActivity(acerca);
                }
            });
        }
    }