package br.com.teste.minierp.minierp.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.teste.minierp.minierp.R;
import br.com.teste.minierp.minierp.models.Produto;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.RecyclerViewHolder> {

    private List<Produto> produtoList;

    public ProdutoAdapter(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Produto produto = produtoList.get(position);

        holder.mTvDescricao.setText(produto.getDescricao());
        holder.mTvCodBarras.setText(produto.getCodBarras());
        holder.mTvValor.setText(String.valueOf(produto.getValor()));

        holder.itemView.setTag(produto);

    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public void addProdutos(List<Produto> produtoList) {
        this.produtoList = produtoList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDescricao)TextView mTvDescricao;
        @BindView(R.id.tvCodBarras) TextView mTvCodBarras;
        @BindView(R.id.tvValor) TextView mTvValor;

        RecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}