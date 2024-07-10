package com.example.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.ui.main.MainActivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.common.api.ApiException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton : ImageButton
    private lateinit var creaCuenta : TextView
    private lateinit var iniciarSesion: Button
    private lateinit var correoElectronico: EditText
    private lateinit var contraseña: EditText

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginButton = findViewById(R.id.imageButtonGoogle)
        creaCuenta = findViewById(R.id.textViewRegistrarse)
        iniciarSesion = findViewById(R.id.btnIniciarSesion)
        correoElectronico = findViewById(R.id.editTextCorreoElect)
        contraseña = findViewById(R.id.editTextContraseña)


        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)
        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener{
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent,100)
        }

        creaCuenta.setOnClickListener{
             val intent = Intent(this,CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        // Verificacion del user
        iniciarSesion.setOnClickListener {
            val email = correoElectronico.text.toString()
            val password = contraseña.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Inicio de sesión fallido. Verifique su correo y contraseña.", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        if (e.message?.contains("There is no user record") == true) {
                            Toast.makeText(this, "La cuenta no existe. Por favor, registrese.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception){
                Log.e("DEMO_API", "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {authResult ->
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                if (authResult.additionalUserInfo!!.isNewUser){
                    Toast.makeText(this, "Cuenta creada...", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Cuenta existente...", Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{e->
                Toast.makeText(this, "Login Fallido...", Toast.LENGTH_LONG).show()
                Log.e("DEMO_API", "firebaseAuthWithGoogleAccount: ${e.message}")

            }
    }
}