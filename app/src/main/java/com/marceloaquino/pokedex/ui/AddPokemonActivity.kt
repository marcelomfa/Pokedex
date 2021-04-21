package com.marceloaquino.pokedex.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.marceloaquino.pokedex.R
import com.marceloaquino.pokedex.data.AppDatabase
import com.marceloaquino.pokedex.data.PokeApi
import com.marceloaquino.pokedex.data.Pokemon
import kotlinx.android.synthetic.main.activity_add_pokemom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AddPokemonActivity : AppCompatActivity() {
    private lateinit var place: Place

    private val Editable?.isFilled get() = !(isNullOrEmpty() || isNullOrBlank())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pokemom)
        setupInputLocationClick()
        setUpDoneButtonClick()
    }

    private fun setUpDoneButtonClick() {
        btDone.setOnClickListener {

            if (etNameInput.text.isFilled && edLocationInput.text.isFilled) {
                val name = etNameInput.text.toString().toLowerCase(Locale.getDefault())

                fetchPokemonByName(
                    name,
                    onError = {
                        Toast.makeText(
                            this@AddPokemonActivity,
                            "Erro Inesperado!",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onSucess = {
                        it.latitude = place.latLng?.latitude ?: 0.0
                        it.longitude = place.latLng?.longitude ?: 0.0
                        AppDatabase.getInstance(this).pokemonDAO.insert(it)
                        finish()
                    }
                )
            } else {
                Toast.makeText(this, "Error nos Campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchPokemonByName(
        name : String,
        onSucess : (Pokemon) -> Unit,
        onError : () -> Unit
    ){
        PokeApi.INSTANCE.getPokemomByName(name).enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                onError()
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                val pokemon = response.body()
                if (response.isSuccessful && pokemon != null) {
                    onSucess(pokemon)
                } else {
                    onError()
                }
            }
        })
    }

    private fun setupInputLocationClick() {
        edLocationInput.setOnClickListener {
            startAutocompleteActivityForPlace()
        }
    }

    private fun startAutocompleteActivityForPlace() {
        val fields = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this).also {
            startActivityForResult(it,AUTOCOMPLETE_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && data != null){
            if(resultCode == Activity.RESULT_OK){
                place = Autocomplete.getPlaceFromIntent(data)
                edLocationInput.setText(place.address)
            }else{
                println("Deu erro")
            }
        }
    }

    companion object{
        const val AUTOCOMPLETE_REQUEST_CODE = 1
        const val ADDED_POKEMON_EXTRA = "AddedPokemon"
    }

}
