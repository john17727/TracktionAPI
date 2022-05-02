package dev.juanrincon.domain.models

import java.io.Serializable

data class Instruction(
    val step: Int,
    val text: String
) : Serializable
