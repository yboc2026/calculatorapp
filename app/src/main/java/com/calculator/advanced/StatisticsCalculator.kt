package com.calculator.advanced

/**
 * 统计计算器类
 */
object StatisticsCalculator {
    
    /**
     * 计算平均值
     */
    fun mean(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return MathFunctions.mean(data)
    }
    
    /**
     * 计算方差
     */
    fun variance(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return MathFunctions.variance(data)
    }
    
    /**
     * 计算标准差
     */
    fun standardDeviation(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return MathFunctions.standardDeviation(data)
    }
    
    /**
     * 计算中位数
     */
    fun median(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        val sortedData = data.sorted()
        val n = sortedData.size
        
        if (n % 2 == 0) {
            return (sortedData[n/2 - 1] + sortedData[n/2]) / 2.0
        } else {
            return sortedData[n/2]
        }
    }
    
    /**
     * 计算众数
     */
    fun mode(data: List<Double>): List<Double> {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        val frequencyMap = mutableMapOf<Double, Int>()
        for (value in data) {
            frequencyMap[value] = frequencyMap.getOrDefault(value, 0) + 1
        }
        
        val maxFrequency = frequencyMap.values.max()
        return frequencyMap.filter { it.value == maxFrequency }.keys.toList()
    }
    
    /**
     * 计算最大值
     */
    fun max(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return data.max()
    }
    
    /**
     * 计算最小值
     */
    fun min(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return data.min()
    }
    
    /**
     * 计算范围
     */
    fun range(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return max(data) - min(data)
    }
    
    /**
     * 计算总和
     */
    fun sum(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        return data.sum()
    }
    
    /**
     * 计算乘积
     */
    fun product(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        var result = 1.0
        for (value in data) {
            result *= value
        }
        return result
    }
    
    /**
     * 计算几何平均数
     */
    fun geometricMean(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        val product = product(data)
        return MathFunctions.pow(product, 1.0 / data.size)
    }
    
    /**
     * 计算调和平均数
     */
    fun harmonicMean(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        var sumInverse = 0.0
        for (value in data) {
            if (value == 0.0) {
                throw ArithmeticException("Cannot compute harmonic mean when value is zero")
            }
            sumInverse += 1.0 / value
        }
        return data.size / sumInverse
    }
    
    /**
     * 计算平方和
     */
    fun sumOfSquares(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        var sum = 0.0
        for (value in data) {
            sum += value.pow(2)
        }
        return sum
    }
    
    /**
     * 计算标准偏差
     */
    fun standardError(data: List<Double>): Double {
        if (data.isEmpty()) {
            throw IllegalArgumentException("Data list cannot be empty")
        }
        
        val stdDev = standardDeviation(data)
        return stdDev / sqrt(data.size.toDouble())
    }
    
    /**
     * 计算协方差
     */
    fun covariance(dataX: List<Double>, dataY: List<Double>): Double {
        if (dataX.isEmpty() || dataY.isEmpty()) {
            throw IllegalArgumentException("Data lists cannot be empty")
        }
        if (dataX.size != dataY.size) {
            throw IllegalArgumentException("Data lists must have the same size")
        }
        
        val meanX = mean(dataX)
        val meanY = mean(dataY)
        
        var covariance = 0.0
        for (i in 0 until dataX.size) {
            covariance += (dataX[i] - meanX) * (dataY[i] - meanY)
        }
        return covariance / dataX.size
    }
    
    /**
     * 计算相关系数
     */
    fun correlation(dataX: List<Double>, dataY: List<Double>): Double {
        if (dataX.isEmpty() || dataY.isEmpty()) {
            throw IllegalArgumentException("Data lists cannot be empty")
        }
        if (dataX.size != dataY.size) {
            throw IllegalArgumentException("Data lists must have the same size")
        }
        
        val covariance = covariance(dataX, dataY)
        val stdDevX = standardDeviation(dataX)
        val stdDevY = standardDeviation(dataY)
        
        if (stdDevX == 0.0 || stdDevY == 0.0) {
            throw ArithmeticException("Standard deviation cannot be zero")
        }
        
        return covariance / (stdDevX * stdDevY)
    }
    
    /**
     * 回归分析
     */
    fun regression(dataX: List<Double>, dataY: List<Double>): RegressionResult {
        if (dataX.isEmpty() || dataY.isEmpty()) {
            throw IllegalArgumentException("Data lists cannot be empty")
        }
        if (dataX.size != dataY.size) {
            throw IllegalArgumentException("Data lists must have the same size")
        }
        
        val n = dataX.size
        val sumX = sum(dataX)
        val sumY = sum(dataY)
        val sumXY = dataX.zip(dataY).sumOf { it.first * it.second }
        val sumXX = sumOfSquares(dataX)
        val sumYY = sumOfSquares(dataY)
        
        val slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX.pow(2))
        val intercept = (sumY - slope * sumX) / n
        
        return RegressionResult(slope, intercept)
    }
    
    /**
     * 回归结果类
     */
    data class RegressionResult(val slope: Double, val intercept: Double)
}