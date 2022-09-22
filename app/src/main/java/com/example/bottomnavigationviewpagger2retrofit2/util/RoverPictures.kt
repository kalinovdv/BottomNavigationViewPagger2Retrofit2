package com.example.bottomnavigationviewpagger2retrofit2.util

import com.google.gson.*
import java.io.StringReader
import java.lang.reflect.Type

class RoverPictures {
    var imgSrc: String = ""
    var earthDate: String = ""
}

class RoverPicturesDeserializer : JsonDeserializer<RoverPictures> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RoverPictures {
        val jsonObj = json as JsonObject
        val roverPictures = RoverPictures()

        roverPictures.earthDate = jsonObj.get("earth_date").asString
        roverPictures.imgSrc = jsonObj.get("img_src").asString

        return roverPictures
    }
}

fun getRoverPicturesFromJson(jsonString: String): List<RoverPictures> {
    val stringReader = StringReader(jsonString)

    val gsonBuilder = GsonBuilder().serializeNulls()
    gsonBuilder.registerTypeAdapter(RoverPictures::class.java, RoverPicturesDeserializer())

    val gson = gsonBuilder.create()

    return gson.fromJson(stringReader, Array<RoverPictures>::class.java).toList()
}