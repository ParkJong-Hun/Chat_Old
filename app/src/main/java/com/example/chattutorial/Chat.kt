package com.example.chattutorial

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import java.text.SimpleDateFormat
import java.util.*


//This is Chat Activity
class Chat : AppCompatActivity() {
    //어댑터
    private var adapter: MessageAdapter? = null
    //로그인 한 사용자 이름
    val currentUserName = FirebaseAuth.getInstance().currentUser?.displayName.toString()
    //로그인 한 사용자 UID
    val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid.toString()

    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.chattutorial.R.layout.chat)

        init()
        UpdateData()


        val button: Button = findViewById(com.example.chattutorial.R.id.submitButton)
        button.setOnClickListener{
            SubmitText()
        }
    }

    //리사이클러뷰 어댑터 연결
    private fun init() {
        recyclerView = findViewById(com.example.chattutorial.R.id.recyclerView)
        var linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)

        adapter = MessageAdapter()
        recyclerView.adapter = adapter
        //InitData()
    }

    //내용 전송
    private fun SubmitText() {
        var editText:EditText = findViewById(com.example.chattutorial.R.id.inputText)
        //Firestore에 보낼 Data
        if(editText.text.isNotEmpty() || editText.text.isNotBlank()) {
            val data = hashMapOf(
                "uid" to currentUserUID,
                "text" to editText.text.toString(),
                "userName" to currentUserName,
                "messageDate" to Date()
            )
            //Query를 사용하지 않고 문서 이름에 날짜와 uid를 추가해 정렬해서 Firestore에 document 추가
            FirebaseFirestore.getInstance().collection("Chat").document(
                "${SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA).format(Date())
                    .toString()}_${currentUserUID}"
            ).set(data)
            //전송 후 editText를 비움
            editText.setText("")
            //화면 하단으로 이동
            recyclerView.scrollToPosition(adapter!!.getItemCount())
        }
    }
    //채팅 초기 설정
    /*fun InitData() {
        FirebaseFirestore.getInstance().collection("Chat")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data:Data = Data()
                    data.text = document["text"].toString()
                    data.userName = document["userName"].toString()
                    val stamp: Timestamp = document["messageDate"] as Timestamp
                    data.messageDate = stamp.toDate()
                    data.uid = document["uid"].toString()
                    adapter?.addItem(data)
                    adapter?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "가져오기를 실패했습니다.", exception)
            }
    }*/
    //채팅 업데이트
    fun UpdateData() {
        //Snapshot(Coolection의 전체 문서)이 업데이트 될 때 마다 확인하는 리스너
        FirebaseFirestore.getInstance().collection("Chat").addSnapshotListener{ querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            //Snapshot이 null이 아닌지 체크
            if (querySnapshot != null) {
                //똑같은 내용이 겹치지 않게 리스트를 초기화.
                adapter?.resetItem()
                //스냅샷의 처음부터 끝까지
                for(newDocument in querySnapshot) {
                    //각 데이터를 가져와 adapter의 ArrayList에 추가
                    var newData:Data = Data()
                    newData.text = newDocument["text"].toString()
                    newData.uid = newDocument["uid"].toString()
                    newData.userName = newDocument["userName"].toString()
                    val stamp: Timestamp = newDocument["messageDate"] as Timestamp
                    newData.messageDate = stamp.toDate()
                    adapter?.addItem(newData)
                }
            }
            adapter?.notifyDataSetChanged()
        }
    }

}