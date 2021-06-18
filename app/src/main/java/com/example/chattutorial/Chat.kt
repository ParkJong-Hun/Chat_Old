package com.example.chattutorial

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


//This is Chat Activity
class Chat : AppCompatActivity() {

    private var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.chattutorial.R.layout.chat)
        init()
    }

    //리사이클러뷰 어댑터 연결
    private fun init() {
        var recyclerView:RecyclerView = findViewById(com.example.chattutorial.R.id.recyclerView)
        var linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)

        adapter = MessageAdapter()
        recyclerView.adapter = adapter

        var data1:Data = Data()
        data1.text = "안녕"
        data1.userName = "John doe"
        data1.messageDate = 202101011231
        adapter!!.addItem(data1)
        var data2:Data = Data()

        data2.text = "나도 안녕"
        data2.userName = "Hong gil dong"
        data2.messageDate = 202101011232
        adapter!!.addItem(data2)
    }
}