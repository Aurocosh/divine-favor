package aurocosh.divinefavor.common.lib.math

import aurocosh.divinefavor.common.lib.interfaces.IDeepCopy
import java.util.*

data class Vector2i(val x: Int, val y: Int) {

    constructor(n: Int) : this(n, n) {
    }

    val isZero: Boolean
        get() = x == 0 && y == 0

    fun inverse(): Vector2i {
        return Vector2i(-x, -y)
    }

    fun add(other: Vector2i): Vector2i {
        return Vector2i(
                x + other.x,
                y + other.y
        )
    }

    fun subtract(vector3i: Vector2i): Vector2i {
        return Vector2i(
                x - vector3i.x,
                y - vector3i.y
        )
    }

    fun scale(value: Int): Vector2i {
        return Vector2i(x * value, y * value)
    }

    fun divide(value: Int): Vector2i {
        return Vector2i(x / value, y / value)
    }

    fun dot(other: Vector2i): Int {
        return x * other.x + y * other.y
    }

    fun getMinCoordinates(vector: Vector2i): Vector2i {
        val xMin = Math.min(x, vector.x)
        val yMin = Math.min(y, vector.y)
        return Vector2i(xMin, yMin)
    }

    fun getMaxCoordinates(vector: Vector2i): Vector2i {
        val xMax = Math.max(x, vector.x)
        val yMax = Math.max(y, vector.y)
        return Vector2i(xMax, yMax)
    }

    fun orthogonal(other: Vector2i): Boolean {
        return dot(other) == 0
    }

    fun magnitude(): Double {
        return Math.sqrt(magnitudeSquare().toDouble())
    }

    fun magnitudeSquare(): Int {
        return x * x + y * y
    }
}