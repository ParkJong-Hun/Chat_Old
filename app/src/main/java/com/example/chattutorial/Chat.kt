package com.example.chattutorial

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


//This is Chat Activity
class Chat : AppCompatActivity() {
    private var adapter: MessageAdapter? = null
    val currentUserName = FirebaseAuth.getInstance().currentUser?.displayName.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.chattutorial.R.layout.chat)
        Toast.makeText(this, currentUserName, Toast.LENGTH_SHORT).show()
        init()
    }

    //리사이클러뷰 어댑터 연결
    private fun init() {
        var recyclerView:RecyclerView = findViewById(com.example.chattutorial.R.id.recyclerView)
        var linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)

        adapter = MessageAdapter()
        recyclerView.adapter = adapter
        
        val button: Button = findViewById(com.example.chattutorial.R.id.submitButton)
        button.setOnClickListener{
            SubmitText()
        }
    }

    //내용 전송
    private fun SubmitText() {
        var editText:EditText = findViewById(com.example.chattutorial.R.id.inputText)
        var data:Data = Data()
        data.text = editText.text.toString()
        data.userName = currentUserName
        data.messageDate = Date()
        adapter!!.addItem(data)
    }
}