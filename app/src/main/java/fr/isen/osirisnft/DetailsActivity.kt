package fr.isen.osirisnft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    private var currentPub: PublicationData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPub = intent.getSerializableExtra(HomeActivity.SELECTED_PUB) as? PublicationData

        setContent()
    }

    private fun setContent() {
        binding.detailsTitle.text = currentPub?.publication_name
        binding.detailsDate.text = currentPub?.publication_date
        binding.detailsAuthor.text = currentPub?.user_name
        binding.detailsDescription.text = currentPub?.description

        binding.detailsLike.text = currentPub?.likes_count.toString()

        Picasso
            .get()
            .load(currentPub?.media_url)
            .into(binding.detailsImage)
    }
}