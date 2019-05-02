package com.example.wafflesale.activities

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.text.method.PasswordTransformationMethod
import com.example.wafflesale.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val typeface = Typeface.createFromAsset(assets, "baloo_regular.ttf")
        et_password.typeface = typeface
        et_password.transformationMethod = PasswordTransformationMethod()

        login_button.setOnClickListener {
            var email = et_email.text.toString()
            var password = et_password.text.toString()

            if(!email.isBlank() && !password.isBlank()) {
                signIn(email, password)
            }else{
                Toast.makeText(this, "Please provide an email and password.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    fun signup(v : View){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
