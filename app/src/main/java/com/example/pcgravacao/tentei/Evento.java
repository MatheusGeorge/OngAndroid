package com.example.pcgravacao.tentei;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Evento extends Fragment {

    private List<EventoLista> eventos = new ArrayList<>();
    private EditText teste;

    public Evento() {}

    public static Evento newInstance(String param1, String param2) {
        Evento fragment = new Evento();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.evento, container, false);
        teste = (EditText) rootView.findViewById(R.id.testeEditText);
        URL url = null;
        try{
            url = new URL("http://hdf-api.herokuapp.com/api/eventos");

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (url != null){
            GetEvents getEvents= new GetEvents();
            getEvents.execute(url);
        }
        else{
            Toast.makeText(getContext(), "Cannot create a URL.", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    private class GetEvents extends AsyncTask<URL, Void, JSONObject> {
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        Toast.makeText(getContext(), "foi", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Errors while trying to get the donates.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    return new JSONObject(builder.toString());
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Errors while trying to connect with server of donates.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        protected void onPostExecute(JSONObject don) {
            convertJSONToArrayList(don);
            Log.d("Eventos Convertidos", eventos.toString());
            teste.setText("");
            teste.setText(formatarEventos(Evento.this.eventos));
        }

        private void convertJSONToArrayList(JSONObject forecast) {
            Evento.this.eventos.clear();
            try {
                JSONArray list = forecast.getJSONArray("eventos");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject line = list.getJSONObject(i);
                    Evento.this.eventos.add(new EventoLista(line.getString("data"), line.getString("titulo")));
                }
                Log.d("ConvertJSONToArray", Evento.this.eventos.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        protected String formatarEventos(List<EventoLista> eve) {
            String ret = "";
            if ((eve != null) && (eve.size() > 0)) {
                for (int i = 0; i < eve.size(); i++) {
                    ret += "Data: " + eve.get(i).getData() + "\n" +
                            "Título : " + eve.get(i).getTitulo() + "\n" +
                            "===============================\n";
                }
            }
            return ret;
        }
    }
}

