package ar.sourcesistemas.empanada.roboarmstream.Acitivty;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.sourcesistemas.empanada.roboarmstream.R;


public class StreamActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String IZQUIERDA = "Izquierda";
    public static final String DERECHA = "Derecha";

    public static final String ARRIBA = "CAMA%20ARRIBA";
    public static final String ABAJO = "CAMA%20ABAJO";

    public static final String PINZA_ABRIR = "Abrir%20pinza";
    public static final String PINZA_CERRAR = "cerrar%20pinza";

    public static final String MUNIECA_ARRIBA = "munieca%20arriba";
    public static final String MUNIECA_ABAJO = "munieca%20abajo";

    public static final String ADELANTE = "Adelante";
    public static final String ATRAS = "Atras";

    public static final String ENCENDER_LED = "Encender%20led";

    public static final String PRECISION_MAS = "Precicion%20mas";
    public static final String PRECISION_MENOS = "Precicion%20menos";

    public static final String URI = "http://93.188.167.193:8080/mover?movimiento=";


    private Button morePress;
    private Button lessPress;
    private Button adelante;
    private Button atras;
    private Button cerrarPinza;
    private Button abrirPinza;
    private Button subir;
    private Button bajar;
    private Button izquierda;
    private Button derecha;
    private Button muñecaUp;
    private Button muñecaDown;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_stream);

        morePress = (Button) findViewById(R.id.button_morePres);
        morePress.setOnClickListener(this);

        lessPress = (Button) findViewById(R.id.button_lessPres);
        lessPress.setOnClickListener(this);

        adelante = (Button) findViewById(R.id.button_adelante);
        adelante.setOnClickListener(this);

        atras = (Button) findViewById(R.id.button_atras);
        atras.setOnClickListener(this);

        cerrarPinza= (Button) findViewById(R.id.button_cerrarPinza);
        cerrarPinza.setOnClickListener(this);

        abrirPinza = (Button) findViewById(R.id.button_abrirPinza);
        abrirPinza.setOnClickListener(this);

        subir = (Button) findViewById(R.id.button_subir);
        subir.setOnClickListener(this);

        bajar = (Button) findViewById(R.id.button_bajar);
        bajar.setOnClickListener(this);

        izquierda = (Button) findViewById(R.id.button_izq);
        izquierda.setOnClickListener(this);

        derecha = (Button) findViewById(R.id.button_der);
        derecha.setOnClickListener(this);

        muñecaUp = (Button) findViewById(R.id.button_muñecaUp);
        muñecaUp.setOnClickListener(this);

        muñecaDown = (Button) findViewById(R.id.button_muñecaDown);
        muñecaDown.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_morePres:
                new Lichterkette(PRECISION_MAS).execute();
                Toast.makeText(StreamActivity.this, URI+PRECISION_MAS, Toast.LENGTH_LONG).show();
                break;
            case R.id.button_lessPres:
                new Lichterkette(PRECISION_MENOS).execute();
                Toast.makeText(StreamActivity.this, URI+PRECISION_MENOS, Toast.LENGTH_LONG).show();
                break;
            case R.id.button_adelante:
                new Lichterkette(ADELANTE).execute();
                Toast.makeText(StreamActivity.this, URI+ADELANTE, Toast.LENGTH_LONG).show();
                break;
            case R.id.button_atras:
                new Lichterkette(ATRAS).execute();
                break;
            case R.id.button_cerrarPinza:
                new Lichterkette(PINZA_CERRAR).execute();
                break;
            case R.id.button_abrirPinza:
                new Lichterkette(PINZA_ABRIR).execute();
                break;
            case R.id.button_subir:
                new Lichterkette(ARRIBA).execute();
                break;
            case R.id.button_bajar:
                new Lichterkette(ABAJO).execute();
                break;
            case R.id.button_izq:
                new Lichterkette(IZQUIERDA).execute();
                break;
            case R.id.button_der:
                new Lichterkette(DERECHA).execute();
                break;
            case R.id.button_muñecaUp:
                new Lichterkette(MUNIECA_ARRIBA).execute();
                break;
            case R.id.button_muñecaDown:
                new Lichterkette(MUNIECA_ABAJO).execute();
                break;

        }
    }

    class Lichterkette extends AsyncTask<String,Void,String>
    {
        private String command;

        public Lichterkette(String command){
            this.command = command;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb=null;
            BufferedReader reader=null;
            String serverResponse=null;
            try {

                URL url = new URL(URI + command);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setConnectTimeout(1000);
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                }

                connection.disconnect();
                if (sb!=null)
                    serverResponse=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //All your UI operation can be performed here
            System.out.println(s);
        }
    }
}
