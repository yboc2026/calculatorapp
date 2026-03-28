# 高级计算器安卓应用项目结构

## 项目根目录
```
CalculatorApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/
│   │   │   │   ├── com/
│   │   │   │   │   ├── calculator/
│   │   │   │   │   │   ├── advanced/
│   │   │   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   │   │   ├── CalculatorViewModel.kt
│   │   │   │   │   │   │   ├── MathFunctions.kt
│   │   │   │   │   │   │   ├── ExpressionParser.kt
│   │   │   │   │   │   │   ├── ComplexNumber.kt
│   │   │   │   │   │   │   ├── MatrixCalculator.kt
│   │   │   │   │   │   │   ├── StatisticsCalculator.kt
│   │   │   │   │   │   │   ├── UnitConverter.kt
│   │   │   │   │   │   │   ├── EngineeringCalculator.kt
│   │   │   │   │   │   │   └── AppConfig.kt
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       │   ├── colors.xml
│   │   │       │   ├── strings.xml
│   │   │       │   ├── themes.xml
│   │   │       │   └── styles.xml
│   │   │       └── layout/ (Compose文件位置)
│   │   └── build.gradle.kts
├── gradle/
│   └── wrapper/
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
└── README.md
```

## 各文件功能说明

### 1. AndroidManifest.xml
- 应用配置
- 权限声明
- Activity定义

### 2. MainActivity.kt
- 主入口Activity
- Compose UI入口
- 生命周期管理

### 3. CalculatorViewModel.kt
- 计算器状态管理
- 业务逻辑处理
- 表达式解析调用

### 4. MathFunctions.kt
- 所有数学函数实现
- 三角函数、对数、指数等
- 错误处理和边界检查

### 5. ExpressionParser.kt
- 表达式解析引擎
- 运算符优先级处理
- 括号匹配校验

### 6. ComplexNumber.kt
- 复数运算实现
- 复数加减乘除
- 共轭和模计算

### 7. MatrixCalculator.kt
- 矩阵运算实现
- 行列式计算
- 逆矩阵计算

### 8. StatisticsCalculator.kt
- 统计函数实现
- 平均值、方差计算
- 标准差计算

### 9. UnitConverter.kt
- 单位转换功能
- 角度/弧度转换
- 进制转换

### 10. EngineeringCalculator.kt
- 工程计算函数
- 电阻电容计算
- 物理公式实现

### 11. AppConfig.kt
- 应用配置
- 常量定义
- 版本信息