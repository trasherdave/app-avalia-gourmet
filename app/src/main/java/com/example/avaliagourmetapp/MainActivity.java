package com.example.avaliagourmetapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.avaliagourmetapp.bd.Banco;
import com.example.avaliagourmetapp.dao.AvaliacaoDao;
import com.example.avaliagourmetapp.model.Avaliacao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> arrayLocalizacoes = new ArrayList<String>();
    public static ArrayList<String> arrayAvaliacoes = new ArrayList<String>();
    private static final String CANAL_ID = "2";  //ID do canal - notificação
    private static final int NOTIFICACAO_ID = 1;   //ID da notificação - notificação

    private Avaliacao avaliacao;
    public static AvaliacaoDao dao;
    private static Banco banco;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Banco db = Room.databaseBuilder(getApplicationContext(), Banco.class, "meu_banco").allowMainThreadQueries().build();
        dao = db.avaliacaoDao();

        Intent intent = getIntent();
        if (intent.getStringExtra("salvo") != null) {

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Salvo com sucesso!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#2b961f"))
                    .setAction("Action", null).show();

            gerarNotificacao();

        }
        if (intent.getStringExtra("localizacao") != null) {

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Sua localização foi adicionada!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#2b961f"))
                    .setAction("Action", null).show();

        }
        if (intent.getStringExtra("deletar") != null) {

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Sua avaliação foi deletada!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#2b961f"))
                    .setAction("Action", null).show();

        }


        //criando canal para notificação
        criarCanal();

        String localizacao = lerSharedPreferences();
        if (!localizacao.isEmpty()) {

            if (!arrayLocalizacoes.isEmpty()) {
                arrayLocalizacoes.remove(0);
            }
            arrayLocalizacoes.add(localizacao);

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setBackgroundColor(Color.parseColor("#EFA92A"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CreditosActivity.class);
                startActivity(intent);

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.map, R.id.nav_map, R.id.nav_local, R.id.ac_avaliacoes, R.id.ac_avaliar)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void criarCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  //verifica as versões do Android
            CharSequence nome = "canal1"; //define o nome
            String descricao = "descrição do canal 1"; //define a descrição
            int importancia = NotificationManager.IMPORTANCE_DEFAULT; //define a prioridade
            NotificationChannel canal = new NotificationChannel(CANAL_ID, nome, importancia); //cria o objeto referente ao canal, passand o ID do canal, nome e prioridade.
            canal.setDescription(descricao);//registrando o canal no sistema
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(canal);
        }
    }//

    public void gerarNotificacao() {
        //quando a notificação for tocada, será aberta uma outra Activity. A notificação responde ao toque do usuário.
        Intent i = new Intent(this, AvaliacoesActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Bitmap bitmap = BitmapFactory
                //criando um Bitmap com a imagem contida na pasta drawable
                .decodeResource(this.getResources(), R.mipmap.ic_avalia_gourmet);
        //configurando a estrutura da notificação
        Notification builder = new NotificationCompat
                .Builder(this, CANAL_ID)
                .setSmallIcon(R.drawable.chat)
                .setContentTitle("Sua avaliação foi criada!")
                .setContentText("Que tal compartilhar com seus amigos?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pi)  //Passando o PendingIntent
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(NOTIFICACAO_ID, builder);

    }//gerar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.creditos:

                Intent intent = new Intent(this, CreditosActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//onOptionsItemSelected

    private void localizacao() {

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void nova(View view) {

        Intent intent = new Intent(this, AvaliarActivity.class);
        startActivity(intent);

    }

    public void avalaicoes(View view) {

        //Método listar populando ArrayList
        ArrayList<String> lista = (ArrayList) dao.listar();
        if (lista.isEmpty()) {

            Snackbar.make(view, "Você ainda não possui avaliações!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#EFA92A"))
                    .setAction("Action", null).show();

        } else {

            Intent intent = new Intent(this, AvaliacoesActivity.class);
            startActivity(intent);

        }//else

    }//avalaicoes

    private String lerSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("dados", MODE_PRIVATE);
        String local = "";
        if (sharedPreferences.contains("local")) {

            local = sharedPreferences.getString("local", "");

        } else {

            // Toast.makeText(MainActivity.this, "Erro no arquivo", Toast.LENGTH_SHORT).show();

        }
        return local;

    }//lerSharedPreferences

}//class
