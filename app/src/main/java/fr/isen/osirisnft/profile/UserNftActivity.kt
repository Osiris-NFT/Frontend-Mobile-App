package fr.isen.osirisnft.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.osirisnft.FavoriteActivity
import fr.isen.osirisnft.PublicationActivity
import fr.isen.osirisnft.R
import fr.isen.osirisnft.data.PublicationData
import fr.isen.osirisnft.databinding.ActivityUserNftBinding
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.network.Constants

class UserNftActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserNftBinding
    private lateinit var currentPub: PublicationData
    private lateinit var currentUser: String
    lateinit var wallet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPub = intent.getSerializableExtra(ProfileActivity.SELECTED_IMAGE) as PublicationData
        currentUser = intent.getStringExtra(HomeActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(HomeActivity.WALLET).toString()

        setContent()
        navigationBar()
    }

    private fun setContent() {
        Picasso
            .get()
            .load(Constants.PublicationServiceURL + currentPub.media_url)
            .into(binding.userNftImage)
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
}