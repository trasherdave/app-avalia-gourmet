package com.example.avaliagourmetapp.fragments.local;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.avaliagourmetapp.CepActivity;
import com.example.avaliagourmetapp.MapsActivity;
import com.example.avaliagourmetapp.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.avaliagourmetapp.MainActivity.arrayLocalizacoes;

public class LocalFragment extends Fragment {
    ListView Localizacao;
    ArrayAdapter<String> listAdapter;
    Button btnCEP, btnMaps;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_local, container, false);
        Localizacao = root.findViewById(R.id.listviewCEP);
        Localizacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                criarDialogDel(i);

            }
        });
        btnMaps = root.findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                abrirLocalizacao(view);

            }
        });
        btnCEP = root.findViewById(R.id.btnCEP);
        btnCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                abrirCep(view);

            }
        });

        preencherAdapter();


        return root;
    }

    public void abrirLocalizacao(View view) {

        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivity(intent);

    }


    public void abrirCep(View view) {

        Intent intent = new Intent(getActivity(), CepActivity.class);
        startActivity(intent);

    }


    private void preencherAdapter() {

        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayLocalizacoes);
        Localizacao.setAdapter(listAdapter);

    }//inicializarLista

    private void criarDialogDel(int item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Deseja deletar essa localização?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayLocalizacoes.remove(item);
                        preencherAdapter();
                        Toast.makeText(getContext(), "Localização deletada", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //não fazer nada :)

                    }
                });
        builder.show();
    }//criarDialogDel

}