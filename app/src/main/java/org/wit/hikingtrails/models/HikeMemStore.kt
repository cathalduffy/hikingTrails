package org.wit.hikingtrails.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HikeMemStore : HikeStore {

    val hikes = ArrayList<HikeModel>()

    override suspend fun findAll(): List<HikeModel> {
        return hikes
    }

    override suspend fun findById(id:Long) : HikeModel? {
        val foundHike: HikeModel? = hikes.find { it.id == id }
        return foundHike
    }

    override suspend fun create(hike: HikeModel) {
        hike.id = getId()
        hikes.add(hike)
        logAll()
    }

    override suspend fun update(hike: HikeModel) {
        val foundHike: HikeModel? = hikes.find { p -> p.id == hike.id }
        if (foundHike != null) {
            foundHike.name = hike.name
            foundHike.description = hike.description
            foundHike.image = hike.image
            foundHike.lat = hike.lat
            foundHike.lng = hike.lng
            foundHike.zoom = hike.zoom
            logAll()
        }
    }

    override suspend fun remove(hike: HikeModel) {
        val foundHike: HikeModel? = hikes.find { p -> p.id == hike.id }
        hikes.remove(foundHike)
    }

    private fun logAll() {
        hikes.forEach { i("$it") }
    }
}