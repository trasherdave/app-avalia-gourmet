package com.example.avaliagourmetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.avaliagourmetapp.viacep.ViaCEP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

import static com.example.avaliagourmetapp.MainActivity.arrayLocalizacoes;


public class CepActivity extends AppCompatActivity {
    private Button btnBuscar, btnConfirmar;
    private EditText txtCEP;
    private EditText txtLogradouro;
    private EditText txtBairro;
    private EditText txtLocalidade;
    private EditText txtUf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        //Setando toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("API ViaCep");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // referência
        this.btnBuscar = (Button) findViewById(R.id.btnBuscar);
        this.btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        this.txtCEP = (EditText) findViewById(R.id.txtCEP);
        this.txtLogradouro = (EditText) findViewById(R.id.txtLogradouro);
        this.txtBairro = (EditText) findViewById(R.id.txtBairro);
        this.txtLocalidade = (EditText) findViewById(R.id.txtLocalidade);
        this.txtUf = (EditText) findViewById(R.id.txtUf);

        // cria a máscara
        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", this.txtCEP);

        // adiciona a máscara no objeto
        this.txtCEP.addTextChangedListener(maskCEP);


    }//onCreate

    //botão home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected


    public void buscarCEP(View view) {
        // evento para buscar um cep
        if (view == this.btnBuscar) {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

                // limpar editText
                this.txtBairro.setText("");
                this.txtLocalidade.setText("");
                this.txtLogradouro.setText("");
                this.txtUf.setText("");

                // cep
                String cep = this.txtCEP.getText().toString();

                // verifica se o CEP é válido
                Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
                Matcher matcher = pattern.matcher(cep);

                if (matcher.find()) {
                    new CepActivity.DownloadCEPTask().execute(cep);

                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Aviso!")
                            .setMessage("Favor informar um CEP válido!")
                            .setPositiveButton(R.string.msgOk, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // nada
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }//else
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Sem Internet!")
                        .setMessage("Não tem nenhuma conexão de rede disponível!")
                        .setPositiveButton(R.string.msgOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // nada
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }//else
        }//if
    }//onClick

    public void confirmarEndereco(View view) {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("localizacao", "localizacao");
        startActivity(i);

    }//confirmar

    private class DownloadCEPTask extends AsyncTask<String, Void, ViaCEP> {
        @Override
        protected ViaCEP doInBackground(String... ceps) {
            ViaCEP vCep = null;

            try {
                vCep = new ViaCEP(ceps[0]);
            } finally {
                return vCep;
            }
        }//doInBackground

        @Override
        protected void onPostExecute(ViaCEP result) {
            if (result != null) {
                txtBairro.setText(result.getBairro());
                txtLocalidade.setText(result.getLocalidade());
                txtLogradouro.setText(result.getLogradouro());
                txtUf.setText(result.getUf());

                if (!arrayLocalizacoes.isEmpty()) {
                    arrayLocalizacoes.remove(0);
                }

                arrayLocalizacoes.add(txtLocalidade.getText().toString() + "-" + txtUf.getText().toString());
                gravarLocalizacao(txtLocalidade.getText().toString() + "-" + txtUf.getText().toString());

                btnConfirmar.setVisibility(View.VISIBLE);
            }
        }//onPostExecute

        private void gravarLocalizacao(String local) {
            SharedPreferences sharedPreferences = getSharedPreferences("dados", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("local", local);
            boolean resposta = editor.commit();
            if (resposta) {

                //Toast.makeText(getApplicationContext(), "Salvo no shared preferences", Toast.LENGTH_SHORT).show();

            } else {

                // Toast.makeText(getApplicationContext(), "Não salvo", Toast.LENGTH_SHORT).show();

            }
        }//gravarLocalizacao

    }//DownloadCEPTask

}//class