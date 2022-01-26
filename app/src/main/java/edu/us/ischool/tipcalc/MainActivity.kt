package edu.us.ischool.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var editTextAmount : EditText
    lateinit var btnTip : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        editTextAmount.addTextChangedListener {
            fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                editTextAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(s))
            }
        }

        val btnTip = findViewById<Button>(R.id.btnTip)
    }
}