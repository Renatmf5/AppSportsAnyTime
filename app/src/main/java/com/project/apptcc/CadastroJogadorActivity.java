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
import Objetos.Jogador;
import Objetos.Usuario;
import Services.HttpService;
import Services.Login.LoginService;


public class CadastroJogadorActivity extends Activity {
    private final CadastroJogadorActivity activity = this;
    Endereco endereco;
    Usuario usuario;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);

        this.initSpinnerTiposDeJogo();
        this.initSpinnerPosicoes();
    }

    private void initSpinnerTiposDeJogo() {
        Spinner spTipos = (Spinner) findViewById(R.id.spnTipoJogo);
        ArrayAdapter adapterTipos = ArrayAdapter.createFromResource(this,R.array.tiposValue,android.R.layout.simple_spinner_item);
        spTipos.setAdapter(adapterTipos);
    }

    private void initSpinnerPosicoes() {
        Spinner spPosicoes = (Spinner) findViewById(R.id.spnPosicao);
        ArrayAdapter adapterPosicoes = ArrayAdapter.createFromResource(this, R.array.posicoesValue, android.R.layout.simple_spinner_item);
        spPosicoes.setAdapter(adapterPosicoes);
    }

    public void registrar(View view) {
        this.endereco = this.makeEndereco();
        this.usuario = this.makeUsuario();
        this.jogador = this.makeJogador(this.endereco, this.usuario);

        DeferredManager deferredManager = new DefaultDeferredManager();
        deferredManager.when(
                Services.Usuario.Criador.execute(this, jogador.getUsuario()),
                Services.Endereco.Criador.execute(this, jogador.getEndereco())
        ).done(
                this.buildCriadorUsuarioEEnderecoDoneCallback(jogador)
        ).fail(
                this.buildCriadorUsuarioEEnderecoFailCallback()
        );
    }

    private Jogador makeJogador(Endereco endereco, Usuario usuario) {
        return new Jogador(
                null,
                ((TextView) findViewById(R.id.edtNomeJogador)).getText().toString(),
                this.buildTipoDeJogoAsString(),
                this.buildPosicaoAsString(),
                ((TextView) findViewById(R.id.edtTelefoneJogador)).getText().toString(),
                endereco,
                usuario
        );
    }

    private String buildTipoDeJogoAsString() {
        Spinner spn = (Spinner) findViewById(R.id.spnTipoJogo);
        String[] keys = getResources().getStringArray(R.array.tiposKey);
        return keys[spn.getSelectedItemPosition()];
    }

    private String buildPosicaoAsString() {
        Spinner spn = (Spinner) findViewById(R.id.spnPosicao);
        String[] keys = getResources().getStringArray(R.array.posicoesKey);
        return keys[spn.getSelectedItemPosition()];
    }

    private Endereco makeEndereco() {
        return new Endereco(
            null,
            ((EditText) findViewById(R.id.edtEnderecoJogador)).getText().toString(),
            ((EditText) findViewById(R.id.edtCidadeJogador)).getText().toString(),
            ((EditText) findViewById(R.id.edtEstadoJogador)).getText().toString()
        );
    }

    private Usuario makeUsuario() {
        return new Usuario(
            null,
            ((EditText) findViewById(R.id.edtNomeJogador)).getText().toString(),
            ((EditText) findViewById(R.id.edtEmailJogador)).getText().toString(),
            ((EditText) findViewById(R.id.edtSenhaJogador)).getText().toString()
        );
    }

    private DoneCallback buildCriadorUsuarioEEnderecoDoneCallback(final Jogador jogador) {
        return new DoneCallback<MultipleResults>() {
            @Override
            public void onDone(MultipleResults result) {
                try {
                    JSONObject resultUsuario = (JSONObject)result.get(0).getResult();
                    JSONObject resultEndereco = (JSONObject)result.get(1).getResult();

                    jogador.getUsuario().setId(resultUsuario.getString("id"));
                    jogador.getEndereco().setId(resultEndereco.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Services.Jogador.Criador.execute(activity, jogador)
                        .done(activity.buildCriadorJogadorDoneCallback(jogador))
                        .fail(activity.buildCriadorJogadorFailCallback(jogador));
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

    private DoneCallback buildCriadorJogadorDoneCallback(final Jogador jogador) {
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

                                Intent intent = new Intent(activity, HomeJogador.class);
                                intent.putExtra("Jogador", jogador);
                                startActivity(intent);
                            }
                        })
                        .fail(new FailCallback() {
                            @Override
                            public void onFail(Object result) {
                                Toast.makeText(CadastroJogadorActivity.this,
                                        "Ocorreu um erro ao autenticar seu usuário após o cadastro. " +
                                                "Por favor, faça o login para continuar", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(CadastroJogadorActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        };
    }

    private FailCallback buildCriadorJogadorFailCallback(Jogador jogador) {
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
