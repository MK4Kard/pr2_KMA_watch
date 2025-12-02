package com.bignerdranch.android.zd2_kma

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson

class SignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val mail = findViewById<EditText>(R.id.email)
        val pass = findViewById<EditText>(R.id.pass)
        val btn = findViewById<AppCompatButton>(R.id.btn)

        mail.text = SpannableStringBuilder(loadText(this, false))
        pass.text = SpannableStringBuilder(loadText(this, true))

        saveText(this, false, mail.text.toString())
        saveText(this, true, pass.text.toString())

        btn.setOnClickListener{
            if (mail.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()){
                val intent = Intent(this, MainScreenActivity::class.java)
                startActivity(intent)
            }
            else {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("У вас есть незаполненные поля")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show()
            }
        }
    }

    fun saveText(context: Context, pass: Boolean, text: String) {
        val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val json = Gson().toJson(text)
        if (pass) {
            editor.putString("pass", json)
        }
        else editor.putString("text", json)
        editor.apply()
    }

    fun loadText(context: Context, pass: Boolean): String {
        val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var t = "text"
        if (pass) {
            t = "pass"
        }
        else t = "text"
        val json = sharedPref.getString(t, null)
        return if (json != null) {
            Gson().fromJson(json, String::class.java) ?: ""
        } else ""
    }
}