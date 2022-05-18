package fr.isen.osirisnft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.osirisnft.data.ParseData
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.ActivityFavoriteBinding
import fr.isen.osirisnft.home.DetailsActivity
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.network.Constants
import fr.isen.osirisnft.profile.ProfileActivity
import fr.isen.osirisnft.profile.UserPubActivity
import okhttp3.internal.wait
import org.json.JSONObject

@Suppress("UNCHECKED_CAST")
class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = intent.getStringExtra(HomeActivity.CURRENT_USER).toString()

        navigationBar()
        getFavImageRequest()
    }

    private fun getFavImageRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.allLikesURL(currentUser)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            parameters,
            {
                Log.d("testlog", it.toString(2))

                val listImage = ParseData().parsePublications(it, "liked_pub")
                setFavImageList(listImage)
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun setFavImageList(images: ArrayList<PublicationData>) {
        binding.listOfFav.layoutManager = GridLayoutManager(this, 2)
        binding.listOfFav.adapter = ImageAdapter(images) {
            showDetails(it)
        }
    }

    private fun showDetails(img: PublicationData) {
        val intent = Intent(this, FavDetailsActivity::class.java)
        intent.putExtra(SELECTED_IMAGE, img)
        intent.putExtra(CURRENT_USER, currentUser)
        startActivity(intent)
    }

    private fun navigationBar() {
        binding.navBar.selectedItemId = R.id.favNav
        binding.navBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeNav -> {
                    startActivity(
                        Intent(this, HomeActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                R.id.pubNav -> {
                    startActivity(
                        Intent(this, PublicationActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                R.id.favNav -> {
                    startActivity(
                        Intent(this, FavoriteActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                R.id.profileNav -> {
                    startActivity(
                        Intent(this, ProfileActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    companion object {
        const val SELECTED_IMAGE = "SELECTED_IMAGE"
        const val CURRENT_USER = "CURRENT_USER"
    }
}