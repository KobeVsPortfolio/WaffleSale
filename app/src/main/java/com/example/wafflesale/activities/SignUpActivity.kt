package com.example.wafflesale.activities

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.wafflesale.R
import com.example.wafflesale.data.MyDBAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var myDBAdapter: MyDBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        initializeDatabase()

        val typeface = Typeface.createFromAsset(assets, "baloo_regular.ttf")
        inputRepeatPassword.typeface = typeface
        inputRepeatPassword.transformationMethod = PasswordTransformationMethod()
        inputPassword.typeface = typeface
        inputPassword.transformationMethod = PasswordTransformationMethod()

        btnRegister.setOnClickListener {
            var firstName = inputFirstName.text.toString()
            var lastName = inputLastName.text.toString()
            var email = inputEmail.text.toString().toLowerCase()
            var password = inputPassword.text.toString()
            var repeatPassword = inputRepeatPassword.text.toString()
            var existingEmails : ArrayList<String?>? = myDBAdapter?.findAllEmails()
            if (!(firstName.isBlank() && lastName.isBlank() && email.isBlank() && password.isBlank())) {
                var displayName = "${firstName} ${lastName}"
                if(existingEmails?.contains(email)!!) {
                    if (password == repeatPassword) {
                        if (password.length > 5) {
                            createAccount(displayName, email, password)
                        } else {
                            Toast.makeText(this, "Your password needs at least 6 characters.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "This member does not exist.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(displayName: String, email: String, password: String){
    auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener(this)
    {
        task ->
        if (task.isSuccessful) {
            val user = auth.currentUser
            user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(displayName).build())
            Toast.makeText(this, "${displayName} is now a waffle.", Toast.LENGTH_SHORT).show()
            updateUI(user)
        } else {
            Toast.makeText(
                baseContext, "Signup failed, email is invalid or already in use.",
                Toast.LENGTH_SHORT
            ).show()
            updateUI(null)
        }
    }
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeDatabase(){
        myDBAdapter = MyDBAdapter(this@SignUpActivity)
        myDBAdapter?.open()
    }
}
