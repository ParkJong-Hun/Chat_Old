package com.example.chattutorial

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


//This is Login Activity
class Login : AppCompatActivity() {
    //FirebaseAuth 인스턴스를 선언
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //FirebaseAuth를 초기화
        firebaseAuth = FirebaseAuth.getInstance()

        //OAuthProvider 인스턴스 생성
        val provider = OAuthProvider.newBuilder("github.com")
        //OAuth 요청과 함께 전송하고자 하는 커스텀 OAuth 매개변수 추가 지정
        //provider.addCustomParameter("login", "your-email@gmail.com");
        //인증 제공업체가 요청하고자 하는 기본 프로필 범위를 넘는 OAuth 2.0 범위 추가 지
        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes


        val LoginButton = findViewById<Button>(R.id.LoginButton)
        LoginButton.setOnClickListener{
            firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
                    .addOnSuccessListener {
                        Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "실패" + it, Toast.LENGTH_SHORT).show()
                    }
        }
    }
}