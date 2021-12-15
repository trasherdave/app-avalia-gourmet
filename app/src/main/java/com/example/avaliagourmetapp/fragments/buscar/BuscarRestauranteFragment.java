package com.example.avaliagourmetapp.fragments.buscar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.avaliagourmetapp.R;
import com.google.android.material.snackbar.Snackbar;

import static com.example.avaliagourmetapp.MainActivity.arrayLocalizacoes;


public class BuscarRestauranteFragment extends Fragment {
    private EditText txtBusca;
    private Button btnMaps;
    public WebView mWebView;
    private String url = "";
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_buscar_restaurante, container, false);


        mWebView = (WebView) root.findViewById(R.id.webView);
        txtBusca = root.findViewById(R.id.txtBusca);
        btnMaps = root.findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                abrirMapa(view);
            }
        });


        // habilitando Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Forçar links e redirecionamentos para abrir no WebView em vez de em um navegador
        mWebView.setWebViewClient(new WebViewClient());


        return root;

    }

    private void abrirMapa(View view) {

        String busca = "";
        busca = txtBusca.getText().toString();

        if (!busca.isEmpty()) {

            mWebView.setVisibility(View.VISIBLE);

            if (arrayLocalizacoes.size() != 0) {

                url = "https://www.google.com/maps/search/?api=1&query=" + busca + "+" + arrayLocalizacoes.get(0);
                mWebView.loadUrl(url);
                txtBusca.setText("");


            } else {

                url = "https://www.google.com/maps/search/?api=1&query=" + busca + "+Palmas-TO";
                mWebView.loadUrl(url);
                txtBusca.setText("");
            }

        } else {

            Snackbar.make(view, "Por favor, preencha o campo!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#A10000"))
                    .setAction("Action", null).show();


        }
    }


}