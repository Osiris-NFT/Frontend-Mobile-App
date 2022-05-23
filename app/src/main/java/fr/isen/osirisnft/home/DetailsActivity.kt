package fr.isen.osirisnft.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.favorite.FavoriteActivity
import fr.isen.osirisnft.profile.ProfileActivity
import fr.isen.osirisnft.PublicationActivity
import fr.isen.osirisnft.R
import fr.isen.osirisnft.data.CommentData
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.ActivityDetailsBinding
import fr.isen.osirisnft.network.Constants
import org.json.JSONObject
import java.time.LocalDateTime
import kotlin.collections.ArrayList

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var currentPub: PublicationData
    lateinit var currentUser: String
    lateinit var wallet: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPub = intent.getSerializableExtra(HomeActivity.SELECTED_PUB) as PublicationData
        currentUser = intent.getStringExtra(HomeActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(HomeActivity.WALLET).toString()

        navigationBar()
        setContent()
        buyClickListener()
        displayComment()
    }

    private fun buyClickListener() {
        binding.buyButton.setOnClickListener {
            getNftHashRequest()

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(HomeActivity.CURRENT_USER, currentUser)
            intent.putExtra(HomeActivity.WALLET, wallet)
            startActivity(intent)
        }
    }

    private fun setContent() {
        binding.detailsTitle.text = currentPub.publication_name
        binding.detailsDate.text = currentPub.publication_date
        binding.detailsAuthor.text = currentPub.user_name
        binding.detailsDescription.text = currentPub.description

        Picasso
            .get()
            .load(Constants.PublicationServiceURL + currentPub.media_url)
            .into(binding.detailsImage)

        binding.listOfComment.layoutManager = LinearLayoutManager(this)
        binding.listOfComment.adapter = CommentAdapter(currentPub.comments)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayComment() {
        var newComment: String
        binding.sendCommentButton.setOnClickListener {
            newComment = binding.inputComment.text.toString()

            val newCom = CommentData(
                "",
                currentUser,
                LocalDateTime.now().toString(),
                newComment,
                0,
                ArrayList()
            )
            addCommentRequest(Constants.addCommentURL(currentPub._id), newComment)

            CommentAdapter(currentPub.comments).addComment(newCom)
            setContent()

            binding.inputComment.text = null
        }
    }

    private fun addCommentRequest(url: String, commentContent: String) { // requête json
        val queue = Volley.newRequestQueue(this)
        val parameters = JSONObject()
        parameters.put("user", currentUser)
        parameters.put("content", commentContent)
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun getNftHashRequest() {
        val imageId = currentPub.media_url.removePrefix("/api/images/")

        val queue = Volley.newRequestQueue(this)
        val url = Constants.nftByIdURL(imageId)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))

                Log.d("testlog", it.getJSONObject("metadata").get("transaction_hash").toString())

                buyNftRequest(it.getJSONObject("metadata").get("transaction_hash").toString(), imageId)
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun buyNftRequest(hash: String, imageId: String) {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.PublicationServiceURL + Constants.TransferNft
        val parameters = JSONObject()
        parameters.put("hash", hash)
        parameters.put("address", wallet)
        Log.d("testlog", parameters.toString())
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))

                updateNftRequest(imageId)
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun updateNftRequest(imageId: String) {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.updateNftURL(imageId, wallet)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.PATCH,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))
                deletePubRequest()
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun deletePubRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.deletePubByIdURL(currentPub._id)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun navigationBar() {
        binding.navBar.selectedItemId = R.id.homeNav
        binding.navBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeNav -> {
                    startActivity(Intent(this, HomeActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser)
                        .putExtra(HomeActivity.WALLET, wallet))
                    true
                }
                R.id.pubNav -> {
                    startActivity(Intent(this, PublicationActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser)
                        .putExtra(HomeActivity.WALLET, wallet))
                    true
                }
                R.id.favNav -> {
                    startActivity(Intent(this, FavoriteActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser)
                        .putExtra(HomeActivity.WALLET, wallet))
                    true
                }
                R.id.profileNav -> {
                    startActivity(Intent(this, ProfileActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser)
                        .putExtra(HomeActivity.WALLET, wallet))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        val context: Context = DetailsActivity()
    }
}
