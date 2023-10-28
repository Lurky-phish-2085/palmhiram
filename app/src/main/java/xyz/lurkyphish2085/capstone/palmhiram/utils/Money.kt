package xyz.lurkyphish2085.capstone.palmhiram.utils


class Money(amount: Double) {

    companion object {
        const val DIVISOR = 100.00
    }

    private val amountInCents: Long = amount.times(DIVISOR).toLong()

    fun toCents(): Long = amountInCents
    fun toActualAmount(): Double = amountInCents / DIVISOR

    override fun toString(): String {
        return toActualAmount().toString()
    }

    operator fun plus(other: Money): Money = Money(this.toActualAmount() + other.toActualAmount())
    operator fun minus(other: Money): Money = Money(this.toActualAmount() - other.toActualAmount())
    operator fun times(other: Money): Money = Money(this.toActualAmount() * other.toActualAmount())
    operator fun div(other: Money): Money = Money(this.toActualAmount() / other.toActualAmount())
}