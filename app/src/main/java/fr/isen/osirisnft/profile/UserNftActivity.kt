package fr.isen.osirisnft.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.favorite.FavoriteActivity
import fr.isen.osirisnft.PublicationActivity
import fr.isen.osirisnft.R
import fr.isen.osirisnft.data.NFTData
import fr.isen.osirisnft.databinding.ActivityUserNftBinding
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.network.Constants
import org.json.JSONObject

class UserNftActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserNftBinding
    private lateinit var currentNft: NFTData
    private lateinit var currentUser: String
    lateinit var wallet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentNft = intent.getSerializableExtra(ProfileActivity.SELECTED_NFT) as NFTData
        currentUser = intent.getStringExtra(ProfileActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(ProfileActivity.WALLET).toString()

        pubListener()
        getNftTokenRequest(currentNft.metadata.transaction_hash)
        navigationBar()
    }

    private fun pubListener() {
        if (currentNft.is_published) {
            binding.userNftPostButton.isEnabled = false
            binding.userNftPostButton.isClickable = false
        } else {
            binding.userNftPostButton.setOnClickListener {
                val intent = Intent(this, NftToPubActivity::class.java)
                intent.putExtra(CURRENT_NFT, currentNft)
                intent.putExtra(CURRENT_USER, currentUser)
                intent.putExtra(WALLET, wallet)
                startActivity(intent)
            }
        }
    }

    private fun getNftTokenRequest(hash: String) {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.PublicationServiceURL + Constants.TokenByNft + "?hash=" + hash
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            parameters,
            {

                Log.d("debug", it.toString(2))
                setContent(it["token_id"].toString())
            },
            {
                Log.d("debug", "$it")
            }
        )
        queue.add(request)
    }

    @SuppressLint("SetTextI18n")
    private fun setContent(token: String) {
        Picasso
            .get()
            .load(Constants.imageIdURL(currentNft._id))
            .into(binding.userNftImage)

        binding.userNftContract.text = "Adresse du contrat : " + currentNft.metadata.contract_address
        binding.userNftToken.text = "Token : " + token
    }

    private fun navigationBar() {
        binding.navBar.selectedItemId = R.id.profileNav
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
        const val CURRENT_NFT = "CURRENT_NFT"
        const val CURRENT_USER = "CURRENT_USER"
        const val WALLET = "WALLET"
    }
}