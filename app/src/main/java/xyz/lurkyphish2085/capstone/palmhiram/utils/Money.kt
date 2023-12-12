package xyz.lurkyphish2085.capstone.palmhiram.utils

import java.text.NumberFormat


class Money(amount: Double) {

    companion object {
        const val DIVISOR = 100.00

        fun valueOf(amount: String): Money {
            if (amount.isBlank()) {
                throw IllegalArgumentException("Value should not be blank")
            }

            return Money(amount.toDouble())
        }

        fun parseActualValue(amountInCents: Long): Money {
            return Money(amountInCents / DIVISOR)
        }
    }

    val centValue: Long = amount.times(DIVISOR).toLong()
    val value: Double = centValue / DIVISOR

    override fun toString(): String {
        val formatter = NumberFormat.getInstance()
        formatter.isGroupingUsed = true

        return formatter.format(value)
    }

    operator fun plus(other: Money): Money = Money(this.value + other.value)
    operator fun minus(other: Money): Money = Money(this.value - other.value)
    operator fun times(other: Money): Money = Money(this.value * other.value)
    operator fun div(other: Money): Money = Money(this.value / other.value)
}