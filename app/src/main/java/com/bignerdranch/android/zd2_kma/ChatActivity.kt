package com.bignerdranch.android.zd2_kma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import com.android.volley.Request
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog

class ChatActivity : AppCompatActivity() {
    var year: String = ""
    var release: String = ""
    var run: String = ""
    var gen: String = ""
    var rating: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val list = findViewById<ListView>(R.id.list)
        val title = findViewById<TextView>(R.id.title)
        val array: ArrayAdapter<*>
        val ser = resources.getStringArray(R.array.series)
        array = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            ser)
        list.adapter = array
        title.setOnClickListener {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }
        list.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            var s = selectedItem.split(" ")

            if(s[0] == "The"){
                getResult(s[1])
                AlertDialog.Builder(this)
                    .setTitle(s[1])
                    .setMessage("""
                        Год выхода: $year
                        Выход: $release
                        Длительность серии: $run
                        Жанр: $gen
                        Рейтинг: $rating
                    """.trimIndent())
                    .setPositiveButton("OK", null)
                    .show()
            }
            else {
                getResult(s[0])
                AlertDialog.Builder(this)
                    .setTitle(s[0])
                    .setMessage("""
                        Год выхода: $year
                        Выход: $release
                        Длительность серии: $run
                        Жанр: $gen
                        Рейтинг: $rating
                    """.trimIndent())
                    .setPositiveButton("OK", null)
                    .show()
            }
            /*Snackbar.make(view, s[0], Snackbar.LENGTH_LONG).show()*/
        }

    }

    fun getResult(movie: String) {
        var url="https://www.omdbapi.com/?apikey=6d1bf815&t="+movie;
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    response->
                val obj = JSONObject(response)
                year = obj.getString("Year")
                release = obj.getString("Released")
                run = obj.getString("Runtime")
                gen = obj.getString("Genre")

                val ratingsArray = obj.getJSONArray("Ratings")
                rating = if (ratingsArray.length() > 0)
                    ratingsArray.getJSONObject(0).getString("Value")
                else
                    "Нет данных"
            },
            {
                Log.d("MyLog","Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }
}