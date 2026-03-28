package com.calculator.advanced

/**
 * 表达式解析器 - 负责解析和计算数学表达式
 */
class ExpressionParser {
    
    private val operators = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "^" to 3,
        "!" to 4
    )
    
    private val functions = mapOf(
        "sin" to FunctionType.TRIGONOMETRY,
        "cos" to FunctionType.TRIGONOMETRY,
1        "tan" to FunctionType.TRIGONOMETRY,
        "asin" to FunctionType.TRIGONOMETRY,
        "acos" to FunctionType.TRIGONOMETRY,
        "atan" to FunctionType.TRIGONOMETRY,
        "log" to FunctionType.LOGARITHM,
        "ln" to FunctionType.LOGARITHM,
        "sqrt" to FunctionType.POWER,
        "abs" to FunctionType.ARITHMETIC,
        "factorial" to FunctionType.ARITHMETIC
    )
    
    enum class FunctionType {
        TRIGONOMETRY,
        LOGARITHM,
        POWER,
        ARITHMETIC,
        CUSTOM
    }
    
    /**
     * 解析表达式并计算结果
     */
    fun parseAndEvaluate(expression: String): Double {
        try {
            // 验证表达式长度
            if (expression.length > AppConfig.MAX_EXPRESSION_LENGTH) {
                throw IllegalArgumentException("Expression too long")
            }
            
            // 清除空白字符
            val cleanExpression = expression.replace(" ", "")
            
            // 检查括号匹配
            if (!validateParentheses(cleanExpression)) {
                throw IllegalArgumentException("Invalid parentheses")
            }
            
            // 转换为RPN（逆波兰表达式）
            val rpn = toRPN(cleanExpression)
            
            // 计算RPN
            return evaluateRPN(rpn)
        } catch (e: Exception) {
            throw CalculationException("Error parsing expression: ${e.message}", e)
        }
    }
    
    /**
     * 验证括号是否匹配
     */
    private fun validateParentheses(expression: String): Boolean {
        var count = 0
        for (char in expression) {
            if (char == '(') count++
            if (char == ')') count--
            if (count < 0) return false
        }
        return count == 0
    }
    
    /**
     * 转换为逆波兰表达式
     */
    private fun toRPN(expression: String): List<String> {
        val output = mutableListOf<String>()
        val stack = mutableListOf<String>()
        
        val tokens = tokenize(expression)
        
        for (token in tokens) {
            if (token.isNumber()) {
                output.add(token)
            } else if (token.isFunction()) {
                stack.add(token)
            } else if (token == "(") {
                stack.add(token)
            } else if (token == ")") {
                while (stack.isNotEmpty() && stack.last() != "(") {
                    output.add(stack.removeLast())
                }
                if (stack.isEmpty() || stack.last() != "(") {
                    throw IllegalArgumentException("Unmatched parentheses")
                }
                stack.removeLast()
                if (stack.isNotEmpty() && stack.last().isFunction()) {
                    output.add(stack.removeLast())
                }
            } else if (token.isOperator()) {
                while (stack.isNotEmpty() && 
                       stack.last().isOperator() && 
                       precedence(stack.last()) >= precedence(token)) {
                    output.add(stack.removeLast())
                }
                stack.add(token)
            }
        }
        
        while (stack.isNotEmpty()) {
            val operator = stack.removeLast()
            if (operator == "(" || operator == ")") {
                throw IllegalArgumentException("Unmatched parentheses")
            }
            output.add(operator)
        }
        
        return output
    }
    
    /**
     * 计算逆波兰表达式
     */
    private fun evaluateRPN(rpn: List<String>): Double {
        val stack = mutableListOf<Double>()
        
        for (token in rpn) {
            if (token.isNumber()) {
                stack.add(token.toDouble())
            } else if (token.isOperator()) {
                if (stack.size < 2) {
                    throw IllegalArgumentException("Insufficient operands for operator")
                }
                val b = stack.removeLast()
                val a = stack.removeLast()
                stack.add(applyOperator(token, a, b))
            } else if (token.isFunction()) {
                if (stack.isEmpty()) {
                    throw IllegalArgumentException("Insufficient operands for function")
                }
                val operand = stack.removeLast()
                stack.add(applyFunction(token, operand))
            }
        }
        
        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid expression format")
        }
        
        return stack[0]
    }
    
    /**
     * 将表达式拆分为token
     */
    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var index = 0
        
        while (index < expression.length) {
            val char = expression[index]
            
            if (char.isDigit() || char == '.' || char == '-' && (index == 0 || expression[index - 1].isOperator() || expression[index - 1] == '(')) {
                // 处理数字
                var number = ""
                if (char == '-' && (index == 0 || expression[index - 1].isOperator() || expression[index - 1] == '(')) {
                    number = "-"
                    index++
                }
                
                while (index < expression.length && 
                       (expression[index].isDigit() || expression[index] == '.' || expression[index] == 'E' || expression[index] == 'e')) {
                    number += expression[index]
                    index++
                    
                    if (index < expression.length && expression[index] == '-' && (number.contains('E') || number.contains('e'))) {
                        number += expression[index]
                        index++
                    }
                }
                tokens.add(number)
            } else if (char.isLetter()) {
                // 处理函数或变量
                var function = ""
                while (index < expression.length && expression[index].isLetter()) {
                    function += expression[index]
                    index++
                }
                tokens.add(function)
            } else if (char == '(' || char == ')') {
                tokens.add(char.toString())
                index++
            } else if (char.isOperator() || char == '!' || char == '^') {
                tokens.add(char.toString())
                index++
            } else {
                throw IllegalArgumentException("Invalid character '$char' in expression")
            }
        }
        
        return tokens
    }
    
    /**
     * 应用运算符
     */
    private fun applyOperator(operator: String, a: Double, b: Double): Double {
        when (operator) {
            "+" -> return a + b
            "-" -> return a - b
            "*" -> return a * b
            "/" -> {
                if (b == 0.0) throw ArithmeticException("Division by zero")
                return a / b
            }
            "^" -> return MathFunctions.pow(a, b)
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }
    
    /**
     * 应用函数
     */
    private fun applyFunction(function: String, operand: Double): Double {
        when (function.toLowerCase()) {
            "sin" -> return MathFunctions.sin(operand)
            "cos" -> return MathFunctions.cos(operand)
            "tan" -> return MathFunctions.tan(operand)
            "asin" -> return MathFunctions.asin(operand)
            "acos" -> return MathFunctions.acos(operand)
            "atan" -> return MathFunctions.atan(operand)
            "log" -> return MathFunctions.log(operand)
            "ln" -> return MathFunctions.ln(operand)
            "sqrt" -> return MathFunctions.sqrt(operand)
            "abs" -> return MathFunctions.absolute(operand)
            "factorial" -> {
                val n = operand.toLong()
                if (n < 0) throw IllegalArgumentException("Factorial requires non-negative input")
                if (n > 20) throw IllegalArgumentException("Factorial overflow")
                return MathFunctions.factorial(n).toDouble()
            }
            else -> throw IllegalArgumentException("Unknown function: $function")
        }
    }
    
    /**
     * 获取运算符优先级
     */
    private fun precedence(operator: String): Int {
        return operators[operator] ?: 0
    }
    
    /**
     * 扩展函数检查
     */
    private fun String.isNumber(): Boolean {
        return this.matches("^-?[0-9]+(\\.[0-9]+)?([Ee]-?[0-9]+)?$")
    }
    
    private fun String.isOperator(): Boolean {
        return operators.containsKey(this)
    }
    
    private fun Char.isOperator(): Boolean {
        return operators.containsKey(this.toString())
    }
    
    private fun String.isFunction(): Boolean {
        return functions.containsKey(this.toLowerCase())
    }
    
    /**
     * 计算异常类
     */
    class CalculationException(message: String, cause: Exception? = null) : Exception(message, cause)
}