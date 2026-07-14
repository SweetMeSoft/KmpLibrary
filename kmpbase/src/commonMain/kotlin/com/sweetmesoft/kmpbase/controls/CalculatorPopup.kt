package com.sweetmesoft.kmpbase.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sweetmesoft.kmpbase.theme.errorColor

@Composable
fun CalculatorPopup(
    visible: Boolean = false, onDismissRequest: () -> Unit = {}, onResult: (Double) -> Unit
) {
    var total: String by remember { mutableStateOf("0") }
    var subtotal: String by remember { mutableStateOf("0") }
    if (visible) {
        Dialog(
            onDismissRequest = {
                onDismissRequest()
            }, properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.extraLarge
                )
            ) {
                Text(
                    text = total,
                    fontSize = 36.sp,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    textAlign = TextAlign.End
                )

                Text(
                    text = formatSubtotal(subtotal),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                HorizontalDivider()

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), 'C') {
                        total = "0"
                        subtotal = "0"
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '←') {
                        if (subtotal.length > 1) {
                            subtotal = subtotal.dropLast(1)
                        } else {
                            subtotal = "0"
                        }
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '%') {
                        subtotal = applyPercent(subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.primaryContainer), '/'
                    ) {
                        subtotal = appendOperator(it, subtotal)
                        total = operate(subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '7') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '8') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '9') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.primaryContainer), '*'
                    ) {
                        subtotal = appendOperator(it, subtotal)
                        total = operate(subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '4') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '5') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '6') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.primaryContainer), '-'
                    ) {
                        subtotal = appendOperator(it, subtotal)
                        total = operate(subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '1') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '2') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '3') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.primaryContainer), '+'
                    ) {
                        subtotal = appendOperator(it, subtotal)
                        total = operate(subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '.') {
                        subtotal = appendDecimal(subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '0') {
                        subtotal = appendDigit(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '»') {
                        subtotal = total.replace(",", "")
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(bottomEnd = 16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer), '='
                    ) {
                        val cleanTotal = total.replace(",", "")
                        onResult(cleanTotal.toDoubleOrNull() ?: 0.0)
                    }
                }
            }
        }
    }
}

private fun appendDigit(char: Char, currentExpr: String): String {
    if (currentExpr == "0") {
        return char.toString()
    }
    if (currentExpr.length >= 2) {
        val lastChar = currentExpr.last()
        val secondLastChar = currentExpr[currentExpr.length - 2]
        val isOperator = secondLastChar == '+' || secondLastChar == '-' || secondLastChar == '*' || secondLastChar == '/'
        if (lastChar == '0' && isOperator) {
            return currentExpr.dropLast(1) + char
        }
    }
    return currentExpr + char
}

private fun appendDecimal(currentExpr: String): String {
    if (currentExpr.isEmpty()) {
        return "0."
    }
    if (currentExpr == "0") {
        return "0."
    }
    val lastOperatorIndex = currentExpr.lastIndexOfAny(charArrayOf('+', '-', '*', '/'))
    val currentNumber = if (lastOperatorIndex == -1) {
        currentExpr
    } else {
        currentExpr.substring(lastOperatorIndex + 1)
    }
    if (currentNumber.contains('.')) {
        return currentExpr
    }
    if (currentNumber.isEmpty()) {
        return currentExpr + "0."
    }
    return currentExpr + "."
}

private fun appendOperator(char: Char, currentExpr: String): String {
    if (currentExpr.isEmpty()) {
        return "0" + char
    }
    val lastChar = currentExpr.last()
    val expr = if (lastChar == '.') currentExpr.dropLast(1) else currentExpr
    val newLastChar = expr.last()
    if (newLastChar == '+' || newLastChar == '-' || newLastChar == '*' || newLastChar == '/') {
        return expr.dropLast(1) + char
    }
    return expr + char
}

private fun applyPercent(currentExpr: String): String {
    val lastOperatorIndex = currentExpr.lastIndexOfAny(charArrayOf('+', '-', '*', '/'))
    if (lastOperatorIndex <= 0) {
        val number = currentExpr.toDoubleOrNull() ?: 0.0
        return formatDoubleToString(number / 100.0)
    }
    val lastOperator = currentExpr[lastOperatorIndex]
    val lastNumberStr = currentExpr.substring(lastOperatorIndex + 1)
    val lastNumber = lastNumberStr.toDoubleOrNull() ?: 0.0
    val possibleTotalExpr = currentExpr.take(lastOperatorIndex)
    
    val percentage = lastNumber / 100.0
    val evaluatedTotal = evaluateSubExpression(possibleTotalExpr)
    
    val newNumber = when (lastOperator) {
        '+' -> evaluatedTotal * percentage
        '-' -> evaluatedTotal * percentage
        '*' -> percentage
        '/' -> percentage
        else -> 0.0
    }
    return currentExpr.take(lastOperatorIndex + 1) + formatDoubleToString(newNumber)
}

private fun evaluateSubExpression(expression: String): Double {
    try {
        var tempExpr = expression.trim()
        while (tempExpr.isNotEmpty() && (tempExpr.endsWith("+") || tempExpr.endsWith("-") || tempExpr.endsWith("*") || tempExpr.endsWith("/") || tempExpr.endsWith("."))) {
            tempExpr = tempExpr.dropLast(1)
        }
        if (tempExpr.isEmpty()) return 0.0
        val tokens = tokenize(tempExpr.replace(" ", ""))
        if (tokens.isEmpty()) return 0.0
        return parseExpression(tokens)
    } catch (e: Exception) {
        return 0.0
    }
}

private fun formatDoubleToString(value: Double): String {
    val str = expandScientificNotation(value.toString())
    if (str.endsWith(".0")) {
        return str.dropLast(2)
    }
    return str
}

private fun expandScientificNotation(str: String): String {
    if (!str.contains("E") && !str.contains("e")) {
        return str
    }
    val parts = if (str.contains("E")) str.split("E") else str.split("e")
    val significand = parts[0]
    val exponent = parts[1].toIntOrNull() ?: return str
    
    if (exponent == 0) return significand
    
    val isNegative = significand.startsWith("-")
    val cleanSignificand = if (isNegative) significand.substring(1) else significand
    
    val sigParts = cleanSignificand.split('.')
    val sigInt = sigParts[0]
    val sigDec = if (sigParts.size > 1) sigParts[1] else ""
    
    val result = if (exponent > 0) {
        if (exponent < sigDec.length) {
            val movedDec = sigDec.substring(0, exponent)
            val remainingDec = sigDec.substring(exponent)
            sigInt + movedDec + "." + remainingDec
        } else {
            val zerosNeeded = exponent - sigDec.length
            sigInt + sigDec + "0".repeat(zerosNeeded)
        }
    } else {
        val absExponent = kotlin.math.abs(exponent)
        if (absExponent <= sigInt.length) {
            val splitIndex = sigInt.length - absExponent
            val movedInt = sigInt.substring(splitIndex)
            val remainingInt = sigInt.substring(0, splitIndex)
            val prefix = if (remainingInt.isEmpty()) "0" else remainingInt
            prefix + "." + movedInt + sigDec
        } else {
            val zerosNeeded = absExponent - sigInt.length
            "0." + "0".repeat(zerosNeeded) + sigInt + sigDec
        }
    }
    
    val finalResult = if (result.contains('.')) {
        val rParts = result.split('.')
        val dec = rParts[1].dropLastWhile { it == '0' }
        if (dec.isEmpty()) rParts[0] else rParts[0] + "." + dec
    } else {
        result
    }
    
    return if (isNegative) "-$finalResult" else finalResult
}

private fun formatSubtotal(expression: String): String {
    val sb = StringBuilder()
    val currentNum = StringBuilder()
    
    fun flushNumber() {
        if (currentNum.isNotEmpty()) {
            sb.append(formatNumberString(currentNum.toString()))
            currentNum.clear()
        }
    }
    
    for (char in expression) {
        if (char.isDigit() || char == '.') {
            currentNum.append(char)
        } else {
            flushNumber()
            sb.append(char)
        }
    }
    flushNumber()
    return sb.toString()
}

private fun formatNumberString(numStr: String): String {
    val parts = numStr.split('.')
    val intPart = parts[0]
    
    val sb = StringBuilder()
    var count = 0
    for (i in intPart.length - 1 downTo 0) {
        sb.append(intPart[i])
        count++
        if (count % 3 == 0 && i > 0) {
            sb.append(',')
        }
    }
    val formattedInt = sb.reverse().toString()
    
    return if (parts.size > 1) {
        formattedInt + "." + parts[1]
    } else {
        formattedInt
    }
}

private fun formatTotal(value: Double): String {
    if (value.isNaN() || value.isInfinite()) return value.toString()
    val str = expandScientificNotation(value.toString())
    val parts = str.split('.')
    val intPart = parts[0]
    val decPart = if (parts.size > 1) parts[1] else ""
    
    val isNegative = intPart.startsWith("-")
    val cleanIntPart = if (isNegative) intPart.substring(1) else intPart
    
    val sb = StringBuilder()
    var count = 0
    for (i in cleanIntPart.length - 1 downTo 0) {
        sb.append(cleanIntPart[i])
        count++
        if (count % 3 == 0 && i > 0) {
            sb.append(',')
        }
    }
    val formattedInt = sb.reverse().toString()
    val signedInt = if (isNegative) "-$formattedInt" else formattedInt
    
    val cleanedDec = decPart.dropLastWhile { it == '0' }
    return if (cleanedDec.isNotEmpty()) {
        "$signedInt.$cleanedDec"
    } else {
        signedInt
    }
}

private fun operate(expression: String): String {
    try {
        var tempExpr = expression.trim()
        while (tempExpr.isNotEmpty() && (tempExpr.endsWith("+") || tempExpr.endsWith("-") || tempExpr.endsWith("*") || tempExpr.endsWith("/") || tempExpr.endsWith("."))) {
            tempExpr = tempExpr.dropLast(1)
        }
        if (tempExpr.isEmpty()) {
            return "0"
        }
        var sanitizedExpression = tempExpr.replace(" ", "")
        if (sanitizedExpression.startsWith("-") || sanitizedExpression.startsWith("+")) {
            sanitizedExpression = "0" + sanitizedExpression
        }
        val tokens = tokenize(sanitizedExpression)
        if (tokens.isEmpty()) return "0"
        return formatTotal(parseExpression(tokens))
    } catch (e: Exception) {
        return "0"
    }
}

private fun tokenize(expression: String): List<String> {
    val regex = Regex("([+\\-*/()])|([0-9]+(?:\\.[0-9]+)?)")
    return regex.findAll(expression).map { it.value }.toList()
}

private fun parseExpression(tokens: List<String>): Double {
    val stack = mutableListOf<Double>()
    val operators = mutableListOf<Char>()
    var index = 0

    fun applyOperator() {
        if (stack.size < 2) return
        val right = stack.removeAt(stack.lastIndex)
        val left = stack.removeAt(stack.lastIndex)
        val operator = operators.removeAt(operators.lastIndex)
        stack.add(
            when (operator) {
                '+' -> left + right
                '-' -> left - right
                '*' -> left * right
                '/' -> left / right
                else -> throw IllegalArgumentException("Operador no soportado")
            }
        )
    }

    while (index < tokens.size) {
        val token = tokens[index]
        when {
            token.toDoubleOrNull() != null -> stack.add(token.toDouble())
            token == "+" || token == "-" -> {
                while (operators.isNotEmpty() && (operators.last() == '+' || operators.last() == '-' || operators.last() == '*' || operators.last() == '/')) {
                    applyOperator()
                }
                operators.add(token[0])
            }

            token == "*" || token == "/" -> {
                while (operators.isNotEmpty() && (operators.last() == '*' || operators.last() == '/')) {
                    applyOperator()
                }
                operators.add(token[0])
            }
        }
        index++
    }

    while (operators.isNotEmpty()) {
        applyOperator()
    }

    return if (stack.isNotEmpty()) stack.last() else 0.0
}

@Composable
private fun CalculatorButton(
    modifier: Modifier = Modifier, text: Char, onClick: (Char) -> Unit
) {
    Box(
        modifier = modifier.aspectRatio(1f).clickable { onClick(text) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.toString(),
            fontSize = 28.sp,
            color = if (text.isDigit()) MaterialTheme.colorScheme.onSurface else if (text == '=') errorColor else MaterialTheme.colorScheme.primary
        )
    }
}