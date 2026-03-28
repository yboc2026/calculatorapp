package com.calculator.advanced

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * 主Activity
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedCalculatorTheme {
                CalculatorApp()
            }
        }
    }
}

/**
 * 主UI组件
 */
@Composable
fun CalculatorApp() {
    val viewModel = viewModel<CalculatorViewModel>()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 显示区域
            DisplayArea(viewModel)
            
            // 功能标签
            ModeSelector(viewModel)
            
            // 数字键盘
            NumberPad(viewModel)
            
            // 高级功能
            AdvancedFunctions(viewModel)
        }
    }
}

/**
 * 显示区域
 */
@Composable
fun DisplayArea(viewModel: CalculatorViewModel) {
    val expression by viewModel.expression.collectAsState()
    val result by viewModel.result.collectAsState()
    val error by viewModel.error.collectAsState()
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 表达式显示
            Text(
                text = expression,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 结果显示
            Text(
                text = if (error != null) "Error: $error" else result,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * 模式选择器
 */
@Composable
fun ModeSelector(viewModel: CalculatorViewModel) {
    val mode by viewModel.mode.collectAsState()
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CalculatorMode.values().forEach { currentMode ->
            Button(
                onClick = { viewModel.changeMode(currentMode) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (mode == currentMode) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
            ) {
                Text(
                    text = currentMode.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * 数字键盘
 */
@Composable
fun NumberPad(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 第一行：清除和运算按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.clearExpression() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("C")
            }
            
            Button(
                onClick = { viewModel.backspace() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("⌫")
            }
            
            Button(
                onClick = { viewModel.addOperator("+") }
            ) {
                Text("+")
            }
            
            Button(
                onClick = { viewModel.addOperator("-") }
            ) {
                Text("-")
            }
            
            Button(
                onClick = { viewModel.addOperator("*") }
            ) {
                Text("×")
            }
            
            Button(
                onClick = { viewModel.addOperator("/") }
            ) {
                Text("÷")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 数字按钮
        val numbers = listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ".", "=")
        
        for (row in 0..3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0..2) {
                    val index = row * 3 + col
                    if (index < numbers.size) {
                        Button(
                            onClick = {
                                if (numbers[index] == "=") {
                                    viewModel.calculate()
                                } else {
                                    viewModel.addNumber(numbers[index])
                                }
                            },
                            colors = if (numbers[index] == "=") {
                                ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                ButtonDefaults.buttonColors()
                            }
                        ) {
                            Text(numbers[index])
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * 高级功能
 */
@Composable
fun AdvancedFunctions(viewModel: CalculatorViewModel) {
    val mode by viewModel.mode.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when (mode) {
            AppConfig.CalculatorMode.SCIENTIFIC -> ScientificFunctions(viewModel)
            AppConfig.CalculatorMode.ADVANCED -> AdvancedMathFunctions(viewModel)
            AppConfig.CalculatorMode.MATRIX -> MatrixFunctions(viewModel)
            AppConfig.CalculatorMode.COMPLEX -> ComplexFunctions(viewModel)
            AppConfig.CalculatorMode.ENGINEERING -> EngineeringFunctions(view1Model)
            AppConfig.CalculatorMode.BASIC -> {} // 基础模式不显示高级功能
        }
    }
}

/**
 * 科学计算函数
 */
@Composable
fun ScientificFunctions(viewModel: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val functions = listOf(
            "sin", "cos", "tan",
            "asin", "acos", "atan",
            "log", "ln", "sqrt",
            "^", "abs", "n!"
        )
        
        for (function in functions) {
            Button(
                onClick = { viewModel.addFunction(function) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(function)
            }
        }
    }
}

/**
 * 高级数学函数
 */
@Composable
fun AdvancedMathFunctions(viewModel: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val functions = listOf(
            "π", "e", "deg/rad",
            "mean", "var", "std",
            "matrix", "complex", "integral"
        )
        
        for (function in functions) {
            Button(
                onClick = { viewModel.addFunction(function) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(function)
            }
        }
    }
}

/**
 * 矩阵功能
 */
@Composable
fun MatrixFunctions(viewModel: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val matrixOperations = listOf(
            "Matrix", "Determinant", "Inverse",
            "Add", "Multiply", "Transpose",
            "Zero", "Identity", "Custom"
        )
        
        for (operation in matrixOperations) {
            Button(
                onClick = { /* TODO: 实现矩阵操作 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(operation)
            }
        }
    }
}

/**
 * 复数功能
 */
@Composable
fun ComplexFunctions(viewModel: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val complexOperations = listOf(
            "ℂ", "+", "-",
            "*", "/", "Magnitude",
            "Phase", "Conjugate", "Power"
        )
        
        for (operation in complexOperations) {
            Button(
                onClick = { /* TODO: 实现复数操作 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(operation)
            }
        }
    }
}

/**
 * 工程功能
 */
@Composable
fun EngineeringFunctions(viewModel: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val engineeringOperations = listOf(
            "Resistance", "Capacitor", "Inductance",
            "RC Time", "RL Time", "dB",
            "Frequency", "Phase", "Power"
        )
        
        for (operation in engineeringOperations) {
            Button(
                onClick = { /* TODO: 实现工程计算 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(operation)
            }
        }
    }
}

/**
 * 应用主题
 */
@Composable
fun AdvancedCalculatorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme(
            primary = Color.Black,
            onPrimary = Color.White,
            primaryContainer = Color(0x80212121),
            onPrimaryContainer = Color.White,
            secondary = Color(0xFF404040),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFF2C2C2C),
            onSecondaryContainer = Color.White,
            tertiary = Color(0xFF2196F3),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFF0D8BF0),
            onTertiaryContainer = Color.White,
            background = Color(0xFF121212),
            onBackground = Color.White,
            surface = Color(0xAA121212),
            onSurface = Color.White,
            surfaceVariant = Color(0xAA303030),
            onSurfaceVariant = Color(0xFFB0B0B0),
            error = Color(0xFFFF5252),
            onError = Color.White,
            outline = Color(0xFF404040),
            outlineVariant = Color(0xFF606060)
        ),
        typography = Typography(),
        content = content
    )
}