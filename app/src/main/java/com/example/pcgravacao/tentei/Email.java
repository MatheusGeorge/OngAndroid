package com.example.pcgravacao.tentei;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Email extends Fragment {

    private ImageButton ongImageButton;
    private EditText mensagem;
    private EditText nome;
    private EditText email;
    private EditText tel;
    private EditText cel;
    private EditText empresa;
    private EditText site;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.email, container, false);
        ongImageButton = (ImageButton) rootView.findViewById(R.id.ongImageButton);
        ongImageButton.setOnClickListener(imgBtnListener);
        mensagem = (EditText) rootView.findViewById(R.id.mensagemEditText);
        nome = (EditText) rootView.findViewById(R.id.nomeEditText);
        email = (EditText) rootView.findViewById(R.id.emailEditText);
        tel = (EditText) rootView.findViewById(R.id.telefoneEditText);
        cel = (EditText) rootView.findViewById(R.id.celularEditText);
        empresa = (EditText) rootView.findViewById(R.id.empresaEditText);
        site = (EditText) rootView.findViewById(R.id.siteEditText);
        return rootView;
    }

    private View.OnClickListener imgBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"matheus.vs2015@hotmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Padrinho");
            if(nome.getText().length() == 0 || email.getText().length() == 0 || tel.getText().length() == 0 || cel.getText().length() == 0 || empresa.getText().length() == 0
                    || site.getText().length() == 0 || mensagem.getText().length() == 0){
                Toast.makeText(getContext(), getString(R.string.aviso_email), Toast.LENGTH_SHORT).show();
            }
            else if(!isValidEmail(email.getText().toString())){
                Toast.makeText(getContext(), getString(R.string.email_invalido), Toast.LENGTH_SHORT).show();
            }
            else if(!isValidSite(site.getText().toString())){
                Toast.makeText(getContext(), getString(R.string.site_invalido), Toast.LENGTH_SHORT).show();
            }
            else{
                i.putExtra(Intent.EXTRA_TEXT   ,"info: \n" + getString(R.string.nome) + ": " + nome.getText().toString() + "\n"
                        + getString(R.string.email) + ": " + email.getText().toString() + "\n"
                        + getString(R.string.telefone) + ": " + tel.getText().toString() + "\n"
                        + getString(R.string.celular) + ": " + cel.getText().toString() + "\n"
                        + getString(R.string.empresa) + ": " + empresa.getText().toString() + "\n"
                        + getString(R.string.site) + ": " + site.getText().toString() + "\n"
                        + getString(R.string.mensagem) + ": " + mensagem.getText().toString() + "\n");
                try {
                    startActivity(Intent.createChooser(i, getString(R.string.enviar_email)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), getString(R.string.erro_email), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidSite(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.WEB_URL.matcher(target).matches();
        }
    }
}
