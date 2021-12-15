package com.example.avaliagourmetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.avaliagourmetapp.model.Avaliacao;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;

import static com.example.avaliagourmetapp.MainActivity.arrayAvaliacoes;
import static com.example.avaliagourmetapp.MainActivity.dao;

public class AvaliarActivity extends AppCompatActivity {
    EditText editNome;
    String nome = "";
    String atendimento = "";
    String comida = "";
    String ambiente = "";
    private Avaliacao avaliacao;
    public static ArrayList<String> array = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar);

        editNome = findViewById(R.id.editNome);


        //setando toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Avaliação");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final View imageButton = findViewById(R.id.a1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#2b961f"), PorterDuff.Mode.SRC_ATOP);

                atendimento = "Ótimo";

            }
        });
        final View imageButton2 = findViewById(R.id.a2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

                atendimento = "Bom";

            }
        });
        final View imageButton3 = findViewById(R.id.a3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#D50000"), PorterDuff.Mode.SRC_ATOP);

                atendimento = "Ruim";

            }
        });

        final View imageButton1 = findViewById(R.id.c1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#2b961f"), PorterDuff.Mode.SRC_ATOP);

                comida = "Ótima";

            }
        });
        final View imageButton22 = findViewById(R.id.c2);
        imageButton22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

                comida = "Boa";

            }
        });
        final View imageButton33 = findViewById(R.id.c3);
        imageButton33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#D50000"), PorterDuff.Mode.SRC_ATOP);

                comida = "Ruim";

            }
        });

        final View imageButton11 = findViewById(R.id.am1);
        imageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#2b961f"), PorterDuff.Mode.SRC_ATOP);

                ambiente = "Ótimo";

            }
        });
        final View imageButton222 = findViewById(R.id.am2);
        imageButton222.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

                ambiente = "Bom";

            }
        });
        final View imageButton333 = findViewById(R.id.am3);
        imageButton333.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView v = (ImageView) view;
                v.getDrawable().setColorFilter(Color.parseColor("#D50000"), PorterDuff.Mode.SRC_ATOP);

                ambiente = "Ruim";

            }
        });

    }

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

    public void salvarAvaliacao(View view) {

        nome = editNome.getText().toString();

        if (nome.isEmpty()) {

            Snackbar.make(view, "Por gentileza, preencha o nome :)", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#A10000"))
                    .setAction("Action", null).show();

        } else if (!atendimento.isEmpty() && !comida.isEmpty() && !ambiente.isEmpty()) {
            arrayAvaliacoes.add("Nome: " + nome + "\nAtendimento: " + atendimento +
                    "\nComida: " + comida + "\nAmbiente: " + ambiente);

            if (avaliacao == null) {
                avaliacao = new Avaliacao();
                array = arrayAvaliacoes;
                int posicaoFinal = array.size() - 1;

                avaliacao.setDados(String.valueOf(array.get(posicaoFinal)).replace("[", "").replace("]", ""));

                long retorno = dao.adicionar(avaliacao);
                if (retorno != -1) {

                    // Toast.makeText(getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), AgradecimentoActivity.class);
                    startActivity(intent);
                    resetarAvaliacao();

                    avaliacao = null;

                } else {
                    Toast.makeText(getApplicationContext(), "Não salvo", Toast.LENGTH_SHORT).show();
                }//else

            }//if


        } else {

            Snackbar.make(view, "Por gentileza, preencha todos os campos :)", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#A10000"))
                    .setAction("Action", null).show();

        }//else

    }//salvarAvaliacao


    private void resetarAvaliacao() {

        final View imageButton = findViewById(R.id.a1);
        ImageView v = (ImageView) imageButton;
        v.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton2 = findViewById(R.id.a2);
        ImageView v1 = (ImageView) imageButton2;
        v1.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton3 = findViewById(R.id.a3);
        ImageView v2 = (ImageView) imageButton3;
        v2.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton1 = findViewById(R.id.c1);
        ImageView v3 = (ImageView) imageButton1;
        v3.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton22 = findViewById(R.id.c2);
        ImageView v4 = (ImageView) imageButton22;
        v4.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton33 = findViewById(R.id.c3);
        ImageView v5 = (ImageView) imageButton33;
        v5.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton11 = findViewById(R.id.am1);
        ImageView v6 = (ImageView) imageButton11;
        v6.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton222 = findViewById(R.id.am2);
        ImageView v7 = (ImageView) imageButton222;
        v7.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        final View imageButton333 = findViewById(R.id.am3);
        ImageView v8 = (ImageView) imageButton333;
        v8.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

    }//resetarAvaliacao

}//class