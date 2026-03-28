package com.calculator.advanced

/**
 * 工程计算类
 */
object EngineeringCalculator {
    
    /**
     * 电阻计算
     */
    fun parallelResistance(resistors: List<Double>): Double {
        if (resistors.isEmpty()) {
            throw IllegalArgumentException("Resistor list cannot be empty")
        }
        return MathFunctions.parallelResistance(resistors)
    }
    
    fun seriesResistance(resistors: List<Double>): Double {
        if (resistors.isEmpty()) {
            throw IllegalArgumentException("Resistor list cannot be empty")
        }
        return MathFunctions.seriesResistance(resistors)
    }
    
    /**
     * 电容计算
     */
    fun parallelCapacitance(capacitors: List<Double>): Double {
        if (capacitors.isEmpty()) {
            throw IllegalArgumentException("Capacitor list cannot be empty")
        }
        return capacitors.sum()
    }
    
    fun seriesCapacitance(capacitors: List<Double>): Double {
        if (capacitors.isEmpty()) {
            throw IllegalArgumentException("Capacitor list cannot be empty")
        }
        val sumInverse = capacitors.map { 1.0 / it }.sum()
        return 1.0 / sumInverse
    }
    
    /**
     * 电感计算
     */
    fun parallelInductance(inductors: List<Double>): Double {
        if (inductors.isEmpty()) {
            throw IllegalArgumentException("Inductor list cannot be empty")
        }
        val sumInverse = inductors.map { 1.0 / it }.sum()
        return 1.0 / sumInverse
    }
    
    fun seriesInductance(inductors: List<Double>): Double {
        if (inductors.isEmpty()) {
            throw IllegalArgumentException("Inductor list cannot be empty")
        }
        return inductors.sum()
    }
    
    /**
     * RC时间常数
     */
    fun rcTimeConstant(resistance: Double, capacitance: Double): Double {
        return MathFunctions.capacitorTimeConstant(resistance, capacitance)
    }
    
    /**
     * RL时间常数
     */
    fun rlTimeConstant(resistance: Double, inductance: Double): Double {
        return MathFunctions.inductorTimeConstant(resistance, inductance)
    }
    
    /**
     * LC谐振频率
     */
    fun resonanceFrequency(inductance: Double, capacitance: Double): Double {
        return 1.0 / (2 * MathFunctions.pi() * sqrt(inductance * capacitance))
    }
    
    /**
     * 交流功率计算
     */
    fun acPower(voltage: Double, current: Double): Double {
        return voltage * current
    }
    
    fun acPowerWithPhase(voltage: Double, current: Double, phaseAngle: Double): Double {
        return voltage * current * cos(phaseAngle)
    }
    
    /**
     * 欧姆定律
     */
    fun ohmsLawVoltage(current: Double, resistance: Double): Double {
        return current * resistance
    }
    
    fun ohmsLawCurrent(voltage: Double, resistance: Double): Double {
        return voltage / resistance
    }
    
    fun ohmsLawResistance(voltage: Double, current: Double): Double {
        return voltage / current
    }
    
    /**
     * 功率计算
     */
    fun powerWithVoltageCurrent(voltage: Double, current: Double): Double {
        return voltage * current
    }
    
    fun powerWithResistanceCurrent(resistance: Double, current: Double): Double {
        return resistance * current.pow(2)
    }
    
    fun powerWithResistanceVoltage(resistance: Double, voltage: Double): Double {
        return voltage.pow(2) / resistance
    }
    
    /**
     * 分贝计算
     */
    fun decibel(powerRatio: Double): Double {
        return 10 * log10(powerRatio)
    }
    
    fun decibelWithVoltage(voltageRatio: Double): Double {
        return 20 * log10(voltageRatio)
    }
    
    fun decibelWithCurrent(currentRatio: Double): Double {
        return 20 * log10(currentRatio)
    }
    
    /**
     * 波特图计算
     */
    fun bodeDiagramFrequency(amplitude: Double): Double {
        return sqrt(abs(amplitude))
    }
    
    /**
     * 傅里叶变换系数计算
     */
    fun fourierCoefficient(amplitude: Double, frequency: Double): Double {
        return amplitude * cos(frequency)
    }
    
    /**
     * 数字信号处理计算
     */
    fun samplingRate(nyquistFrequency: Double): Double {
        return 2 * nyquistFrequency
    }
    
    fun nyquistFrequency(samplingRate: Double): Double {
        return samplingRate / 2
    }
    
    /**
     * 滤波器设计
     */
    fun lowPassFilterCutoff(resistance: Double, capacitance: Double): Double {
        return 1.0 / (2 * MathFunctions.pi() * resistance * capacitance)
    }
    
    fun highPassFilterCutoff(resistance: Double, capacitance: Double): Double {
        return 1.0 / (2 * MathFunctions.pi() * resistance * capacitance)
    }
    
    /**
     * 带宽计算
     */
    fun bandwidth(frequency1: Double, frequency2: Double): Double {
        return abs(frequency1 - frequency2)
    }
    
    /**
     * 增益计算
     */
    fun gain(input: Double, output: Double): Double {
        return output / input
    }
    
    /**
     * 相位计算
     */
    fun phaseAngle(timeDelay: Double, frequency: Double): Double {
        return 2 * MathFunctions.pi() * frequency * timeDelay
    }
}