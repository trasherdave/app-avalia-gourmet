package com.example.avaliagourmetapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avaliagourmetapp.model.Avaliacao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.avaliagourmetapp.MainActivity.arrayAvaliacoes;
import static com.example.avaliagourmetapp.MainActivity.dao;


public class AvaliacoesActivity extends AppCompatActivity {
    ListView listviewAvaliacoes;
    ArrayAdapter<String> listAdapter;
    private String mensagem = "";
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);

        //setando toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Minhas Avaliações");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listviewAvaliacoes = findViewById(R.id.listviewAvaliacoes);
        listviewAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Avaliacao avaliacao = (Avaliacao) adapterView.getItemAtPosition(i);
                mensagem = avaliacao.getDados();
                criarDialog(adapterView, i);

            }
        });

        inicializarLista();

        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setBackgroundColor(Color.parseColor("#EFA92A"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Método dao populando ArrayList
                ArrayList<String> lista = (ArrayList) dao.listar();
                if (lista.isEmpty()) {

                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Para compartilhar\nÉ preciso fazer uma avaliação antes!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#EFA92A"))
                            .setAction("Action", null).show();

                } else {

                    Snackbar.make(view, "Clique na avaliação que deseja compartilhar :)", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#2b961f"))
                            .setAction("Action", null).show();
                }//else
            }//onClick
        });

    }//onCreate

    //Botão home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected


    private void inicializarLista() {

        //Método dao populando ArrayList
        ArrayList<String> lista = (ArrayList) dao.listar();
        if (!lista.isEmpty()) {

            listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista);
            listviewAvaliacoes.setAdapter(listAdapter);

        } else {

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Você não possui nenhuma avaliação!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#EFA92A"))
                    .setAction("Action", null).show();

        }//else

    }//inicializarLista


    private void criarDialog(AdapterView<?> adapterView, int pos) {

        String[] opcao = new String[3];
        opcao[0] = "Compartilhar via WhatsApp";
        opcao[1] = "Compartilhar via Gmail";
        opcao[2] = "Deletar";


        AlertDialog.Builder builder = new AlertDialog.Builder(AvaliacoesActivity.this);
        builder
                .setTitle("Selecione o que deseja fazer")
                .setSingleChoiceItems(opcao, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        switch (i) {
                            default:
                                posicao = i;
                                break;
                        }
                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (posicao == 0) {

                            try {

                                Toast.makeText(AvaliacoesActivity.this, "" + mensagem, Toast.LENGTH_SHORT).show();
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, mensagem);
                                sendIntent.setType("text/plain");
                                sendIntent.setPackage("com.whatsapp");
                                startActivity(sendIntent);

                            } catch (Exception ex) {

                                Toast.makeText(AvaliacoesActivity.this, "Houve um erro!", Toast.LENGTH_SHORT).show();
                            }

                        }//if
                        if (posicao == 1) {

                            try {

                                Toast.makeText(AvaliacoesActivity.this, "" + mensagem, Toast.LENGTH_SHORT).show();

                                String email = "davidmanoel.007@gmail.com";
                                String subject = "Assunto";
                                String text = mensagem;

                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_EMAIL, email);
                                sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                                sendIntent.setType("text/plain");
                                sendIntent.setPackage("com.google.android.gm");
                                startActivity(sendIntent);

                            } catch (Exception ex) {

                                Toast.makeText(AvaliacoesActivity.this, "Houve um erro!", Toast.LENGTH_SHORT).show();
                            }//catch

                        }//if
                        if (posicao == 2) {

                            Avaliacao avaliacao = (Avaliacao) adapterView.getItemAtPosition(pos);
                            dao.excluir(avaliacao);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("deletar", "deletar");
                            startActivity(intent);


                        }//if

                    }//onClick
                })//setPositiveButton
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //não fazer nada

                    }
                });//setNegativeButton
        builder.show();

    }//criarDialog

}//class