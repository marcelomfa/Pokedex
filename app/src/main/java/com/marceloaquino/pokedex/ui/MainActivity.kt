package com.marceloaquino.pokedex.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.libraries.places.api.Places
import com.marceloaquino.pokedex.BuildConfig
import com.marceloaquino.pokedex.R
import com.marceloaquino.pokedex.data.AppDatabase
import com.marceloaquino.pokedex.data.Pokemon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val pokemons: MutableList<Pokemon> = mutableListOf()

    private var adapter: PokemonAdapter =
        PokemonAdapter(pokemons) {
            Intent(
                this,
                PokemonDetailActivity::class.java
            ).apply {
                putExtra(
                    PokemonDetailActivity.POKEMOM_EXTRA,
                    it
                )
            }.also { startActivity(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the SDK
        Places.initialize(applicationContext,
            BuildConfig.GOOGLE_API_KEY
        )

        setUpPokemonListWithRecycleView()
        setUpAddPokemonButtonClick()

        shouldDisplayEmptyView(pokemons.isEmpty())

    }

    private fun setUpAddPokemonButtonClick() {
        floatingActionButton.setOnClickListener { startAddPokemonActivityForNewPokemon() }
    }

    private fun startAddPokemonActivityForNewPokemon() {
        Intent(this, AddPokemonActivity::class.java).also {
            startActivityForResult(it,ADD_POKEMON_REQUEST_CODE)
        }
    }

    private fun setUpPokemonListWithRecycleView() {
        rvPokemons.adapter = adapter
    }

    private fun shouldDisplayEmptyView(isEmpty: Boolean){
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        val updatedPokemonList = AppDatabase.getInstance(this).pokemonDAO.selectAll()
        updateRecycleView(updatedPokemonList)
    }

    private fun updateRecycleView(updatedPokemonList: List<Pokemon>){
        pokemons.clear()
        pokemons.addAll(updatedPokemonList)
        adapter.notifyDataSetChanged()
        shouldDisplayEmptyView(pokemons.isEmpty())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_POKEMON_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.getParcelableExtra<Pokemon>(AddPokemonActivity.ADDED_POKEMON_EXTRA)?.let {
                pokemons.add(it)
                adapter.notifyDataSetChanged()
                shouldDisplayEmptyView(pokemons.isEmpty())
            }
        }
    }

    companion object{
        const val ADD_POKEMON_REQUEST_CODE = 1
    }
}