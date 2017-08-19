package com.project.apptcc;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends DebugActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Login");

        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(onClickLogin());

    Button btCadJogador = (Button) findViewById(R.id.btnCadJogador);
    Button btCadTime = (Button) findViewById(R.id.btnCadTime);
        btCadTime.setOnClickListener(onClickCadTime());
        btCadJogador.setOnClickListener(onClickCadJogador());

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

                if("Renato".equals(login) && "123".equals(senha)) {


                    Intent intent = new Intent(getContext(),BemVindoActivity.class);
                    Bundle params = new Bundle();
                    params.putString("nome", login);
                    intent.putExtras(params);
                    startActivity(intent);
                } else {
                    alert("Login e senha incorretos.");
                }
            }
        };
    }

    private Context getContext() {

        return this;
    }

    private void alert(String s) {

        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
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
