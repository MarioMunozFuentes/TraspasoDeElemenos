package com.example.traspasoelemenos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> elementosSuperiores = new ArrayList<>();
        ArrayList<String> elementosInferiores = new ArrayList<>();

        inicializaElementosListado(elementosInferiores, elementosSuperiores);

        MiAdaptador adaptadorSuperior = getAdaptador (R.id.rvSuperior, elementosSuperiores);
        MiAdaptador adaptadorInferior = getAdaptador (R.id.rvInferior, elementosInferiores);

        // comportamiento del botón de bajar elementos
        ImageButton bBaja = (ImageButton) findViewById(R.id.bBaja);
        bBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkedHashMap<TextView, Integer> candidatosTraspaso = adaptadorSuperior.getCandidatosTraspaso();

                for (TextView clave:candidatosTraspaso.keySet()) {
                    clave.setBackgroundColor(Color.WHITE);

                    elementosInferiores.add(clave.getText().toString()); // añado por el final
                    adaptadorInferior.notifyItemInserted(elementosInferiores.size()-1);
                    adaptadorInferior.notifyItemRangeChanged(0, elementosInferiores.size());
                    // equivalente a:
                    // adaptadorInferior.notifyItemRangeChanged(0, adaptadorInferior.getItemCount());

                    elementosSuperiores.remove(clave.getText().toString());  // elimino donde esté. También puedo usar candidatosTraspaso.get(clave)
                    adaptadorSuperior.notifyItemRemoved (candidatosTraspaso.get(clave));
                    adaptadorSuperior.notifyItemRangeChanged(0, elementosSuperiores.size());
                    // equivalente a:
                    // adaptadorSuperior.notifyItemRangeChanged(0, adaptadorSuperior.getItemCount());
                }

                candidatosTraspaso.clear();
            }
        });
    }

    public void inicializaElementosListado(ArrayList<String> elementosInferiores, ArrayList<String> elementosSuperiores) {
        int i = 0;
        elementosSuperiores.add(i++ + " Amparo");
        elementosSuperiores.add(i++ + " Manuel");
        elementosSuperiores.add(i++ + " Rosa");
        elementosSuperiores.add(i++ + " Davinia");
        elementosSuperiores.add(i++ + " Ángel");
        elementosSuperiores.add(i++ + " Óscar");
        elementosSuperiores.add(i++ + " Nerea");
        elementosSuperiores.add(i++ + " Silvia");
        elementosSuperiores.add(i++ + " Estrella");
        elementosSuperiores.add(i++ + " José");
        elementosSuperiores.add(i++ + " Francisco");
        elementosSuperiores.add(i++ + " Daniela");
        elementosSuperiores.add(i++ + " Triana");
    }

    public MiAdaptador getAdaptador (int idRecyclerView, ArrayList<String> lista){
        RecyclerView rv = findViewById(idRecyclerView);
        LinearLayoutManager managerLayout = new LinearLayoutManager(this);
        rv.setLayoutManager(managerLayout);
        MiAdaptador adaptador = new MiAdaptador(this, lista);
        rv.setAdapter(adaptador);

        DividerItemDecoration decorador = new DividerItemDecoration(
                rv.getContext(), managerLayout.getOrientation());
        rv.addItemDecoration(decorador);
        return adaptador;
    }
}