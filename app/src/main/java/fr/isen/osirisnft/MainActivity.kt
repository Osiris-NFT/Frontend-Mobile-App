package fr.isen.osirisnft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.osirisnft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.pubButton.setOnClickListener {
            startActivity(Intent(this, PublicationActivity::class.java))
        }
    }
}