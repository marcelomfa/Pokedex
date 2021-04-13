package com.marceloaquino.pokedex

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_pokemom.view.*

class MainActivity : AppCompatActivity() {

    private var pokemons: List<Pokemom> = listOf(
        Pokemom (
            "Pikachu",
            25,
            listOf("eletric"),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
            6.0F,
            4F,
            -3.088065767230632,
            -59.94700775014282
        ),
        Pokemom (
            "Charmander",
            4,
            listOf("fire"),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
            8.5F,
            6F,
            -3.09030882218969,
            -59.943538313895374
        ),
        Pokemom (
            "Squirtle",
            7,
            listOf("water"),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
            9.0F,
            5F,
            -3.0880195643789734,
            -59.948066006988554
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPokemons.adapter = PokemomAdapter(pokemons) {
            val intent = Intent(this, PokemomDetailActivity::class.java).apply {
                putExtra(PokemomDetailActivity.POKEMOM_EXTRA, it)
            }

            startActivity(intent)
        }
        shouldDisplayEmptyView(pokemons.isEmpty())
    }

    fun shouldDisplayEmptyView(isEmpty: Boolean){
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    class PokemomAdapter (
        private val pokemons : List<Pokemom>,
        private val onItemClick : (Pokemom) -> Unit ) :

        RecyclerView.Adapter<PokemomAdapter.PokemomViewHolder> (){
        class PokemomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemomViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_pokemom,parent,false)
            return PokemomViewHolder(itemView)
        }

        override fun getItemCount() = pokemons.size


        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: PokemomViewHolder, position: Int) {
            holder.itemView.tvPokemomName.text = pokemons[position].name
            holder.itemView.tvPokemomNumber.text = "#%03d".format(pokemons[position].number)
            Picasso.get().load(pokemons[position].imageURL).into(holder.itemView.ivPokemomImage)

            holder.itemView.setOnClickListener{
                onItemClick(pokemons[position])
            }
        }



    }


    override fun onRestart() {
        super.onRestart()
        Log.d("Callbacks: ","OnRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Callbacks: ","OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Callbacks: ","OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Callbacks: ","OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Callbacks: ","OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Callbacks: ","OnDestroy")
    }

}