package com.marceloaquino.pokedex.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PokemomJsonDeserializer : JsonDeserializer<Pokemon>{

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Pokemon {
        val JsonObject = json?.asJsonObject
        val number = JsonObject?.get("id")?.asInt ?:0
        val name = JsonObject?.get("name")?.asString ?:""
        val weight = JsonObject?.get("weight")?.asFloat ?:0F
        val height = JsonObject?.get("height")?.asFloat ?:0F
        val types = JsonObject?.getAsJsonArray("types")?.map { it.asJsonObject.get("type").asJsonObject.get("name").asString } ?: listOf()
        val imageURL = JsonObject?.getAsJsonObject("sprites")?.get("front_default")?.asString ?:""

        return Pokemon(
            0,
            name,
            number,
            types,
            imageURL,
            weight,
            height,
            0.0,
            0.0
        )
    }
}