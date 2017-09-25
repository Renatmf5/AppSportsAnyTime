package com.project.apptcc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import Objetos.Endereco;
import Objetos.Jogo;
import Objetos.PosicaoDisponivelJogo;
import Objetos.Time;
import Services.*;
import com.thomashaertel.widget.MultiSpinner;

import org.jdeferred.DeferredManager;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.impl.DefaultDeferredManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Date;

public class CadastroJogo extends Activity {
    private CadastroJogo activity = this;
    private Endereco endereco;
    private Jogo jogo;
    private Time time;
    private MultiSpinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogo);

        this.time = (Time) getIntent().getSerializableExtra("Time");
        this.initSpinnerPosicoes();
    }

    private void initSpinnerPosicoes() {
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.posicoesValue)
        );

        spinner = (MultiSpinner) findViewById(R.id.spnPosicoesDisponiveis);
        spinner.setAdapter(adapter, false, onSelectedListener);
    }

    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
        @Override
        public void onItemsSelected(boolean[] selected) {
            // do nothing
        }
    };

    public void salvar(View view) {
        this.endereco = this.makeEndereco();
        this.jogo = this.makeJogo();
        Services.Endereco.Criador.execute(this, jogo.getEndereco())
                .done(this.buildCriadorEnderecoDoneCallback())
                .fail(this.buildCriadorEnderecoFailCallback());
    }

    private Jogo makeJogo() {
        String dataStr = ((EditText)findViewById(R.id.edtDataJogo)).getText().toString();
        String[] dataArr = dataStr.split("/");
        Date data = new Date(Integer.parseInt(dataArr[2]), Integer.parseInt(dataArr[1]), Integer.parseInt(dataArr[0]));

        Jogo jogo = new Jogo();
        jogo.setDatadoEm(data);
        jogo.setTime(this.time);
        jogo.setEndereco(this.endereco);

        return jogo;
    }

    private Endereco makeEndereco() {
        return new Endereco(
                null,
                ((EditText) findViewById(R.id.edtEnderecoJogo)).getText().toString(),
                ((EditText) findViewById(R.id.edtCidadeJogo)).getText().toString(),
                ((EditText) findViewById(R.id.edtEstadoJogo)).getText().toString()
        );
    }

    private DoneCallback buildCriadorEnderecoDoneCallback() {
        return new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONObject json = (JSONObject)result;

                    jogo.getEndereco().setId(json.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Services.Jogo.Criador.execute(activity, jogo)
                        .done(activity.buildCriadorJogoDoneCallback())
                        .fail(activity.buildCriadorJogoFailCallback());
            }
        };
    }

    private FailCallback buildCriadorEnderecoFailCallback() {
        return new FailCallback() {
            @Override
            public void onFail(Object result) {
                Toast.makeText(
                        activity,
                        "Ocorreu um erro ao salvar o seu registro. Tente novamente, por favor",
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
    }

    private DoneCallback buildCriadorJogoDoneCallback() {
        return new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONObject json = (JSONObject)result;
                    activity.jogo.setId(json.getInt("id"));
                    activity.salvarPosicoes();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(activity, HomeTime.class);
                startActivity(i);
            }
        };
    }

    private FailCallback buildCriadorJogoFailCallback() {
        return new FailCallback() {
            @Override
            public void onFail(Object result) {
                Toast.makeText(
                        activity,
                        "Ocorreu um erro ao salvar o seu registro. Tente novamente, por favor",
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
    }

    private void salvarPosicoes() {
        String[] keys = getResources().getStringArray(R.array.posicoesKey);
        for (String key : keys) {
            Services.Jogo.PosicoesDisponiveis.Criador.execute(
                    this, new PosicaoDisponivelJogo(key)
            ).done(new DoneCallback() {
                @Override
                public void onDone(Object result) {

                }
            });
        }
    }
}
