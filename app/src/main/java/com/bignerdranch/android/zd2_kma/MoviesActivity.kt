package com.bignerdranch.android.zd2_kma

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class MoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val title = findViewById<TextView>(R.id.title)
        title.setOnClickListener {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }

        val img1 = findViewById<ImageView>(R.id.i1)
        val img2 = findViewById<ImageView>(R.id.i2)
        val img3 = findViewById<ImageView>(R.id.i3)

        Picasso.get()
            .load(R.drawable.img1)
            .resize(1080, 0)
            .onlyScaleDown()
            .into(img1)

        Picasso.get()
            .load(R.drawable.img2)
            .resize(1080, 0)
            .onlyScaleDown()
            .into(img2)

        Picasso.get()
            .load(R.drawable.img3)
            .resize(1080, 0)
            .onlyScaleDown()
            .into(img3)
    }
}