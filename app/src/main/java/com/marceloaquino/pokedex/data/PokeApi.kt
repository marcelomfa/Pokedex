package com.marceloaquino.pokedex.data

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("pokemon/{name}")
    fun getPokemomByName(@Path("name") name :String) : Call<Pokemon>

    companion object{
        val INSTANCE : PokeApi
            get(){

                val gson = GsonBuilder()
                    .registerTypeAdapter(
                        Pokemon::class.java,
                        PokemomJsonDeserializer()
                    )
                    .create()

                return Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(PokeApi::class.java)
            }
    }

}