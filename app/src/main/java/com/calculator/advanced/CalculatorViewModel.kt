package com.calculator.advanced

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * 计算器视图模型
 */
class CalculatorViewModel : ViewModel() {
    
    // 状态管理
    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression
    
    private val _result = MutableStateFlow("0")
    val result: StateFlow<String> = _result
    
    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history
    
    private val _mode = MutableStateFlow(AppConfig.CalculatorMode.BASIC)
    val mode: StateFlow<AppConfig.CalculatorMode> = _mode
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    // 数学库实例
    private val mathFunctions = MathFunctions
    private val expressionParser = ExpressionParser()
    private val matrixCalculator = MatrixCalculator()
    private val statisticsCalculator = StatisticsCalculator()
    private val unitConverter = UnitConverter()
    private val engineeringCalculator = EngineeringCalculator()
    
    /**
     * 历史记录项
     */
    data class HistoryItem(
        val expression: String,
        val result: String,
        val timestamp: Long
    )
    
    /**
     * 添加数字到表达式
     */
    fun addNumber(number: String) {
        _expression.value += number
    }
    
    /**
     * 添加运算符到表达式
     */
    fun addOperator(operator: String) {
        _expression.value += operator
    }
    
    /**
     * 添加函数到表达式
     */
    fun addFunction(function: String) {
        _expression.value += function + "("
    }
    
    /**
     * 清除表达式
     */
    fun clearExpression() {
        _expression.value = ""
        _result.value = "0"
        _error.value = null
    }
    
    /**
     * 回退一个字符
     */
    fun backspace() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
        }
    }
    
    /**
     * 计算表达式
     */
    fun calculate() {
        viewModelScope.launch {
            try {
                if (_expression.value.isEmpty()) {
                    _result.value = "0"
                    return
                }
                
                // 解析表达式并计算结果
                val resultValue = expressionParser.parseAndEvaluate(_expression.value)
                _result.value = mathFunctions.formatNumber(resultValue)
                
                // 添加到历史记录
                addToHistory(_expression.value, _result.value)
                _error.value = null
            } catch (e: ExpressionParser.CalculationException) {
                _error.value = e.message
                _result.value = "Error"
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
                _result.value = "Error"
            }
        }
    }
    
    /**
     * 添加到历史记录
     */
    private fun addToHistory(expr: String, result: String) {
        val newHistory = _history.value + HistoryItem(
            expression = expr,
            result = result,
            timestamp = System.currentTimeMillis()
        )
        
        // 限制历史记录数量
        if (newHistory.size > AppConfig.MAX_HISTORY_SIZE) {
            _history.value = newHistory.drop(1)
        } else {
            _history.value = newHistory
        }
    }
    
    /**
     * 清除历史记录
     */
    fun clearHistory() {
        _history.value = emptyList()
    }
    
    /**
     * 更改计算模式
     */
    fun changeMode(newMode: AppConfig.CalculatorMode) {
        _mode.value = newMode
    }
    
    /**
     * 执行高级数学函数
     */
    fun executeMathFunction(functionName: String, input: Double): String {
        try {
            when (functionName) {
                "sin" -> return mathFunctions.formatNumber(mathFunctions.sin(input))
                "cos" -> return mathFunctions.formatNumber(mathFunctions.cos(input))
                "tan" -> return mathFunctions.formatNumber(mathFunctions.tan(input))
                "asin" -> return mathFunctions.formatNumber(mathFunctions.asin(input))
                "acos" -> return mathFunctions.formatNumber(mathFunctions.acos(input))
                "atan" -> return mathFunctions.formatNumber(mathFunctions.atan(input))
                "log" -> return mathFunctions.formatNumber(mathFunctions.log(input))
                "ln" -> return mathFunctions.formatNumber(mathFunctions.ln(input))
                "exp" -> return mathFunctions.formatNumber(mathFunctions.exp(input))
                "sqrt" -> return mathFunctions.formatNumber(mathFunctions.sqrt(input))
                "factorial" -> {
                    val n = input.toLong()
                    return mathFunctions.formatNumber(mathFunctions.factorial(n).toDouble())
                }
                else -> return "Invalid function"
            }
        } catch (e: Exception) {
            return "Error: ${e.message}"
        }
    }
    
    /**
     * 执行统计函数
     */
    fun executeStatisticsFunction(functionName: String, data: List<Double>): String {
        try {
            when (functionName) {
                "mean" -> return mathFunctions.formatNumber(statisticsCalculator.mean(data))
                "variance" -> return mathFunctions.formatNumber(statisticsCalculator.variance(data))
                "stdDev" -> return mathFunctions.formatNumber(statisticsCalculator.standardDeviation(data))
                "median" -> return mathFunctions.formatNumber(statisticsCalculator.median(data))
                "sum" -> return mathFunctions.formatNumber(statisticsCalculator.sum(data))
                else -> return "Invalid function"
            }
        } catch (e: Exception) {
            return "Error: ${e.message}"
0        }
    }
    
    /**
     * 执行工程函数
     */
    fun executeEngineeringFunction(functionName: String, params: Map<String, Double>): String {
        try {
            when (functionName) {
                "parallelResistance" -> {
                    val resistors = params.values.toList()
                    return mathFunctions.formatNumber(engineeringCalculator.parallelResistance(resistors))
                }
                "seriesResistance" -> {
                    val resistors = params.values.toList()
                    return mathFunctions.formatNumber(engineeringCalculator.seriesResistance(resistors))
                }
                "rcTime" -> {
                    val resistance = params["resistance"] ?: 0.0
                    val capacitance = params["capacitance"] ?: 0.0
                    return mathFunctions.formatNumber(engineeringCalculator.rcTimeConstant(resistance, capacitance))
                }
                "rlTime" -> {
                    val resistance = params["resistance"] ?: 0.0
                    val inductance = params["inductance"] ?: 0.0
                    return mathFunctions.formatNumber(engineeringCalculator.rlTimeConstant(resistance, inductance))
                }
                else -> return "Invalid function"
            }
        } catch (e: Exception) {
            return "Error: ${e.message}"
        }
    }
}