package com.marceloaquino.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemom_detail.*

class PokemomDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemom_detail)
        intent.getParcelableExtra<Pokemom>(POKEMOM_EXTRA)?.let {
            tvPokemomNameDetails.text = it.name
            tvPokemomNumberDetails.text = "#%03d".format(it.number)

            tvPokemomType1.text = it.types.getOrNull(0)

            val secondType = it.types.getOrNull(1)
            if (secondType == null){
                tvPokemomType2.visibility = View.GONE
            }else{
                tvPokemomType2.visibility = View.VISIBLE
                tvPokemomType2.text = secondType
            }

            tvPokemomHeight.text = (it.heigth/10.0).toString() + " cm"
            tvPokemomWeight.text = it.weight.toString() + " kg"
            Picasso.get().load(it.imageURL).into(ivPokemomImageDetail)

//            val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
//            mapFragment.getMapAsync {
//
//            }
        }
    }

    companion object{
        const val POKEMOM_EXTRA = "Pokemom"
    }
}