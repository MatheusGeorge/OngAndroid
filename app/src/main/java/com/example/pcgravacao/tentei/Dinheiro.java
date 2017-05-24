package com.example.pcgravacao.tentei;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Dinheiro extends Fragment {

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dinheiro, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.dinheiroWebView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        String  html = "<!DOCTYPE html>";
                    html += "<head><meta charset='utf-8'/></head>";
                    html += "<div class='container'>";
                            html += "<div class='row'>";
                                    html += "<form style='text-align: center;' action='https://pagseguro.uol.com.br/checkout/v2/donation.html?currency=BRL&receiverEmail=matheus.vs2015@hotmail.com' method='post'>";
                                    html += "<input type='hidden' name='currency' value='BRL'/>";
                                    html += "<input type='hidden' name='receiverEmail' value='matheus.vs2015@hotmail.com'/>";
                                    html += "<input type='hidden' name='iot' value='button'/>";
                                    html += "<input type='image' src='https://stc.pagseguro.uol.com.br/public/img/botoes/doacoes/120x53-doar-preto.gif' name='submit' alt='Pague com PagSeguro - é rápido, grátis e seguro!'/>";
                                    html += "</form>";
                            html += "</div>";
                        html += "</div>";
                    html += "</html>";
        mWebView.loadData(html, "text/html", null);
        return rootView;
    }

}




