package com.example.pcgravacao.tentei;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

public class Item extends Fragment {

    private List<ItemLista> itens = new ArrayList<>();
    private ItemArrayAdapter itemArrayAdapter;
    private ListView itemListView;

    public Item() {}

    public static Item newInstance(String param1, String param2) {
        Item fragment = new Item();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item, container, false);
        itemListView = (ListView) rootView.findViewById(R.id.itemListView);
        itemArrayAdapter = new ItemArrayAdapter(getContext(),itens);
        itemListView.setAdapter(itemArrayAdapter);
        URL url = null;
        try{
            url = new URL("http://hdf-api.herokuapp.com/api/itens");

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (url != null){
            Item.GetItens getItens= new Item.GetItens();
            getItens.execute(url);
        }
        else{
            Toast.makeText(getContext(), "Cannot create a URL.", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    private class GetItens extends AsyncTask<URL, Void, JSONObject> {
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
            itemArrayAdapter.notifyDataSetChanged();
            itemListView.smoothScrollToPosition(0);
        }

        private void convertJSONToArrayList(JSONObject forecast) {
            itens.clear();
            try {
                JSONArray list = forecast.getJSONArray("itens");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject line = list.getJSONObject(i);
                    itens.add(new ItemLista(line.getString("nome"), line.getString("imagem")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}