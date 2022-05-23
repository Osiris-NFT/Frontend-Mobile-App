package fr.isen.osirisnft

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.isen.osirisnft.data.UploadUtility
import fr.isen.osirisnft.databinding.ActivityPublicationBinding
import fr.isen.osirisnft.favorite.FavoriteActivity
import fr.isen.osirisnft.home.HomeActivity
import fr.isen.osirisnft.profile.ProfileActivity

@Suppress("DEPRECATION")
class PublicationActivity : AppCompatActivity() {
    lateinit var binding: ActivityPublicationBinding
    lateinit var uri: Uri
    private lateinit var currentUser: String
    lateinit var wallet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = intent.getStringExtra(HomeActivity.CURRENT_USER).toString()
        wallet = intent.getStringExtra(HomeActivity.WALLET).toString()

        navigationBar()
        listenAddClick()
    }

    private fun listenAddClick() {
        binding.addPubButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/jpeg"
            startActivityForResult(intent, REQUEST_CODE)
        }

        binding.postPubButton.setOnClickListener {
            uploadImageNftRequest(wallet)

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(CURRENT_USER, currentUser)
            intent.putExtra(WALLET, wallet)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

            val uri = data?.data
            Log.d("debug", uri.toString())
            if (uri != null) {
                binding.addPubImage.setImageURI(uri)
                this.uri = uri
            }
        }
    }

    private fun uploadImageNftRequest(wallet: String) {
        Log.d("testlog", "On est dans la request :)")
        UploadUtility(this, wallet).uploadFile(this.uri)
    }

    private fun navigationBar() {
        binding.navBar.selectedItemId = R.id.pubNav
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
        const val REQUEST_CODE = 1
        const val CURRENT_USER = "CURRENT_USER"
        const val WALLET = "WALLET"
    }
}