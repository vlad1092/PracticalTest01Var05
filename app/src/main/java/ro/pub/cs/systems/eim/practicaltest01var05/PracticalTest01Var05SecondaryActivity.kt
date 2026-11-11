package ro.pub.cs.systems.eim.practicaltest01var05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var05SecondaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var05_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val templateTextView = findViewById<TextView>(R.id.template_text_view)
        templateTextView.text = intent.getStringExtra("template")

        findViewById<Button>(R.id.verify_button).setOnClickListener {
            setResult(RESULT_OK, Intent())
            finish()
        }

        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            setResult(RESULT_CANCELED, Intent())
            finish()
        }
    }
}
