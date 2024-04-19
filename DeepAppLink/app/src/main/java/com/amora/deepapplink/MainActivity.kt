package com.amora.deepapplink

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amora.deepapplink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.apply {
            btLink.setOnClickListener {
                val url = "https://www.animekompi.cam"
                openUrl(url)
            }
            btYoutube.setOnClickListener {
                val channelId = "UCbCmjCuTUZos6Inko4u57UQ" // Cocomelon's channel ID
                openYoutubeChannel(channelId)
            }
            btEmail.setOnClickListener {
                val email = "recipient@example.com"
                val subject = "Subject"
                val body = "Body"
                openEmailApp(email, subject, body)
            }
        }

        setContentView(binding.root)
    }

    private fun openYoutubeChannel(channelId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/$channelId"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.youtube")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "YouTube App Not Found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Browser Not Found", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openEmailApp(email: String, subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Email App Not Found", Toast.LENGTH_SHORT).show()
        }
    }


}