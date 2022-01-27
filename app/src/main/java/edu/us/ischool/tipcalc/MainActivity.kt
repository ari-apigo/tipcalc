package edu.us.ischool.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import java.text.NumberFormat
import java.util.*
import java.lang.NumberFormatException


class MainActivity : AppCompatActivity() {

    lateinit var editTextAmount : EditText
    lateinit var btnTip : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        editTextAmount.setText("$")
        Selection.setSelection(editTextAmount.text, editTextAmount.text.length)

        val btnTip = findViewById<Button>(R.id.btnTip)

        var current = ""

        editTextAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val stringText = s.toString()

                if(stringText != current) {
                    editTextAmount.removeTextChangedListener(this)

                    val locale: Locale = Locale.US
                    val currency = Currency.getInstance(locale)
                    val cleanString = stringText.replace("[${currency.symbol},.]".toRegex(), "")
                    val parsed = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance(locale).format(parsed / 100)

                    current = formatted
                    editTextAmount.setText(formatted)
                    editTextAmount.setSelection(formatted.length)
                    editTextAmount.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(s: Editable) {
                // format editText as currency
//                NumberFormat.getCurrencyInstance()
//                var currentString = s.toString()
//                var currentDouble = NumberFormat.getCurrencyInstance().parse(currentString).toDouble()
//                var formatted = NumberFormat.getCurrencyInstance().format(currentDouble)
//                editTextAmount.setText(formatted)

                if (!btnTip.isEnabled) {
                    // enable tip button
                    btnTip.isEnabled = true
                }
            }
        })


        btnTip.setOnClickListener{
            // convert input text to number
            val stringAmt = editTextAmount.text.toString()
            NumberFormat.getCurrencyInstance()
            val doubleAmt = NumberFormat.getCurrencyInstance().parse(stringAmt).toDouble()

            val tip = calculateTip(doubleAmt)
            Toast.makeText(this, tip, Toast.LENGTH_LONG).show()
        }
    }

    // calculates tip based on given amount. returns tip formatted as currency.
    fun calculateTip(amt: Double): String {
        // get tip percentage
        val tipOptions = findViewById<RadioGroup>(R.id.tipOptions)
        val selectedTipId = tipOptions.checkedRadioButtonId
        val tipPercent = when (selectedTipId) {
            R.id.radioButton10 -> .10
            R.id.radioButton15 -> .15
            R.id.radioButton18 -> .18
            else -> .20
        }

        // calculate tip
        val tip = amt * tipPercent

        // format as currency
        NumberFormat.getCurrencyInstance()
        return NumberFormat.getCurrencyInstance().format(tip)
    }
}