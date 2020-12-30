package com.sivajonah.todo.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.login, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.login).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()

            if(email.isBlank() || password.isBlank()) {
                Toast.makeText(context, "Il manque des informations", Toast.LENGTH_LONG).show()
            } else {
                val loginForm = LoginForm(email, password)
                lifecycleScope.launch {
                    val loginResponse =  Api.INSTANCE.userWebService.login(loginForm)

                    if(loginResponse.isSuccessful) {
                        val token = loginResponse.body()!!.token
                        Toast.makeText(context, token, Toast.LENGTH_LONG).show()
                        PreferenceManager.getDefaultSharedPreferences(context).edit {
                            putString(SHARED_PREF_TOKEN_KEY, token)
                        }
                        startActivity(Intent(activity, MainActivity::class.java))
                    } else {
                        val message = loginResponse.errorBody()?.string() ?: ""
                        Log.e("", loginResponse.errorBody().toString())
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}