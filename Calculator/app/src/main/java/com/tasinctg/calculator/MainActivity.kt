package com.tasinctg.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tasinctg.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    private lateinit var display: TextView
    private var currentInput: String = ""
    private var operator: String = ""
    private var firstOperand: Double = 0.0
    private var isNewInput: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.display) // Replace with your TextView's ID

        // Set up listeners for numeric buttons
        setNumericButtonListeners()

        // Set up listeners for operator buttons
        findViewById<Button>(R.id.button_add).setOnClickListener { handleOperator("+") }
        findViewById<Button>(R.id.button_subtract).setOnClickListener { handleOperator("-") }
        findViewById<Button>(R.id.button_multiply).setOnClickListener { handleOperator("×") }
        findViewById<Button>(R.id.button_divide).setOnClickListener { handleOperator("÷") }

        // Set up listeners for other buttons
        findViewById<Button>(R.id.button_clear).setOnClickListener { handleClear() }
        findViewById<Button>(R.id.button_equals).setOnClickListener { handleEquals() }
        findViewById<Button>(R.id.button_dot).setOnClickListener { handleDot() }
        findViewById<Button>(R.id.button_plus_minus).setOnClickListener { handlePlusMinus() }
        findViewById<Button>(R.id.button_percent).setOnClickListener { handlePercent() }
    }
    private fun setNumericButtonListeners() {
        val numericButtonIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9
        )

        for (id in numericButtonIds) {
            findViewById<Button>(id).setOnClickListener { handleNumericButton(it) }
        }
    }

    private fun handleNumericButton(view: View) {
        val button = view as Button
        if (isNewInput) {
            currentInput = button.text.toString()
            isNewInput = false
        } else {
            currentInput += button.text.toString()
        }
        display.text = currentInput
    }

    private fun handleOperator(newOperator: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
            operator = newOperator
            isNewInput = true
        }
    }

    private fun handleEquals() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "×" -> firstOperand * secondOperand
                "÷" -> if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN
                else -> 0.0
            }

            display.text = if (result.isNaN()) "Error" else result.toString()
            currentInput = result.toString()
            operator = ""
            isNewInput = true
        }
    }

    private fun handleClear() {
        currentInput = ""
        operator = ""
        firstOperand = 0.0
        isNewInput = true
        display.text = "0"
    }

    private fun handleDot() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            display.text = currentInput
        }
    }

    private fun handlePlusMinus() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble() * -1
            currentInput = value.toString()
            display.text = currentInput
        }
    }

    private fun handlePercent() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble() / 100
            currentInput = value.toString()
            display.text = currentInput
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Greeting("Android")
    }
}