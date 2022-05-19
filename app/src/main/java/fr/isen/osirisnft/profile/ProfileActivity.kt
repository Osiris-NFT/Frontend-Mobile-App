package fr.isen.osirisnft.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.osirisnft.*
import fr.isen.osirisnft.data.NFTData
import fr.isen.osirisnft.data.ParseData
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.ActivityProfileBinding
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.network.Constants
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var currentUser: String
    lateinit var wallet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = intent.getStringExtra(HomeActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(HomeActivity.WALLET).toString()

        navigationBar()
        toggleButtonClick()
        getPubImageRequest()
    }

    /***** Publications' display *****/
    private fun getPubImageRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.pubByUserURL(currentUser)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            parameters,
            {
                Log.d("debug", it.toString(2))

                val listImage = ParseData().parsePublications(it, "publications")
                setPubImageList(listImage)
            },
            {
                Log.d("debug", "$it")
            }
        )
        queue.add(request)
    }

    private fun setPubImageList(images: ArrayList<PublicationData>) {
        binding.profileUserName.text = currentUser

        binding.listOfPubImage.layoutManager = GridLayoutManager(this, 2)
        binding.listOfPubImage.adapter = ImageAdapter(images) {
            showPubDetails(it)
        }
    }

    private fun showPubDetails(img: PublicationData) {
        val intent = Intent(this, UserPubActivity::class.java)
        intent.putExtra(SELECTED_IMAGE, img)
        intent.putExtra(CURRENT_USER, currentUser)
        intent.putExtra(WALLET, wallet)
        startActivity(intent)
    }

    /***** NFTs' display *****/
    private fun getNftImageRequest() { /** A MODIFIER AVEC LA REQUETE CORRESPONDANTE + DATA CLASS **/
        val queue = Volley.newRequestQueue(this)
        val url = Constants.nftByWalletURL(wallet)
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            parameters,
            {
                Log.d("debug", it.toString(2))

                val listNft = ParseData().parseNfts(it)
                setNftImageList(listNft)
            },
            {
                Log.d("debug", "$it")
            }
        )
        queue.add(request)
    }

    private fun setNftImageList(nfts: ArrayList<NFTData>) { /** DIFFERENTE DATA CLASS **/
        binding.listOfPubImage.layoutManager = GridLayoutManager(this, 2)
        binding.listOfPubImage.adapter = NftAdapter(nfts) {
            showNftDetails(it)
        }
    }

    private fun showNftDetails(nft: NFTData) {
        val intent = Intent(this, UserNftActivity::class.java)
        intent.putExtra(SELECTED_NFT, nft)
        intent.putExtra(CURRENT_USER, currentUser)
        intent.putExtra(WALLET, wallet)
        startActivity(intent)
    }

    private fun toggleButtonClick() {
        binding.profilePubButton.setOnClickListener {
            binding.profilePubButton.isChecked = true
            binding.profileNFTButton.isChecked = false

            getPubImageRequest()
        }
        binding.profileNFTButton.setOnClickListener {
            binding.profileNFTButton.isChecked = true
            binding.profilePubButton.isChecked = false

            getNftImageRequest()
        }
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
        const val SELECTED_IMAGE = "SELECTED_IMAGE"
        const val SELECTED_NFT = "SELECTED_NFT"
        const val CURRENT_USER = "CURRENT_USER"
        const val WALLET = "WALLET"
    }
}