package com.example.fredrick_abrera_ricardo_rey_act3

import android.annotation.SuppressLint
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.media.SoundPool
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.notkamui.keval.Keval
import kotlin.collections.iterator

class CalculatorActivity : AppCompatActivity() {

    // variable initialization
    private lateinit var tvSolution: TextView
    private lateinit var tvResult: TextView
    private lateinit var soundPool: SoundPool
    private var equalsSymbol: String = ""
    private var syntaxErrorMessage: String = ""
    private var expression = ""
    private var solution = ""
    private var buttonClickSound: Int = 0
    private var equalsSound: Int = 0
    private var errorSound: Int = 0
    private var clearSound: Int = 0
    private var backspaceSound: Int = 0
    private var clearEntrySound: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        tvSolution = findViewById(R.id.solution_display)
        tvResult = findViewById(R.id.display)

        equalsSymbol = getString(R.string.equals_symbol)
        syntaxErrorMessage = getString(R.string.syntax_error_message)

//        private lateinit var soundPool: SoundPool
//        soundPool = SoundPool.Builder()
//            .setMaxStreams(5)
//            .build()
//        exampleSound = soundPool.load(this, R.raw.<filename>, 1)
//        soundPool.play(exampleSound, 1f, 1f, 0, 0, 1f)
//        parameters are (<soundID>, <left volume>, <right volume>, <priority>, <loop>, <rate>)

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()
        buttonClickSound = soundPool.load(this, R.raw.pop1, 1)
        equalsSound = soundPool.load(this, R.raw.pop2, 1)
        errorSound = soundPool.load(this, R.raw.error, 1)
        clearSound = soundPool.load(this, R.raw.clear, 1)
        backspaceSound = soundPool.load(this, R.raw.backspace, 1)
        clearEntrySound = soundPool.load(this, R.raw.clearentry, 1)

        // -- start: dynamic application of transition animations to all buttons
        // creating a map object to assign each button from the layout xml file to a string for easier access
        val buttonImageNames = mapOf(
            R.id.btn_c to "btn_c",
            R.id.btn_backspace to "btn_backspace",
            R.id.btn_ce to "btn_ce",
            R.id.btn_right_parentheses to "btn_right_parentheses",
            R.id.btn_left_parentheses to "btn_left_parentheses",
            R.id.btn_pos_neg_toggle to "btn_pos_neg_toggle",
            R.id.btn_dot to "btn_dot",
            R.id.btn_obelus to "btn_obelus",
            R.id.btn_minus to "btn_minus",
            R.id.btn_plus to "btn_plus",
            R.id.btn_x to "btn_x",
            R.id.btn_percent to "btn_percent",
            R.id.btn_equals to "btn_equals",
            R.id.btn_zero to "btn_zero",
            R.id.btn_one to "btn_one",
            R.id.btn_two to "btn_two",
            R.id.btn_three to "btn_three",
            R.id.btn_four to "btn_four",
            R.id.btn_five to "btn_five",
            R.id.btn_six to "btn_six",
            R.id.btn_seven to "btn_seven",
            R.id.btn_eight to "btn_eight",
            R.id.btn_nine to "btn_nine"
        )

        // dynamic application of the transition animations per button via for looping
        for ((id, imageName) in buttonImageNames) {
            val btn = findViewById<ImageButton>(id)
            setupImageButtonWithTransition(btn, imageName)
        }
        // -- end

        // -- start: functionality, setting onclicklisteners to each button
        // digits (0-9)
        findViewById<ImageButton>(R.id.btn_zero).setOnClickListener {
            appendToExpression("0")
        }
        findViewById<ImageButton>(R.id.btn_one).setOnClickListener {
            appendToExpression("1")
        }
        findViewById<ImageButton>(R.id.btn_two).setOnClickListener {
            appendToExpression("2")
        }
        findViewById<ImageButton>(R.id.btn_three).setOnClickListener {
            appendToExpression("3")
        }
        findViewById<ImageButton>(R.id.btn_four).setOnClickListener {
            appendToExpression("4")
        }
        findViewById<ImageButton>(R.id.btn_five).setOnClickListener {
            appendToExpression("5")
        }
        findViewById<ImageButton>(R.id.btn_six).setOnClickListener {
            appendToExpression("6")
        }
        findViewById<ImageButton>(R.id.btn_seven).setOnClickListener {
            appendToExpression("7")
        }
        findViewById<ImageButton>(R.id.btn_eight).setOnClickListener {
            appendToExpression("8")
        }
        findViewById<ImageButton>(R.id.btn_nine).setOnClickListener {
            appendToExpression("9")
        }

        // misc buttons & arithmetic operators
        findViewById<ImageButton>(R.id.btn_plus).setOnClickListener {
            appendToExpression(" + ")
        }
        findViewById<ImageButton>(R.id.btn_minus).setOnClickListener {
            appendToExpression(" - ")
        }
        findViewById<ImageButton>(R.id.btn_x).setOnClickListener {
            appendToExpression(" * ")
        }
        findViewById<ImageButton>(R.id.btn_obelus).setOnClickListener {
            appendToExpression(" / ")
        }
        findViewById<ImageButton>(R.id.btn_left_parentheses).setOnClickListener {
            appendToExpression("(")
        }
        findViewById<ImageButton>(R.id.btn_right_parentheses).setOnClickListener {
            appendToExpression(")")
        }
        findViewById<ImageButton>(R.id.btn_dot).setOnClickListener {
            appendToExpression(".")
        }
        findViewById<ImageButton>(R.id.btn_pos_neg_toggle).setOnClickListener {
            val tokens = expression.trim().split(" ").toMutableList()

            if (tokens.isEmpty()) {
                return@setOnClickListener
            }

            val lastToken = tokens.last()

            val number = lastToken.toDoubleOrNull()
            if (number != null) {
                val negated = if (number > 0) "-$lastToken" else lastToken.removePrefix("-")
                tokens[tokens.lastIndex] = negated
                expression = tokens.joinToString(" ")
                tvSolution.text = expression
            }
        }
        findViewById<ImageButton>(R.id.btn_percent).setOnClickListener {
            if (expression.isNotEmpty()) {
                try {
                    val result = Keval.eval(expression) / 100
                    tvResult.text = result.toString()
                } catch (e: Exception) {
                    tvResult.text = syntaxErrorMessage
                }
            }
        }
        findViewById<ImageButton>(R.id.btn_equals).setOnClickListener {
            val resultText = evaluateExpression(expression)
            solution = expression + equalsSymbol
            tvResult.text = resultText
            tvSolution.text = solution
        }

        // destructive buttons
        findViewById<ImageButton>(R.id.btn_c).setOnClickListener {
            expression = ""
            tvSolution.text = "0"
            tvResult.text = "0"
        }
        findViewById<ImageButton>(R.id.btn_backspace).setOnClickListener {
            val tokens = expression.trim().split(" ").toMutableList()
            if (tokens.isEmpty()) {
                return@setOnClickListener
            }

            val lastToken = tokens.last()

            // checks if the last element in the token list is greater than 1 (will happen per operator since they have whitespaces around them)
            if (lastToken.length > 1) {
                tokens[tokens.lastIndex] = lastToken.dropLast(1)
            } else {
                tokens.removeAt(tokens.lastIndex)
            }

            expression = tokens.joinToString(" ")

            if (expression.isEmpty()) {
                tvSolution.text = "0"
                tvResult.text = "0"
            } else {
                tvSolution.text = expression
                tvResult.text = ""
            }
        }
        findViewById<ImageButton>(R.id.btn_ce).setOnClickListener {
            if (expression.isEmpty()) return@setOnClickListener

            val tokens = expression.trim().split(" ").toMutableList()

            if (tokens.isNotEmpty()) {
                tokens.removeAt(tokens.lastIndex)
            }

            expression = tokens.joinToString(" ")
            tvSolution.text = expression
            tvResult.text = "0"
        }
        // -- end

        // -- start: applying a linear gradient to the display text for pogi points hehe
        val textShader: Shader = LinearGradient(
            0f, 0f, 0f, tvResult.textSize,
            intArrayOf("#D9D9D9".toColorInt(), "#000000".toColorInt()),
            floatArrayOf(0f, 1f), TileMode.CLAMP
        )
        tvResult.paint.setShader(textShader)
        // -- end
    }

    // function for appending to the expression string for evaluation via Keval
    private fun appendToExpression(value: String, clearResult: Boolean = true) {
        if (clearResult) {
            tvResult.text = ""
        }
        val trimmedValue = value.trim()

        // since arithmetic & misc operators are surrounded by whitespaces, this checks and deletes
        if (trimmedValue in listOf("+", "-", "*", "/", "%")) {
            val tokens = expression.trim().split(" ").toMutableList()
            if (tokens.isNotEmpty() && tokens.last() in listOf("+", "-", "*", "/", "%")) {
                tokens[tokens.lastIndex] = trimmedValue
                expression = tokens.joinToString(" ") + " "
            } else {
                expression += " $trimmedValue "
            }
        } else {
            expression += trimmedValue
        }
        tvSolution.text = expression
    }

    // function that checks if the string to be evaluated starts with a decimal point, and converts it to have a zero before the decimal pt.
    private fun evaluateExpression(expression: String): String {
        // if char has a decimal pt at the start:
        var parsedExpression = expression.replace(Regex("""\.(\d+)"""), "0.$1")
        return try {
            Keval.eval(parsedExpression).toString()
        } catch (_: Exception) {
            "Syntax Error"
        }
    }

    // suppressing the accessibility warning since i only use this method for the animations & sounds
    // (it has nothing to do with the functionality of the buttons)
    @SuppressLint("ClickableViewAccessibility")
    private fun setupImageButtonWithTransition(button: ImageButton, imageName: String) {
        val context = button.context
        val defaultId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        val pressedId = context.resources.getIdentifier("${imageName}_pressed", "drawable", context.packageName)
        // had to use getIdentifier instead of the usual R.foo.bar syntax to dynamically get each drawable image for all the image buttons
        // unfortunately resource identifiers (e.g. R.foo.bar) cannot access variables like getIdentifier() can
        val defaultDrawable = ContextCompat.getDrawable(context, defaultId)
        val pressedDrawable = ContextCompat.getDrawable(context, pressedId)

        button.setImageDrawable(defaultDrawable)

        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    button.setImageDrawable(pressedDrawable)
                    button.animate().scaleX(0.95f).scaleY(0.95f).setDuration(10).start()
                    if (imageName == "btn_equals") {
                        soundPool.play(equalsSound, 1f, 1f, 0, 0, 1f)
                        if (tvResult.text == syntaxErrorMessage) {
                            soundPool.play(errorSound, 1f, 1f, 0, 0, 1f)
                        }
                    } else if (imageName == "btn_c") {
                        soundPool.play(clearSound, 1f, 1f, 0, 0, 1f)
                    } else if (imageName == "btn_backspace") {
                        soundPool.play(backspaceSound, 1f, 1f, 0, 0, 1f)
                    } else if (imageName == "btn_ce"){
                        soundPool.play(clearEntrySound, 1f, 1f, 0, 0, 1f)
                    } else {
                        soundPool.play(buttonClickSound, 1f, 1f, 0, 0, 1f)
                    }
                }

                MotionEvent.ACTION_UP -> {
                    button.setImageDrawable(defaultDrawable)
                    button.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                    v.performClick()
                }

                MotionEvent.ACTION_CANCEL -> {
                    button.setImageDrawable(defaultDrawable)
                    button.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start()
                }
            }
            true
        }
    }
}