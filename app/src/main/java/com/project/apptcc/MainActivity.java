package com.project.apptcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Jogador;
import Objetos.Time;
import Services.HttpService;
import Services.Login.LoginService;


public class MainActivity extends DebugActivity {
    private final MainActivity that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        HttpService.destroy();

        Button btCadJogador = (Button) findViewById(R.id.btnCadJogador);
        Button btCadTime = (Button) findViewById(R.id.btnCadTime);
        Button btLogin = (Button) findViewById(R.id.btLogin);

        btCadJogador.setOnClickListener(onClickCadJogador());
        btCadTime.setOnClickListener(onClickCadTime());
        btLogin.setOnClickListener(onClickLogin());
    }

    private View.OnClickListener onClickCadTime(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(),CadastroTimeActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onClickCadJogador(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(),CadastroJogadorActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onClickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tLogin = (TextView) findViewById(R.id.tLogin);
                TextView tSenha = (TextView) findViewById(R.id.tSenha);
                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();

                LoginService.execute(login, senha)
                        .fail(new FailCallback() {
                            @Override
                            public void onFail(Object result) {
                                Toast.makeText(
                                        that,
                                        "Credenciais inválidas",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        })
                        .done(that.buildDoneCallback())
                        .fail(new FailCallback() {
                            @Override
                            public void onFail(Object result) {
                                Toast.makeText(
                                        that,
                                        "Credenciais inválidas",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
            }
        };
    }

    private Context getContext() {
        return this;
    }

    private DoneCallback buildDoneCallback() {
        return new DoneCallback() {
            @Override
            public void onDone(Object result) {
                try {
                    JSONObject json = (JSONObject)result;
                    HttpService.getInstance().setToken(json.getString("access_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LoginService.authenticatedUser(getContext()).done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        JSONObject json = (JSONObject) result;
                        try {
                            if (!json.isNull("team")) {
                                Time time = new Time(json.getJSONObject("team"));
                                Intent intent = new Intent(getContext(), HomeTime.class);
                                intent.putExtra("Time", time);

                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (!json.isNull("player")) {
                                Jogador jogador = new Jogador(json.getJSONObject("player"));
                                Intent intent = new Intent(getContext(), HomeJogador.class);
                                intent.putExtra("Jogador", jogador);

                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

}
