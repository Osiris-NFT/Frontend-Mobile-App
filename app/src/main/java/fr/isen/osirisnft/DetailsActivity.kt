package fr.isen.osirisnft

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.osirisnft.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}