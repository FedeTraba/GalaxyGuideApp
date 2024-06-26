package com.example.myapplication.data.dbLocal

import com.example.myapplication.model.FavPublication
import com.example.myapplication.model.Publication

fun PublicationLocal.toPublication() = Publication(
    date,
    explanation,
    hdurl ?: "",
    title,
    url,
    copyright ?: ""
)

fun List<PublicationLocal>.toPublicationList() = map(PublicationLocal::toPublication)

fun Publication.toPublicationLocal() = PublicationLocal(
    date,
    explanation,
    hdurl ?: "",
    title,
    url,
    copyright ?: ""
)

fun List<Publication>.toPublicationLocalList() = map(Publication::toPublicationLocal)