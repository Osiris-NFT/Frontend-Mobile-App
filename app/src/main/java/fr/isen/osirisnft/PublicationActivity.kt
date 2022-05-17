package fr.isen.osirisnft

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.osirisnft.data.Constants
import fr.isen.osirisnft.data.UploadUtility
import fr.isen.osirisnft.databinding.ActivityPublicationBinding
import okhttp3.FormBody
import okhttp3.RequestBody
import org.json.JSONObject

class PublicationActivity : AppCompatActivity() {
    lateinit var binding: ActivityPublicationBinding
    lateinit var uri: Uri
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = intent.getStringExtra(HomeActivity.CURRENT_USER).toString()

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
            postPubRequest()

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(CURRENT_USER, currentUser)
            startActivity(intent)
        }
    }

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

    private fun requestBody(title: String, description: String, category:String): JSONObject {
        val parameters = JSONObject()

        parameters.put("publication_name", title)
        parameters.put("user_name", "$currentUser")
        parameters.put("description", description)
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
        val pubTitle = binding.inputTitle.text.toString()
        val pubDescription = binding.inputDescription.text.toString()
        val pubCategory = translateCat(binding.inputCategory.selectedItem.toString())

        val queue = Volley.newRequestQueue(this)
        val url = Constants.PublicationServiceURL + Constants.PostPublication
        val parameters = requestBody(pubTitle, pubDescription, pubCategory)
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                Log.d("testlog", "Pulication ID = " + it["publication_id"].toString())
                Log.d("testlog", it.toString(2))
                val pubId = it["publication_id"].toString()

                uploadImgRequest(pubId)
            },
            {
                Log.d("testlog", "$it")
            }
        )
        queue.add(request)
    }

    private fun uploadImgRequest(pubId: String) {
        Log.d("testlog", "On est dans la request :)")
        UploadUtility(this, pubId).uploadFile(this.uri)
    }

    private fun navigationBar() {
        binding.navBar.selectedItemId = R.id.pubNav
        binding.navBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeNav -> {
                    startActivity(Intent(this, HomeActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                R.id.pubNav -> {
                    startActivity(Intent(this, PublicationActivity::class.java)
                        .putExtra(HomeActivity.CURRENT_USER, currentUser))
                    true
                }
                R.id.profileNav -> {
                    startActivity(Intent(this, ProfileActivity::class.java)
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
        const val REQUEST_CODE = 1
        const val CURRENT_USER = "CURRENT_USER"
    }
}