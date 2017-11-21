package br.com.teste.minierp.minierp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.minierp.minierp.R;
import br.com.teste.minierp.minierp.helpers.RecyclerTouchListener;
import br.com.teste.minierp.minierp.models.Produto;
import br.com.teste.minierp.minierp.services.Service;
import br.com.teste.minierp.minierp.services.ServiceGenerator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ProdutoAdapter mProdutoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mProdutoAdapter = new ProdutoAdapter(new ArrayList<Produto>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mProdutoAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getBaseContext(), mRecyclerView,
                        new RecyclerTouchListener.OnTouchActionListener() {
                            public void onClick(View view, int position) {
                                Produto produto = (Produto) view.getTag();
                                //Toast.makeText(getBaseContext(), produto.getDescricao(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), ProdutoDetalheActivity.class).putExtra("id", produto.getProdutoId()));
                            }

                            @Override
                            public void onRightSwipe(View view, int position) {
                                Produto produto = (Produto) view.getTag();
                                deleteProduto(produto.getProdutoId());
                            }
                            @Override
                            public void onLeftSwipe(View view, int position) {
                                Produto produto = (Produto) view.getTag();
                                deleteProduto(produto.getProdutoId());
                            }

                        }));

        getProdutos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProdutos();
    }

    public void getProdutos(){

        Service service = ServiceGenerator.createService(Service.class);

        final Call<List<Produto>> produtos = service.getAllProdutos();

        produtos.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                mProdutoAdapter.addProdutos(response.body());
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteProduto(int id) {
        try {

            Service service = ServiceGenerator.createService(Service.class);

            final Call<Produto> person = service.deleteProduto(id);

            person.enqueue(new Callback<Produto>() {
                @Override
                public void onResponse(Call<Produto> call, Response<Produto> response) {

                    Produto produto = response.body();

                    if (response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Produto " + produto.getDescricao() + " deletado com sucesso!", Toast.LENGTH_LONG).show();
                        getProdutos();
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

    @OnClick(R.id.fab)
    public void btnCadastro(View view) {
        startActivity(new Intent(getBaseContext(), ProdutoCadastroActivity.class));
    }
}
