package ar.edu.uade.cocktailapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    private val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    //objeto retrofir creado, cuando lo hice por primera vez
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val cocktailApi: ICocktailAPI by lazy {
        retrofit.create(ICocktailAPI::class.java)
    }

}