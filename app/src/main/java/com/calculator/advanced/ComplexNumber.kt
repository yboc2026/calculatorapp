package com.calculator.advanced

/**
 * 复数运算类
 */
data class ComplexNumber(
    val real: Double,
    val imaginary: Double
) {
    
    // 加法
    fun add(other: ComplexNumber): ComplexNumber {
        return ComplexNumber(
            this.real + other.real,
            this.imaginary + other.imaginary
        )
    }
    
    // 减法
    fun subtract(other: ComplexNumber): ComplexNumber {
        return ComplexNumber(
            this.real - other.real,
            this.imaginary - other.imaginary
        )
    }
    
    // 乘法
    fun multiply(other: ComplexNumber): ComplexNumber {
        val real = this.real * other.real - this.imaginary * other.imaginary
        val imaginary = this.real * other.imaginary + this.imaginary * other.real
        return ComplexNumber(real, imaginary)
    }
    
    // 除法
    fun divide(other: ComplexNumber): ComplexNumber {
        val denominator = other.real.pow(2) + other.imaginary.pow(2)
        if (denominator == 0.0) throw ArithmeticException("Division by zero")
        
        val real = (this.real * other.real + this.imaginary * other.imaginary) / denominator
        val imaginary = (this.imaginary * other.real - this.real * other.imaginary) / denominator
        return ComplexNumber(real, imaginary)
    }
    
    // 共轭复数
    fun conjugate(): ComplexNumber {
        return ComplexNumber(this.real, -this.imaginary)
    }
    
    // 绝对值（模）
    fun magnitude(): Double {
        return sqrt(real.pow(2) + imaginary.pow(2))
    }
    
    // 角度（相位）
    fun phase(): Double {
        return MathFunctions.atan(imaginary / real)
    }
    
    // 乘幂（a^n）
    fun power(n: Double): ComplexNumber {
        val magnitude = magnitude()
        val phase = phase()
        
        val newMagnitude = magnitude.pow(n)
        val newPhase = phase * n
        
        val real = newMagnitude * cos(newPhase)
        val imaginary = newMagnitude * sin(newPhase)
        
        return ComplexNumber(real, imaginary)
    }
    
    // 平方根
    fun sqrt(): ComplexNumber {
        val magnitude = magnitude()
        val phase = phase()
        
        val sqrtMagnitude = sqrt(magnitude)
        val sqrtPhase = phase / 2
        
        val real = sqrtMagnitude * cos(sqrtPhase)
        val imaginary = sqrtMagnitude * sin(sqrtPhase)
        
        return ComplexNumber(real, imaginary)
    }
    
    // 指数函数 e^z
    fun exp(): ComplexNumber {
        val real = MathFunctions.exp(this.real) * cos(this.imaginary)
        val imaginary = MathFunctions.exp(this.real) * sin(this.imaginary)
        
        return ComplexNumber(real, imaginary)
    }
    
    // 对数函数 log(z)
    fun log(): ComplexNumber {
        val magnitude = magnitude()
        val phase = phase()
        
        val real = log(magnitude)
        val imaginary = phase
        
        return ComplexNumber(real, imaginary)
    }
    
    // 正弦函数 sin(z)
    fun sin(): ComplexNumber {
        val real = sin(real) * cosh(imaginary)
        val imaginary = cos(real) * sinh(imaginary)
        
        return ComplexNumber(real, imaginary)
    }
    
    // 余弦函数 cos(z)
    fun cos(): ComplexNumber {
        val real = cos(real) * cosh(imaginary)
        val imaginary = -sin(real) * sinh(imaginary)
        
        return ComplexNumber(real, imaginary)
    }
    
    // 三角函数辅助函数
    private fun sinh(x: Double): Double {
        return (MathFunctions.exp(x) - MathFunctions.exp(-x)) / 2
    }
    
    private fun cosh(x: Double): Double {
        return (MathFunctions.exp(x) + MathFunctions.exp(-x)) / 2
    }
    
    override fun toString(): String {
        if (imaginary == 0.0) {
            return real.toString()
        } else if (real == 0.0) {
            return imaginary.toString() + "i"
        } else if (imaginary > 0) {
            return real.toString() + "+" + imaginary.toString() + "i"
        } else {
            return real.toString() + imaginary.toString() + "i"
        }
    }
    
    // 格式化显示
    fun format(): String {
        val formattedReal = MathFunctions.formatNumber(real)
        val formattedImaginary = MathFunctions.formatNumber(imaginary)
        
        if (imaginary == 0.0) {
            return formattedReal
        } else if (real == 0.0) {
            return formattedImaginary + "i"
        } else if (imaginary > 0) {
            return formattedReal + "+" + formattedImaginary + "i"
        } else {
            return formattedReal + formattedImaginary + "i"
        }
    }
}