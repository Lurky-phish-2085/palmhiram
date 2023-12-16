package xyz.lurkyphish2085.capstone.palmhiram.ui.utils

import xyz.lurkyphish2085.capstone.palmhiram.utils.Money

class CalculationUtils {

    companion object {

        fun convertPercentageToDecimal(percentage: Int): Double {
            // Check if the percentage is within valid range (0-100)
            require(!(percentage < 0 || percentage > 100)) { "Percentage must be between 0 and 100" }

            // Convert percentage to decimal form
            return percentage / 100.0
        }

        fun calculateSimpleInterest(principal: Money, interestPercent: Int, timeInYears: Double): Money {
            return Money.parseActualValue((principal.centValue * convertPercentageToDecimal(interestPercent) * timeInYears).toLong())
        }
    }
}