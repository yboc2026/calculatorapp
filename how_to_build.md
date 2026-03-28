# 如何构建和运行高级计算器应用

## 构建步骤

### 1. 使用Android Studio
1. **安装Android Studio**：
   - 下载最新版本的Android Studio
   - 确保安装SDK 34或更高版本

2. **导入项目**：
   - 打开Android Studio
   - 选择"Import Project"
   - 选择`CalculatorApp`文件夹
   - 等待gradle同步完成

3. **编译和运行**：
   - 点击工具栏中的"Build"按钮
   - 编译成功后，点击"Run"按钮
   - 选择连接的安卓设备或模拟器

### 2. 命令行构建
```bash
# 进入项目目录
cd CalculatorApp

# 清理项目
gradlew clean

# 构建项目
gradlew build

# 生成APK文件
gradlew assembleRelease

# 安装到设备
gradlew installDebug
```

### 3. 使用模拟器
1. **安装模拟器**：
   - 在Android Studio中安装Android模拟器
   - 选择API 34或更高版本

2. **启动模拟器**：
   - 在Android Studio中启动模拟器
   - 等待模拟器完全启动

3. **运行应用**：
   - 选择模拟器作为目标设备
   - 点击"Run"按钮安装和运行应用

## 项目配置

### 依赖项检查
确保项目中包含以下依赖：
1. **Compose**：最新版本的Jetpack Compose
2. **Material Design**：Material 3组件
3. **Kotlin**：Kotlin 1.9.0或更高版本
4. **Android SDK**：API 34或更高版本

### 编译选项
1. **优化**：启用代码优化和资源压缩
2. **调试**：保留调试信息
3. **签名**：添加数字签名用于发布

## 测试应用

### 功能测试
测试以下功能是否正常工作：
1. **基础运算**：加减乘除
2. **括号运算**：处理优先级
3. **科学函数**：三角函数、对数等
4. **高级功能**：矩阵、复数运算
5. **模式切换**：切换不同计算模式

### UI测试
验证以下UI特性：
1. **布局正确**：按钮排列合理
2. **响应速度**：点击响应无延迟
3. **错误处理**：显示合适的错误提示
4. **主题切换**：确保主题正常工作

## 发布准备

### 生成APK
```bash
# 生成发布APK
gradlew assembleRelease

# APK位置
CalculatorApp/app/build/outputs/apk/release/app-release.apk
```

### 签名配置
如果需要发布到Play Store：
1. 创建签名密钥
2. 配置gradle签名信息
3. 生成签名APK

## 常见问题

### 编译错误
- **gradle同步失败**：检查gradle版本和依赖项
- **API版本不匹配**：确保SDK版本正确
- **Java版本错误**：设置Java 17或更高版本

### 运行问题
- **模拟器问题**：重启模拟器或重新安装
- **设备兼容性**：确保minSdk设置为24
- **权限问题**：添加必要的权限配置

## 项目文件
所有所需文件已在`CalculatorApp`目录中创建完成，包括：
1. 源代码
2. 配置文件
3. Gradle文件
4. 资源文件

应用已准备完毕，可以直接导入Android Studio进行编译和运行。