package com.teampwr.przepisomat.model

import com.google.errorprone.annotations.Keep

data class Recipe(
    val image: String?,
    val name: String?,
    val description: String?,
    val likes: Long?,
    val category: String?
) {
    constructor() : this(null, null, null, null, null)
}
