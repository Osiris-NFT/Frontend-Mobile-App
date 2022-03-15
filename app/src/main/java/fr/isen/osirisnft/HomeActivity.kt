package fr.isen.osirisnft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.osirisnft.data.Constants
import fr.isen.osirisnft.databinding.ActivityHomeBinding
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeRequest()
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = Constants.URL
        val parameters = JSONObject()
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                Log.d("debug", it.toString(2))
            },
            {
                Log.d("debug", "$it")
            }
        )
        queue.add(request)
    }
}