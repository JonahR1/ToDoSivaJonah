package com.sivajonah.todo.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sivajonah.todo.R
import com.sivajonah.todo.network.Api
import com.sivajonah.todo.MainActivity

class AuthenticationFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.authentication, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!Api.INSTANCE.getToken().isNullOrEmpty()) {
            startActivity(Intent(activity, MainActivity::class.java))
        }
        view.findViewById<Button>(R.id.login_auth).setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.signup_auth).setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }
}