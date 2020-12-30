package com.sivajonah.todo.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.sivajonah.todo.MainActivity
import com.sivajonah.todo.R
import com.sivajonah.todo.network.Api
import com.sivajonah.todo.network.SHARED_PREF_TOKEN_KEY
import kotlinx.coroutines.launch

class SignupFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.signup, container, false)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.signup).setOnClickListener {
            val firstname = view.findViewById<EditText>(R.id.firstname).text.toString()
            val lastname = view.findViewById<EditText>(R.id.lastname).text.toString()
            val email = view.findViewById<EditText>(R.id.email_signup).text.toString()
            val password = view.findViewById<EditText>(R.id.password_signup).text.toString()
            val confirm_password = view.findViewById<EditText>(R.id.password_signup_confirmation).text.toString()

            if(firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank() || confirm_password.isBlank()) {
                Toast.makeText(context, "Il manque des informations", Toast.LENGTH_LONG).show()
            } else if(password != confirm_password) {
                Toast.makeText(context, "Password & password confirm non identiques", Toast.LENGTH_LONG).show()
            } else {
                val loginForm = SignUpForm(firstname,lastname,email,password,confirm_password)
                lifecycleScope.launch {
                    val signupResponse =  Api.INSTANCE.userWebService.signUp(loginForm)
                    if(signupResponse.isSuccessful) {
                        val token = signupResponse.body()!!.token
                        PreferenceManager.getDefaultSharedPreferences(context).edit {
                            putString(SHARED_PREF_TOKEN_KEY, token)
                        }
                        startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        Toast.makeText(context, "ERREUR Inscription", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}