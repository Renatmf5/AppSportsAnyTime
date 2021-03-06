package com.project.apptcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jdeferred.DeferredManager;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.multiple.MultipleResults;
import org.jdeferred.multiple.OneReject;
import org.json.JSONException;
import org.json.JSONObject;

import Objetos.Endereco;
import Objetos.Time;
import Objetos.Usuario;
import Services.HttpService;
import Services.Login.LoginService;


public class CadastroTimeActivity extends Activity {
    private final CadastroTimeActivity activity = this;
    Endereco endereco;
    Usuario usuario;
    Time time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_time);

        this.initSpinnerTiposDeJogo();
    }

    private void initSpinnerTiposDeJogo() {
        Spinner spTipos = (Spinner) findViewById(R.id.spnTipoJogoTime);
        ArrayAdapter adapterTipos = ArrayAdapter.createFromResource(
                this,
                R.array.tiposValue,
                android.R.layout.simple_spinner_item
        );
        spTipos.setAdapter(adapterTipos);
    }

    public void registrar(View view) {
        this.endereco = this.makeEndereco();
        this.usuario = this.makeUsuario();
        this.time = this.makeTime(this.endereco, this.usuario);

        DeferredManager deferredManager = new DefaultDeferredManager();
        deferredManager.when(
                Services.Usuario.Criador.execute(this, time.getUsuario()),
                Services.Endereco.Criador.execute(this, time.getEndereco())
        ).done(
                this.buildCriadorUsuarioEEnderecoDoneCallback(time)
        ).fail(
                this.buildCriadorUsuarioEEnderecoFailCallback()
        );
    }

    private Time makeTime(Endereco endereco, Usuario usuario) {
        return new Time(
                ((TextView) findViewById(R.id.edtNomeTime)).getText().toString(),
                this.buildTipoDeJogoAsString(),
                ((TextView) findViewById(R.id.edtTelefoneTime)).getText().toString(),
                usuario,
                endereco
        );
    }

    private String buildTipoDeJogoAsString() {
        Spinner spn = (Spinner) findViewById(R.id.spnTipoJogoTime);
        String[] keys = getResources().getStringArray(R.array.tiposKey);
        return keys[spn.getSelectedItemPosition()];
    }

    private Endereco makeEndereco() {
        return new Endereco(
                null,
                ((EditText) findViewById(R.id.edtEnderecoTime)).getText().toString(),
                ((EditText) findViewById(R.id.edtCidadeTime)).getText().toString(),
                ((EditText) findViewById(R.id.edtEstadoTime)).getText().toString()
        );
    }

    private Usuario makeUsuario() {
        return new Usuario(
                null,
                ((EditText) findViewById(R.id.edtNomeTime)).getText().toString(),
                ((EditText) findViewById(R.id.edtEmailTime)).getText().toString(),
                ((EditText) findViewById(R.id.edtSenhaTime)).getText().toString()
        );
    }

    private DoneCallback buildCriadorUsuarioEEnderecoDoneCallback(final Time time) {
        return new DoneCallback<MultipleResults>() {
            @Override
            public void onDone(MultipleResults result) {
                try {
                    JSONObject resultUsuario = (JSONObject)result.get(0).getResult();
                    JSONObject resultEndereco = (JSONObject)result.get(1).getResult();

                    time.getUsuario().setId(resultUsuario.getString("id"));
                    time.getEndereco().setId(resultEndereco.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Services.Time.Criador.execute(time)
                        .done(activity.buildCriadorTimeDoneCallback(time))
                        .fail(activity.buildCriadorTimeFailCallback(time));
            }
        };
    }

    private FailCallback buildCriadorUsuarioEEnderecoFailCallback() {
        return new FailCallback<OneReject>() {
            @Override
            public void onFail(OneReject result) {
                Toast.makeText(
                        activity,
                        "Ocorreu um erro ao salvar o seu registro. Tente novamente, por favor",
                        Toast.LENGTH_SHORT
                ).show();
            }
        };
    }

    private DoneCallback buildCriadorTimeDoneCallback(final Time time) {
        return new DoneCallback() {
            @Override
            public void onDone(Object result) {
                LoginService.execute(usuario.getEmail(), usuario.getPassword())
                        .done(new DoneCallback() {
                            @Override
                            public void onDone(Object result) {
                                try {
                                    JSONObject json = (JSONObject)result;
                                    HttpService.getInstance().setToken(json.getString("access_token"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(activity, HomeTime.class);
                                intent.putExtra("Time", time);
                                startActivity(intent);
                            }
                        })
                        .fail(new FailCallback() {
                            @Override
                            public void onFail(Object result) {
                                Toast.makeText(CadastroTimeActivity.this,
                                        "Ocorreu um erro ao autenticar seu usuário após o cadastro. " +
                                                "Por favor, faça o login para continuar", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(CadastroTimeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        };
    }

    private FailCallback buildCriadorTimeFailCallback(Time time) {
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
}
