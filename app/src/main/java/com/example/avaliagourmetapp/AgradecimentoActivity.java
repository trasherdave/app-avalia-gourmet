package com.example.avaliagourmetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class AgradecimentoActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agradecimento);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        executarProgressBar();

    }//onCreate

    private void executarProgressBar() {
        Handler handler = new Handler();
        progressBar.setVisibility(View.VISIBLE);
        i = progressBar.getProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i < 100) {
                    i = i + 10;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(i);
                            if (i >= 100) {

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("salvo", "salvo");
                                startActivity(intent);

                            }//if
                        }//run
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }//catch
                }//while
            }//run
        }).start();//Thread
    }//progress

}//class