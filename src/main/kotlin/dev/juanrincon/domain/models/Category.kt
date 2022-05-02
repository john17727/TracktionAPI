package dev.juanrincon.domain.models

import java.io.Serializable

data class Category(
    val id: Int = 0,
    val name: String,
    val imageUrl: String?
) : Serializable
