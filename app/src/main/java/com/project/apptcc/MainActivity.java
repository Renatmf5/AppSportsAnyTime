package com.project.apptcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import Services.LoginService.LoginService;


public class MainActivity extends DebugActivity {
    private final Activity that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
                        .done(new DoneCallback() {
                            @Override
                            public void onDone(Object result) {
                                Intent intent = new Intent(getContext(), BemVindoActivity.class);
                                Bundle params = new Bundle();
                                intent.putExtras(params);
                                startActivity(intent);
                            }
                        });
            }
        };
    }

    private Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Clicou no settings",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
