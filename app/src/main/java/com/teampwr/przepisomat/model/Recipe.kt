package com.teampwr.przepisomat.model

import com.google.errorprone.annotations.Keep

data class Recipe(
    val image: String?,
    val name: String?,
    val description: String?,
    val likes: Long?,
    val category: String?,
    val en_name: String?,
    val en_category: String?,
    val en_description: String?,
    val movie_url: String?

) {
    constructor() : this(null, null, null, null, null, null, null, null, null)
}
