package com.example.pcgravacao.tentei;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class EventoArrayAdapter extends ArrayAdapter <EventoLista>{

    private static class ViewHolder{
        TextView tituloTextView;
        TextView dataTextView;
    }

    public EventoArrayAdapter (Context context, List<EventoLista> forecast){
        super (context, -1, forecast);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventoLista eve = getItem (position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.evento_list, parent, false);
            viewHolder.tituloTextView =  (TextView) convertView.findViewById(R.id.tituloTextView);
            viewHolder.dataTextView = (TextView) convertView.findViewById(R.id.dataTextView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Context context = getContext();
        viewHolder.tituloTextView.setText(context.getString(R.string.titulo_evento, eve.titulo));
        viewHolder.dataTextView.setText(context.getString(R.string.data_evento, eve.data));
        return convertView;
    }
}
