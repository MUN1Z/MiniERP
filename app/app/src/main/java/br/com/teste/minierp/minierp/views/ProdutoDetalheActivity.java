package br.com.teste.minierp.minierp.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.teste.minierp.minierp.R;
import br.com.teste.minierp.minierp.models.Produto;
import br.com.teste.minierp.minierp.services.Service;
import br.com.teste.minierp.minierp.services.ServiceGenerator;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private int mId = 1;

    @BindView(R.id.tvDescricao)TextView mTvDescricao;
    @BindView(R.id.tvCodBarras) TextView mTvCodBarras;
    @BindView(R.id.tvValor) TextView mTvValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() != null) {
            mId = getIntent().getExtras().getInt("id");
        }

        getProduto(mId);
    }

    public void getProduto(int id){
        try {

            Service service = ServiceGenerator.createService(Service.class);

            final Call<Produto> person = service.getProduto(id);

            person.enqueue(new Callback<Produto>() {
                @Override
                public void onResponse(Call<Produto> call, Response<Produto> response) {

                    Produto produto = response.body();
                    mTvDescricao.setText("Descrição: " + produto.getDescricao());
                    mTvCodBarras.setText("Código de barras: " + produto.getCodBarras());
                    mTvValor.setText("Valor: " + produto.getValor());
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
