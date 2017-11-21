package br.com.teste.minierp.minierp.services;

import java.util.List;

import br.com.teste.minierp.minierp.models.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Felip on 21/11/2017.
 */

public interface Service {
    @GET("produtos")
    Call<List<Produto>> getAllProdutos();

    @GET("produtos/{id}")
    Call<Produto> getProduto(@Path("id") int id);

    @DELETE("produtos/{id}")
    Call<Produto> deleteProduto(@Path("id") int id);

    @POST("produtos")
    Call<Produto> saveProduto(@Body Produto produto);
}
