package fr.isen.osirisnft.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.data.NFTData
import fr.isen.osirisnft.databinding.ActivityNftToPubBinding
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.network.Constants
import org.json.JSONObject

class NftToPubActivity : AppCompatActivity() {
    lateinit var binding: ActivityNftToPubBinding
    lateinit var currentNft: NFTData
    lateinit var currentUser: String
    lateinit var wallet: String
    private lateinit var nftURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNftToPubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentNft = intent.getSerializableExtra(UserNftActivity.CURRENT_NFT) as NFTData
        currentUser = intent.getStringExtra(UserNftActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(UserNftActivity.WALLET).toString()
        nftURL = Constants.imageIdURL(currentNft._id)

        setImage()
        pubClickListener()
    }

    private fun pubClickListener() {
        binding.nftToPubButton.setOnClickListener {
            postPubRequest()

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(CURRENT_NFT, currentNft)
            intent.putExtra(CURRENT_USER, currentUser)
            intent.putExtra(WALLET, wallet)
            startActivity(intent)
        }
    }

    private fun setImage() {
        Picasso
            .get()
            .load(nftURL)
            .into(binding.nftToPubImage)
    }

    private fun requestBody(title: String, description: String, category:String): JSONObject {
        val parameters = JSONObject()

        parameters.put("publication_name", title)
        parameters.put("user_name", currentUser)
        parameters.put("description", description)
        parameters.put("media_url", "/api/images/" + currentNft._id)
        parameters.put("content_type", "image")
        parameters.put("category", category)

        return parameters
    }

    private fun translateCat(cat: String): String {
        return when (cat) {
            "Photographie" -> "photography"
            "Pixel Art" -> "pixel-art"
            "Dessin" -> "digital-drawing"
            else -> "error"
        }
    }

    private fun postPubRequest() {
        val pubTitle = binding.nftToPubTitle.text.toString()
        val pubDescription = binding.nftToPubDescription.text.toString()
        val pubCategory = translateCat(binding.nftToPubCategory.selectedItem.toString())

        val queue = Volley.newRequestQueue(this)
        val url = Constants.PublicationServiceURL + Constants.PostPublication
        val parameters = requestBody(pubTitle, pubDescription, pubCategory)
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                Log.d("testlog", "Publication ID = " + it["publication_id"].toString())
                Log.d("testlog", it.toString(2))
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    companion object {
        const val CURRENT_NFT = "CURRENT_NFT"
        const val CURRENT_USER = "CURRENT_USER"
        const val WALLET = "WALLET"
    }
}