package com.calculator.advanced

/**
 * 矩阵运算类
 */
class MatrixCalculator {
    
    /**
     * 创建矩阵
     * @param rows 行数
     * @param cols 列数
     * @param data 矩阵数据
     */
    class Matrix(val rows: Int, val cols: Int, val data: Array<DoubleArray>) {
        
        // 检查矩阵尺寸
        init {
            if (rows <= 0 || cols <= 0) {
                throw IllegalArgumentException("Matrix dimensions must be positive")
            }
            if (rows > AppConfig.MAX_MATRIX_SIZE || cols > AppConfig.MAX_MATRIX_SIZE) {
                throw IllegalArgumentException("Matrix size exceeds maximum limit")
            }
            if (data.size != rows || data[0].size != cols) {
                throw IllegalArgumentException("Data dimensions don't match matrix dimensions")
            }
        }
        
        // 加法
        fun add(other: Matrix): Matrix {
            if (this.rows != other.rows || this.cols != other.cols) {
                throw IllegalArgumentException("Matrix dimensions must match for addition")
            }
            
            val result = Array(this.rows) { DoubleArray(this.cols) }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result[i][j] = this.data[i][j] + other.data[i][j]
                }
            }
            return Matrix(rows, cols, result)
0        }
        
        // 减法
        fun subtract(other: Matrix): Matrix {
            if (this.rows != other.rows || this.cols != other.cols) {
                throw IllegalArgumentException("Matrix dimensions must match for subtraction")
            }
            
            val result = Array(this.rows) { DoubleArray(this.cols) }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result[i][j] = this.data[i][j] - other.data[i][j]
                }
            }
            return Matrix(rows, cols, result)
        }
        
        // 乘法
        fun multiply(other: Matrix): Matrix {
            if (this.cols != other.rows) {
                throw IllegalArgumentException("Matrix dimensions must match for multiplication: this.cols == other.rows")
            }
            
            val result = Array(this.rows) { DoubleArray(other.cols) }
            for (i in 0 until this.rows) {
                for (j in 0 until other.cols) {
                    var sum = 0.0
                    for (k in 0 until this.cols) {
                        sum += this.data[i][k] * other.data[k][j]
                    }
                    result[i][j] = sum
                }
            }
            return Matrix(this.rows, other.cols, result)
        }
        
        // 数乘
        fun scalarMultiply(scalar: Double): Matrix {
            val result = Array(this.rows) { DoubleArray(this.cols) }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result[i][j] = this.data[i][j] * scalar
                }
            }
            return Matrix(rows, cols, result)
        }
        
        // 行列式计算
        fun determinant(): Double {
            if (rows != cols) {
                throw IllegalArgumentException("Matrix must be square for determinant calculation")
            }
            
            return calculateDeterminant(data)
        }
        
        private fun calculateDeterminant(matrix: Array<DoubleArray>): Double {
            if (matrix.size == 1) return matrix[0][0]
            if (matrix.size == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
            
            var det = 0.0
            for (col in 0 until matrix.size) {
                val subMatrix = Array(matrix.size - 1) { DoubleArray(matrix.size - 1) }
                for (i in 1 until matrix.size) {
                    for (j in 0 until matrix.size) {
                        if (j != col) {
                            subMatrix[i-1][if (j < col) j else j-1] = matrix[i][j]
                        }
                    }
                }
                
                val sign = if (col % 2 == 0) 1.0 else -1.0
                det += sign * matrix[0][col] * calculateDeterminant(subMatrix)
            }
            return det
        }
        
        // 逆矩阵计算
        fun inverse(): Matrix {
            if (rows != cols) {
                throw IllegalArgumentException("Matrix must be square for inverse calculation")
            }
            
            val det = determinant()
            if (abs(det) < 1e-15) {
                throw ArithmeticException("Matrix is singular (determinant = 0)")
            }
            
            val n = rows
            val result = Array(n) { DoubleArray(n) }
            
            // 使用伴随矩阵法计算逆矩阵
            val adjoint = getAdjoint()
            
            for (i in 0 until n) {
                for (j in 0 until n) {
                    result[i][j] = adjoint[i][j] / det
                }
            }
            
            return Matrix(n, n, result)
        }
        
        private fun getAdjoint(): Array<DoubleArray> {
            val n = rows
            val adjoint = Array(n) { DoubleArray(n) }
            
            for (i in 0 until n) {
                for (j in 0 until n) {
                    val subMatrix = Array(n - 1) { DoubleArray(n - 1) }
                    
                    for (row in 0 until n) {
                        if (row == i) continue
                        for (col in 0 until n) {
                            if (col == j) continue
                            val subRow = if (row < i) row else row - 1
                            val subCol = if (col < j) col else col - 1
                            subMatrix[subRow][subCol] = data[row][col]
                        }
                    }
                    
                    val sign = if ((i + j) % 2 == 0) 1.0 else -1.0
                    adjoint[i][j] = sign * calculateDeterminant(subMatrix)
                }
            }
            
            return adjoint
        }
        
        // 转置
        fun transpose(): Matrix {
            val result = Array(cols) { DoubleArray(rows) }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result[j][i] = data[i][j]
                }
            }
            return Matrix(cols, rows, result)
        }
        
        // 格式化显示
        fun format(): String {
            val sb = StringBuilder()
            sb.append("Matrix($rows×$cols):\n")
            for (i in 0 until rows) {
                sb.append("[")
                for (j in 0 until cols) {
                    sb.append(MathFunctions.formatNumber(data[i][j]))
                    if (j < cols - 1) sb.append(", ")
                }
                sb.append("]\n")
            }
            return sb.toString()
        }
        
        // 获取指定元素
        fun get(row: Int, col: Int): Double {
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw IllegalArgumentException("Row or column index out of bounds")
            }
            return data[row][col]
        }
        
        // 设置指定元素
        fun set(row: Int, col: Int, value: Double) {
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw IllegalArgumentException("Row or column index out of bounds")
            }
            data[row][col] = value
        }
    }
    
    // 创建零矩阵
    fun zeros(rows: Int, cols: Int): Matrix {
        val data = Array(rows) { DoubleArray(cols) }
        return Matrix(rows, cols, data)
    }
    
    // 创建单位矩阵
    fun identity(size: Int): Matrix {
        val data = Array(size) { DoubleArray(size) }
        for (i in 0 until size) {
            data[i][i] = 1.0
        }
        return Matrix(size, size, data)
    }
    
    // 矩阵相加
    fun add(matrix1: Matrix, matrix2: Matrix): Matrix {
        return matrix1.add(matrix2)
    }
    
    // 矩阵相减
    fun subtract(matrix1: Matrix, matrix2: Matrix): Matrix {
        return matrix1.subtract(matrix2)
    }
    
    // 矩阵相乘
    fun multiply(matrix1: Matrix, matrix2: Matrix): Matrix {
        return matrix1.multiply(matrix2)
    }
    
    // 计算行列式
    fun determinant(matrix: Matrix): Double {
        return matrix.determinant()
    }
    
    // 计算逆矩阵
    fun inverse(matrix: Matrix): Matrix {
        return matrix.inverse()
    }
    
    // 矩阵转置
    fun transpose(matrix: Matrix): Matrix {
        return matrix.transpose()
    }
}