package com.marceloaquino.pokedex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val pokemonDAO : PokemonDAO

    companion object{
        fun getInstance(context: Context) =
            Room.databaseBuilder(context,AppDatabase::class.java, "pokedex-db")
                .allowMainThreadQueries() //não usa isso em produção. porfavor! AMEM!
                .build()
    }
}