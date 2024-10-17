package com.example.shebachat

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shebachat.ui.theme.ShebaChatTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : ComponentActivity() {

    private lateinit var name: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        name=findViewById(R.id.name)
        edtEmail=findViewById(R.id.edit_email)
        edtPassword=findViewById(R.id.edit_password)
        btnSignup=findViewById(R.id.btnSignUp)
        mAuth=FirebaseAuth.getInstance()


        btnSignup.setOnClickListener{
            val name=name.text.toString()
            val email= edtEmail.text.toString()
            val password=edtPassword.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name:String,email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code is successful then jumping to home
                    addUserToDataBase(name,email,mAuth.currentUser?.uid!!) //!! is null safe
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    //successful signup
                    Toast.makeText(this@SignUp,"Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDataBase(name:String, email:String, uid:String){
        //getting reference of database
        mdbRef = FirebaseDatabase.getInstance().getReference()

        //child will add a node to the database
        mdbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}