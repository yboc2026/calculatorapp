package com.calculator.advanced

/**
 * 应用配置常量
 */
object AppConfig {
    // 数学常量
    const val PI = 3.14159265358979323846
    const val E = 2.71828182845904523536
    
    // 计算精度
    const val PRECISION = 15 // 小数位数
    
    // 最大表达式长度
    const val MAX_EXPRESSION_LENGTH = 1000
    
    // 矩阵最大尺寸
    const val MAX_MATRIX_SIZE = 10
    
    // 历史记录最大数量
    const val MAX_HISTORY_SIZE = 100
    
    // 按钮动画持续时间
    const val ANIMATION_DURATION = 200 // ms
    
    // 玻璃效果透明度
    const val GLASS_ALPHA = 0.8f
    
    // 按钮尺寸
    const val BUTTON_WIDTH = 72.dp
    const val BUTTON_HEIGHT = 72.dp
    
    // 显示区域字体大小
    const val DISPLAY_TEXT_SIZE_LARGE = 32.sp
    const val DISPLAY_TEXT_SIZE_MEDIUM = 24.sp
    const val DISPLAY_TEXT_SIZE_SMALL = 18.sp
    
    // 错误消息
    const val ERROR_MESSAGE_SYNTAX = "Syntax Error"
    const val ERROR_MESSAGE_MATH = "Math Error"
    const val ERROR_MESSAGE_OVERFLOW = "Overflow"
    const val ERROR_MESSAGE_DIVISION_ZERO = "Division by Zero"
    const val ERROR_MESSAGE_INVALID_FUNCTION = "Invalid Function"
    const val ERROR_MESSAGE_INVALID_INPUT = "Invalid Input"
    
    // 计算模式
    enum class CalculatorMode {
        BASIC,
        SCIENTIFIC,
        ADVANCED,
        MATRIX,
        COMPLEX,
        ENGINEERING
    }
}