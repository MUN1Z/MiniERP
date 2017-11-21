package br.com.teste.minierp.minierp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.teste.minierp.minierp.R;
import br.com.teste.minierp.minierp.models.Produto;
import br.com.teste.minierp.minierp.services.Service;
import br.com.teste.minierp.minierp.services.ServiceGenerator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoCadastroActivity extends AppCompatActivity {

    @BindView(R.id.edtDescricao) EditText mEdtDescricao;
    @BindView(R.id.edtCodBarras) EditText mEdtCodBarras;
    @BindView(R.id.edtValor) EditText mEdtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.fab)
    public void btnCadastro(View view) {

        Produto produto = new Produto();

        produto.setDescricao(mEdtDescricao.getText().toString());
        produto.setCodBarras(mEdtCodBarras.getText().toString());
        produto.setValor(Float.parseFloat(mEdtValor.getText().toString()));

        saveProduto(produto);
    }

    public void saveProduto(Produto produto) {
        try {

            Service service = ServiceGenerator.createService(Service.class);

            final Call<Produto> person = service.saveProduto(produto);

            person.enqueue(new Callback<Produto>() {
                @Override
                public void onResponse(Call<Produto> call, Response<Produto> response) {

                    Produto produto = response.body();

                    if (response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Produto " + produto.getDescricao() + " cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<Produto> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sem conexão com internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("IO","IO"+e);
            Toast.makeText(this, "Exeption: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(OutOfMemoryError e1) {
            e1.printStackTrace();
            Log.e("Memory exceptions","exceptions"+e1);
        }
    }

}
