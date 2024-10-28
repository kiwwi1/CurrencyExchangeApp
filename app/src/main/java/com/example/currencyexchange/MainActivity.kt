package com.example.currencyexchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isSourceEditText1 = true
    private val exchangeRates = mapOf(
        "Vietnam - Dong" to 1.0,
        "United States - Dollar" to 0.000043,
        "Japan - Yen" to 0.0058,
        "European Union - Euro" to 0.000039,
        "United Kingdom - Pound Sterling" to 0.000034
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner1: Spinner = findViewById(R.id.spinner1)
        val spinner2: Spinner = findViewById(R.id.spinner2)
        val editText1: EditText = findViewById(R.id.editTextNumber1)
        val editText2: EditText = findViewById(R.id.editTextNumber2)

        val items = listOf("Vietnam - Dong", "United States - Dollar", "Japan - Yen", "European Union - Euro", "United Kingdom - Pound Sterling")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter
        spinner2.adapter = adapter

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (isSourceEditText1) {
                    convertCurrency(editText1, editText2, spinner1, spinner2)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (!isSourceEditText1) {
                    convertCurrency(editText2, editText1, spinner2, spinner1)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        editText1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isSourceEditText1) {
                    convertCurrency(editText1, editText2, spinner1, spinner2)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isSourceEditText1) {
                    convertCurrency(editText2, editText1, spinner2, spinner1)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editText1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isSourceEditText1 = true
            }
        }

        editText2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isSourceEditText1 = false
            }
        }
    }

    private fun convertCurrency(sourceEditText: EditText, targetEditText: EditText, sourceSpinner: Spinner, targetSpinner: Spinner) {
        val sourceCurrency = sourceSpinner.selectedItem.toString()
        val targetCurrency = targetSpinner.selectedItem.toString()

        val sourceAmount = sourceEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val targetRate = exchangeRates[targetCurrency] ?: 1.0

        val targetAmount = sourceAmount * (targetRate / sourceRate)
        targetEditText.setText("%.2f".format(targetAmount))
    }
}
