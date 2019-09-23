package jpuente.afinal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginControl extends LinearLayout {
	private EditText textousuario;
	private EditText textopassword;
	private Button botonlogin;
	private OnLoginListener onloginlistener;

	public LoginControl(Context context){ // Constructores propios de la Clase LinearLayout
		super(context);
		inicializar();
	}
	public LoginControl(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    inicializar();
	}
	
	private void asignarEventos(){
		botonlogin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				onloginlistener.onLogin(textousuario.getText().toString(), textopassword.getText().toString());
			}
		});
	}
	
	private void inicializar(){
		//usamos el layout login_control
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li=(LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.login_control, this, true);

		//obtenemos referencias
		textousuario= (EditText)findViewById(R.id.editText1);
		textopassword=(EditText)findViewById(R.id.editText2);
		botonlogin=(Button)findViewById(R.id.button1);

		//asociamos eventos
		asignarEventos();
	}

	public void setOnLoginListener(OnLoginListener oll){
		onloginlistener=oll;
	}
}
