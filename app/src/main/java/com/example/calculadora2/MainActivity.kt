package com.example.calculadora2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private val operationHistory = mutableListOf<String>()

    private var currentOperator: String? = null
    private var currentValue: Double? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = mutableListOf<Button>()
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("button$i", "id", packageName)
            val button = findViewById<Button>(buttonId)
            buttons.add(button)
            button.setOnClickListener { onNumberClick(it) }
        }

        val addButton = findViewById<Button>(R.id.buttonAdd)
        addButton.setOnClickListener { onOperatorClick(it) }

        val subtractButton = findViewById<Button>(R.id.buttonSubtract)
        subtractButton.setOnClickListener { onOperatorClick(it) }

        val multiplyButton = findViewById<Button>(R.id.buttonMultiply)
        multiplyButton.setOnClickListener { onOperatorClick(it) }

        val divButton = findViewById<Button>(R.id.buttonDiv)
        divButton.setOnClickListener { onOperatorClick(it) }

        val equalsButton = findViewById<Button>(R.id.buttonEquals)
        equalsButton.setOnClickListener { onEqualsClick() }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener { onClearClick() }

        val historyButton = findViewById<Button>(R.id.historyButton)
        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putStringArrayListExtra("operationHistory", ArrayList(operationHistory))
            startActivity(intent)
        }
    }

    private fun onNumberClick(view: View) {
        val button = view as Button
        display.append(button.text)
    }

    @SuppressLint("SetTextI18n")
    private fun onOperatorClick(view: View) {
        val button = view as Button
        val displayValue = display.text.toString().toDoubleOrNull()

        if (displayValue != null) {
            currentOperator = button.text.toString()
            currentValue = displayValue
            display.text = "$currentValue$currentOperator"
        }
    }

    private fun getSecondOperand(): Double? {
        val operatorIndex = display.text.indexOf(currentOperator!!)
        val secondOperandString = display.text.substring(operatorIndex + 1)
        return secondOperandString.toDoubleOrNull()
    }

    private fun onEqualsClick() {
        val secondOperand = getSecondOperand()
        if (secondOperand != null && currentValue != null && currentOperator != null) {
            val result = when (currentOperator) {
                "+" -> currentValue!! + secondOperand
                "-" -> currentValue!! - secondOperand
                "*" -> currentValue!! * secondOperand
                "/" -> currentValue!! / secondOperand
                else -> null
            }

            if (result != null) {
                display.text = result.toString()
                saveOperationToHistory(currentValue!!, currentOperator!!, secondOperand, result)
                currentValue = null
                currentOperator = null
            }
        }
    }

    private fun onClearClick() {
        display.text = ""
        currentValue = null
        currentOperator = null
    }

    private fun saveOperationToHistory(value1: Double, operator: String, value2: Double, result: Double) {
        val operation = "$value1 $operator $value2 = $result"
        if (operationHistory.size >= 30) {
            operationHistory.removeAt(0)
        }
        operationHistory.add(operation)
    }
}
