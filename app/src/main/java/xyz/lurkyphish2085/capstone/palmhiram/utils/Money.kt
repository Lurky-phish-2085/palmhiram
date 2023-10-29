package xyz.lurkyphish2085.capstone.palmhiram.utils


class Money(amount: Double) {

    companion object {
        const val DIVISOR = 100.00
    }

    val centValue: Long = amount.times(DIVISOR).toLong()
    val value: Double = centValue / DIVISOR

    override fun toString(): String {
        return String.format("%.2f", value)
    }

    operator fun plus(other: Money): Money = Money(this.value + other.value)
    operator fun minus(other: Money): Money = Money(this.value - other.value)
    operator fun times(other: Money): Money = Money(this.value * other.value)
    operator fun div(other: Money): Money = Money(this.value / other.value)
}