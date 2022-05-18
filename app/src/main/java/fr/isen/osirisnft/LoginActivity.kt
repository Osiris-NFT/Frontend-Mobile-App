
package fr.isen.osirisnft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.osirisnft.databinding.ActivityLoginBinding
import fr.isen.osirisnft.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var username: String
    lateinit var wallet: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLogin()
    }

    private fun getLogin() {
        binding.loginButton.setOnClickListener {
            username = binding.loginUsername.text.toString()
            wallet = binding.loginWallet.text.toString()
            password = binding.loginPassword.text.toString()

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(CURRENT_USER, username)
            intent.putExtra(WALLET, wallet)
            startActivity(intent)
        }
    }

    companion object {
        const val CURRENT_USER = "CURRENT_USER"
        const val WALLET = "WALLET"
    }
}