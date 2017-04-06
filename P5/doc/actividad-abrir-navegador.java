package dssbcs.juegopreguntas;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/**
 * Created by Manuel on 17/12/2015.
 */
public class Presentacion extends Activity implements View.OnClickListener{
    private Button btn_plop;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.presentacion);

        this.btn_abrirnavegador = (Button) this.findViewById(R.id.btn_plop);
        this.btn_plop.setBackgroundResource(R.drawable.floppy04);//se le pone un fondo de imagen
        this.btn_plop.setOnClickListener(this);

    }//onCreate

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_plop) {
            Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            myWebLink.setData(Uri.parse("http://INdicar la direccion de la pagina que va a abrir el navegador"));
            startActivity(myWebLink);
        }//if
    }//onClick()
}
