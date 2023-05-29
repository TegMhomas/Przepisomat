package com.teampwr.przepisomat.model

import com.google.errorprone.annotations.Keep

data class Recipe(
    val image: Int,
    val name: String?,
    val description: String?,
    val likes: Long?,
    val category: String?
) {
    constructor() : this(0, null, null, null, null)
}
