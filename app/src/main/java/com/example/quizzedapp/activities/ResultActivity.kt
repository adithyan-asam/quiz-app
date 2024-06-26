package com.example.quizzedapp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.example.quizzedapp.models.Quiz
import android.util.Log
import com.example.quizzedapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.quizzedapp.utils.ColorPicker
import com.example.quizzedapp.utils.IconPicker
import com.example.quizzedapp.adapters.QuizAdapter
import com.example.quizzedapp.adapters.ResultAdapter

class ResultActivity : AppCompatActivity() {

    lateinit var quiz: Quiz
    lateinit var resultAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpViews()
        ColorPicker.resetColorIndex()
        IconPicker.resetIconIndex()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        resultAdapter = ResultAdapter(quiz)
        setAnswerView()
        displayScore()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color='#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        val txtAnswer = findViewById<TextView>(R.id.txtAnswer)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun displayScore() {
        val txtScore = findViewById<TextView>(R.id.txtScore)
        txtScore.text = resultAdapter.calculateScore()
        resultAdapter.displayScore()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        )
    }
}
