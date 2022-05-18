package fr.isen.osirisnft.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.osirisnft.*
import fr.isen.osirisnft.data.*
import fr.isen.osirisnft.databinding.ActivityHomeBinding
import fr.isen.osirisnft.network.Constants
import fr.isen.osirisnft.profile.ProfileActivity
import org.json.JSONObject


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = intent.getStringExtra(LoginActivity.CURRENT_USER).toString()

        navigationBar()
        getPubRequest()
    }

    private fun setPubList(publications: ArrayList<PublicationData>) { // envoie les données à l'adapter pour qu'il complète les champs
        binding.listOfPublication.layoutManager = LinearLayoutManager(this)
        binding.listOfPublication.adapter = PublicationAdapter(publications, currentUser) {
            showDetails(it)
        }
    }

    private fun showDetails(pub: PublicationData) { // ouvre une publication en particulier
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(SELECTED_PUB, pub)
        intent.putExtra(CURRENT_USER, currentUser)
        startActivity(intent)
    }

    private fun getPubRequest() { // requête json
        val queue = Volley.newRequestQueue(this)
        val url = Constants.PublicationServiceURL + Constants.NewPublications + Constants.DateParam
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            parameters,
            {
                Log.d("debug", it.toString(2))

                val listPub = ParseData().parsePublications(it, "new")
                setPubList(listPub)
            },
            {
                Log.d("debug", "$it")
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
                        .putExtra(CURRENT_USER, currentUser))
                    true
                }
                R.id.pubNav -> {
                    startActivity(Intent(this, PublicationActivity::class.java)
                        .putExtra(CURRENT_USER, currentUser))
                    true
                }
                R.id.favNav -> {
                    startActivity(Intent(this, FavoriteActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                R.id.profileNav -> {
                    startActivity(Intent(this, ProfileActivity::class.java)
                        .putExtra(CURRENT_USER, currentUser))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    companion object {
        const val SELECTED_PUB = "SELECTED_PUB"
        const val CURRENT_USER = "CURRENT_USER"
    }
}