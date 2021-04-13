package com.marceloaquino.pokedex


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemom (
    val name : String,
    val number : Int,
    val types : List<String>,
    val imageURL : String,
    val weight: Float,
    val heigth: Float,
    val latitude : Double,
    val longitude : Double) : Parcelable

{}