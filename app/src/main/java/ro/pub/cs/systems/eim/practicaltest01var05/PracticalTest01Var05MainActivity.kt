package ro.pub.cs.systems.eim.practicaltest01var05

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var05MainActivity : AppCompatActivity() {

    private lateinit var buttonsTextView: TextView
    private lateinit var clicksTextView: TextView

    // B.2.a - numarul total de apasari ale butoanelor
    private var numberOfClicks = 0

    private val buttonClickListener = ButtonClickListener()

    // B.1 - concateneaza textul butonului apasat in campul text
    private inner class ButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            numberOfClicks++
            clicksTextView.text = "Clicks: $numberOfClicks"
            val buttonText = (view as Button).text
            if (buttonsTextView.text.isEmpty()) {
                buttonsTextView.text = buttonText
            } else {
                buttonsTextView.append(", $buttonText")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonsTextView = findViewById(R.id.buttons_text_view)
        clicksTextView = findViewById(R.id.clicks_text_view)

        val buttonIds = intArrayOf(
            R.id.top_left_button, R.id.top_right_button, R.id.center_button,
            R.id.bottom_left_button, R.id.bottom_right_button
        )
        for (id in buttonIds) {
            findViewById<View>(id).setOnClickListener(buttonClickListener)
        }

        findViewById<Button>(R.id.navigate_to_secondary_activity_button).setOnClickListener {
            val intent = Intent(this, PracticalTest01Var05SecondaryActivity::class.java)
            intent.putExtra("template", buttonsTextView.text.toString())
            startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            val result = if (resultCode == RESULT_OK) "VERIFY" else "CANCEL"
            Toast.makeText(this, "Result: $result", Toast.LENGTH_LONG).show()
            buttonsTextView.text = ""
            numberOfClicks = 0
            clicksTextView.text = "Clicks: $numberOfClicks"
        }
    }

    companion object {
        const val SECONDARY_ACTIVITY_REQUEST_CODE = 1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numberOfClicks", numberOfClicks)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("numberOfClicks")) {
            numberOfClicks = savedInstanceState.getInt("numberOfClicks")
            clicksTextView.text = "Clicks: $numberOfClicks"
            Toast.makeText(this, "Number of clicks: $numberOfClicks", Toast.LENGTH_LONG).show()
        }
    }
}
