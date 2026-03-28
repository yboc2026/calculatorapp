package com.calculator.advanced

import kotlin.math.*

/**
 * 数学函数库 - 包含所有高级数学函数
 */
object MathFunctions {
    
    // 三角函数
    fun sin(x: Double): Double = sin(x)
    fun cos(x: Double): Double = cos(x)
    fun tan(x: Double): Double = tan(x)
    
    fun asin(x: Double): Double {
        if (x < -1.0 || x > 1.0) throw IllegalArgumentException("asin requires input between -1 and 1")
        return asin(x)
    }
    
    fun acos(x: Double): Double {
        if (x < -1.0 || x > 1.0) throw IllegalArgumentException("acos requires input between -1 and 1")
        return acos(x)
    }
    
    fun atan(x: Double): Double = atan(x)
    
    // 对数函数
    fun log(x: Double): Double {
        if (x <= 0) throw IllegalArgumentException("log requires positive input")
        return log10(x)
    }
    
    fun ln(x: Double): Double {
        if (x <= 0) throw IllegalArgumentException("ln requires positive input")
        return ln(x)
    }
    
    // 指数函数
    fun exp(x: Double): Double = exp(x)
    fun pow(base: Double, exponent: Double): Double = base.pow(exponent)
    
    // 幂运算
    fun sqrt(x: Double): Double {
        if (x < 0) throw IllegalArgumentException("sqrt requires non-negative input")
        return sqrt(x)
    }
    
    fun cbrt(x: Double): Double = x.pow(1.0/3.0)
    
    fun square(x: Double): Double = x * x
    fun cube(x: Double): Double = x * x * x
    
    // 阶乘
    fun factorial(n: Long): Long {
        if (n < 0) throw IllegalArgumentException("factorial requires non-negative input")
        if (n > 20) throw IllegalArgumentException("factorial overflow for n > 20")
        
        var result = 1L
        for (i in 1..n) {
            result *= i
        }
        return result
    }
    
    // 绝对值
    fun absolute(x: Double): Double = abs(x)
    
    // 常用常数
    fun pi(): Double = AppConfig.PI
    fun e(): Double = AppConfig.E
    
    // 弧度/角度转换
    fun degreesToRadians(degrees: Double): Double = degrees * AppConfig.PI / 180.0
    fun radiansToDegrees(radians: Double): Double = radians * 180.0 / AppConfig.PI
    
    // 进制转换
    fun decimalToBinary(decimal: Long): String = decimal.toString(2)
    fun decimalToOctal(decimal: Long): String = decimal.toString(8)
    fun decimalToHexadecimal(decimal: Long): String = decimal.toString(16)
    
    fun binaryToDecimal(binary: String): Long = binary.toLong(2)
    fun octalToDecimal(octal: String): Long = octal.toLong(8)
    fun hexadecimalToDecimal(hex: String): Long = hex.toLong(16)
    
    // 统计函数
    fun mean(values: List<Double>): Double {
        if (values.isEmpty()) return 0.0
        return values.sum() / values.size
    }
    
    fun variance(values: List<Double>): Double {
        if (values.isEmpty()) return 0.0
        val meanVal = mean(values)
        val sumSquares = values.map { (it - meanVal).pow(2) }.sum()
        return sumSquares / values.size
    }
    
    fun standardDeviation(values: List<Double>): Double = sqrt(variance(values))
    
    // 工程计算
    fun parallelResistance(resistors: List<Double>): Double {
        if (resistors.isEmpty()) return 0.0
        val sumInverse = resistors.map { 1.0 / it }.sum()
        return 1.0 / sumInverse
    }
    
    fun seriesResistance(resistors: List<Double>): Double {
        if (resistors.isEmpty()) return 0.0
        return resistors.sum()
    }
    
    fun capacitorTimeConstant(resistance: Double, capacitance: Double): Double = resistance * capacitance
    
    fun inductorTimeConstant(resistance: Double, inductance: Double): Double = inductance / resistance
    
    // 近似导数计算（有限差分法）
    fun derivative(f: (Double) -> Double, x: Double, h: Double = 1e-6): Double {
        return (f(x + h) - f(x - h)) / (2 * h)
    }
    
    // 近似积分计算（梯形法）
    fun integral(f: (Double) -> Double, a: Double, b: Double, n: Int = 1000): Double {
        val h = (b - a) / n
        var sum = 0.5 * (f(a) + f(b))
        
        for (i in 1 until n) {
            sum += f(a + i * h)
        }
        
        return sum * h
    }
    
    // 百分比计算
    fun percentage(value: Double, total: Double): Double = (value / total) * 100.0
    
    fun valueFromPercentage(percentage: Double, total: Double): Double = (percentage / 100.0) * total
    
    // 格式化数字
    fun formatNumber(value: Double): String {
        if (abs(value) < 1e-15) return "0"
        
        val absValue = abs(value)
        if (absValue >= 1e9 || absValue <= 1e-9) {
            return value.toString()
        }
        
        // 保留15位精度
        val formatted = value.toString()
        if (formatted.contains('.')) {
            val parts = formatted.split('.')
            if (parts[1].length > AppConfig.PRECISION) {
                return value.format("%.${AppConfig.PRECISION}f")
            }
        }
        
        return formatted
    }
    
    private fun Double.format(format: String): String {
        return format.format(this)
    }
}