package org.wit.hikingtrails.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.hikingtrails.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "hikes.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<HikeModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class HikeJSONStore(private val context: Context) : HikeStore {

    var hikes = mutableListOf<HikeModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<HikeModel> {
        logAll()
        return hikes
    }

    override fun create(hike: HikeModel) {
        hike.id = generateRandomId()
        hikes.add(hike)
        serialize()
    }

    override fun findById(id: Long): HikeModel? {
        TODO("Not yet implemented")
    }

    override fun remove(hike: HikeModel) {
        val foundHike: HikeModel? = hikes.find { p -> p.id == hike.id }
        hikes.remove(foundHike)
        serialize()
    }

    override fun update(hike: HikeModel) {
        val foundHike: HikeModel? = hikes.find { p -> p.id == hike.id }
        if (foundHike != null) {
            foundHike.name = hike.name
            foundHike.description = hike.description
            foundHike.image = hike.image
            foundHike.lat = hike.lat
            foundHike.lng = hike.lng
            foundHike.zoom = hike.zoom
            foundHike.distance = hike.distance
            foundHike.difficultyLevel = hike.difficultyLevel
            serialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hikes, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hikes = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        hikes.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}