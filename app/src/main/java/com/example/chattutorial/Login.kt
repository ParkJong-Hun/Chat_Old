package com.example.chattutorial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

        //깃헙 계정으로 로그인 버튼
        val LoginButton = findViewById<Button>(R.id.LoginButton)
        LoginButton.setOnClickListener{
            firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
                    //로그인 버튼 클릭 성공적
                    .addOnSuccessListener {
                        moveActivity()
                    }
                    //로그인 버튼 클릭 실패
                    .addOnFailureListener{
                        Toast.makeText(this, "연결이 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
        }
        //해당 기기에서 1번이라도 로그인한 적 있을 때 자동 로그인 되게 하기
        firebaseAuth.currentUser?.startActivityForLinkWithProvider(this, provider.build())
                ?.addOnSuccessListener {
                    moveActivity()
                }
    }

    fun moveActivity() {
        //Chat으로 이동하는 intent 생성
        val intent: Intent = Intent(this, Chat::class.java)
        //현재 로그인한 사용자의 uid 반환
        val uid: String? = FirebaseAuth.getInstance().uid
        //intent에 uid를 전달
        intent.putExtra("uid", uid)
        //intent로 Chat Activity를 실행
        startActivity(intent)
    }
    //뒤로가기 눌러도 반응 X
    override fun onBackPressed() {
    }

}