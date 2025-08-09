package com.sweetmesoft.kmplibrary.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CalculatorPopup(
    visible: Boolean = false,
    onDismissRequest: () -> Unit = {},
    onResult: (Double) -> Unit
) {
    var total: String by remember { mutableStateOf("0") }
    var subtotal: String by remember { mutableStateOf("0") }
    if (visible) {
        Dialog(
            onDismissRequest = {
                //visible = false
                onDismissRequest()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
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
                    text = subtotal,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.End,
                    color = Color.Gray
                )

                Divider()

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), 'C') {
                        total = "0"
                        subtotal = "0"
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '←') {
                        subtotal = subtotal.substring(0, subtotal.length - 1)
                        if (subtotal.isEmpty()) {
                            subtotal = "0"
                        }
                        if (subtotal.last().isDigit()) {
                            total = operate(subtotal)
                        }
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '%') {
                        val lastOperator = subtotal.reversed().find { !it.isDigit() } ?: ' '
                        if (lastOperator != ' ') {
                            val lastNumber =
                                subtotal.substring(subtotal.lastIndexOf(lastOperator) + 1)
                            val possibleTotal =
                                subtotal.substring(0, subtotal.lastIndexOf(lastOperator))
                            val percentage = lastNumber.toDouble() / 100.0
                            val newNumber = when (lastOperator) {
                                '+' -> possibleTotal.toDouble() * percentage
                                '-' -> possibleTotal.toDouble() * percentage
                                '*' -> percentage
                                '/' -> percentage
                                else -> 0.0
                            }

                            subtotal = subtotal.substring(
                                0,
                                subtotal.lastIndexOf(lastOperator) + 1
                            ) + newNumber
                            total = operate(subtotal)
                        }
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.background), '/'
                    ) {
                        subtotal = addNumber(it, subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '7') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '8') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '9') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.background), '*'
                    ) {
                        subtotal = addNumber(it, subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '4') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '5') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '6') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.background), '-'
                    ) {
                        subtotal = addNumber(it, subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '1') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '2') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '3') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .background(MaterialTheme.colorScheme.background), '+'
                    ) {
                        subtotal = addNumber(it, subtotal)
                    }
                }

                Row {
                    CalculatorButton(modifier = Modifier.weight(1f), '.') {
                        subtotal = addNumber(it, subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '0') {
                        subtotal = addNumber(it, subtotal)
                        total = operate(subtotal)
                    }
                    CalculatorButton(modifier = Modifier.weight(1f), '»') {
                        subtotal = total
                    }
                    CalculatorButton(
                        modifier = Modifier.weight(1f)
                            .clip(RoundedCornerShape(bottomEnd = 16.dp))
                            .background(MaterialTheme.colorScheme.background), '='
                    ) {
                        onResult(total.toDouble())
                    }
                }
            }
        }
    }
}

private fun operate(expression: String): String {
    val sanitizedExpression = expression.replace(" ", "")
    val tokens = tokenize(sanitizedExpression)
    return parseExpression(tokens).toString()
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

    return stack.last()
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
            color = if (text.isDigit()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary
        )
    }
}

private fun addNumber(character: Char, subtotal: String): String {
    return if (subtotal == "0") {
        character.toString()
    } else {
        subtotal + character
    }
}