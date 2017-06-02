package com.example.pcgravacao.tentei;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemArrayAdapter extends ArrayAdapter<ItemLista> {

    private static class ViewHolder {
        TextView tituloTextView;
        ImageView itemImageView;
    }

    private Map<String, Bitmap> bitmaps = new HashMap<>();
    public ItemArrayAdapter(Context context, List<ItemLista> forecast) {
        super(context, -1, forecast);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemLista item = getItem(position);
        ItemArrayAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ItemArrayAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            viewHolder.tituloTextView = (TextView) convertView.findViewById(R.id.tituloTextView);
            viewHolder.itemImageView = (ImageView) convertView.findViewById(R.id.itemImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemArrayAdapter.ViewHolder) convertView.getTag();
        }
        if (bitmaps.containsKey(item.imagem)){
            viewHolder.itemImageView.setImageBitmap(bitmaps.get(item.imagem));
        }
        else{ new LoadImageTask (viewHolder.itemImageView).execute (item.imagem);
        }
        Context context = getContext();
        viewHolder.tituloTextView.setText(context.getString(R.string.titulo_item_lista, item.titulo));
        return convertView;
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;
        public LoadImageTask (ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                try(InputStream inputStream = connection.getInputStream ()){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.put (params[0], bitmap);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally{
                connection.disconnect();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

    }

}
