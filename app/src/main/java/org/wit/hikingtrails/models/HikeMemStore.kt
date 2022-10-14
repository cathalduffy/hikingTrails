package org.wit.hikingtrails.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HikeMemStore : HikeStore {

    val hikes = ArrayList<HikeModel>()

    override fun findAll(): List<HikeModel> {
        return hikes
    }

    override fun create(hike: HikeModel) {
        hike.id = getId()
        hikes.add(hike)
        logAll()
    }

    override fun update(hike: HikeModel) {
        var foundHike: HikeModel? = hikes.find { p -> p.id == hike.id }
        if (foundHike != null) {
            foundHike.name = hike.name
            foundHike.description = hike.description
            logAll()
        }
    }

    private fun logAll() {
        hikes.forEach { i("$it") }
    }
}