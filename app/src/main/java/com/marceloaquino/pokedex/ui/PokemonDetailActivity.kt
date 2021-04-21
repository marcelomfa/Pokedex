package com.marceloaquino.pokedex.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marceloaquino.pokedex.R
import com.marceloaquino.pokedex.data.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemom_detail.*

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemom_detail)
        bindViewsToPokemonData()
    }

    private fun bindViewsToPokemonData() {
        intent.getParcelableExtra<Pokemon>(POKEMOM_EXTRA)?.let {
            tvPokemomNameDetails.text = it.name
            tvPokemomNumberDetails.text = "#%03d".format(it.number)
            tvPokemomHeight.text = "%.2f cm".format(it.height * 10)
            tvPokemomWeight.text = "%.1f kg".format(it.weight / 10)
            Picasso.get().load(it.imageURL).into(ivPokemomImageDetail)

            bindTextViewsToPokemonTypes(it)


            bindMapFragmentToPokemonLocation(it)
        }
    }


    private fun bindTextViewsToPokemonTypes(pokemom: Pokemon) {
        tvPokemomType1.text = pokemom.types.getOrNull(0)
        val secondType = pokemom.types.getOrNull(1)
        if (secondType == null) {
            tvPokemomType2.visibility = View.GONE
        } else {
            tvPokemomType2.visibility = View.VISIBLE
            tvPokemomType2.text = secondType
        }
    }

    private fun bindMapFragmentToPokemonLocation(pokemom: Pokemon) {
        (supportFragmentManager.findFragmentById( R.id.mapFragment ) as SupportMapFragment).apply {
            getMapAsync {GoogleMap ->
                GoogleMap.uiSettings.isZoomControlsEnabled = true

                val latlng = LatLng(pokemom.latitude, pokemom.longitude)

                MarkerOptions()
                    .position(latlng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .also {  GoogleMap.addMarker(it) }

                GoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                GoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
            }
        }
    }

    companion object{
        val POKEMOM_EXTRA = "${PokemonDetailActivity::class.java.`package`}.showPokemonDetail"
    }
}