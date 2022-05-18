package fr.isen.osirisnft

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
import fr.isen.osirisnft.data.CommentData
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.ActivityFavDetailsBinding
import fr.isen.osirisnft.home.CommentAdapter
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.network.Constants
import fr.isen.osirisnft.profile.ProfileActivity
import org.json.JSONObject
import java.time.LocalDateTime

class FavDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavDetailsBinding
    private lateinit var currentPub: PublicationData
    lateinit var currentUser: String
    lateinit var wallet: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPub = intent.getSerializableExtra(FavoriteActivity.SELECTED_IMAGE) as PublicationData
        currentUser = intent.getStringExtra(FavoriteActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(FavoriteActivity.WALLET).toString()

        navigationBar()
        setContent()
        displayComment()
    }

    private fun setContent() {
        binding.favDetTitle.text = currentPub.publication_name
        binding.favDetDate.text = currentPub.publication_date
        binding.favDetAuthor.text = currentPub.user_name
        binding.favDetDescription.text = currentPub.description

        Picasso
            .get()
            .load(Constants.PublicationServiceURL + currentPub.media_url)
            .into(binding.favDetImage)

        binding.favListOfComment.layoutManager = LinearLayoutManager(this)
        binding.favListOfComment.adapter = CommentAdapter(currentPub.comments)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayComment() {
        var newComment: String
        binding.favSendCommentButton.setOnClickListener {
            newComment = binding.favInputComment.text.toString()

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

            binding.favInputComment.text = null
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

    private fun navigationBar() {
        binding.navBar.selectedItemId = R.id.favNav
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
}