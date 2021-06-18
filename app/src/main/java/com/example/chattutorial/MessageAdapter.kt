package com.example.chattutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

//This is Message Adapter of Chat
class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ItemViewHolder>() {
    // adapter에 사용될 데이터
    private val listData: ArrayList<Data> = ArrayList<Data>()
    //현재 User 이름
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        //ViewHolder 반환
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false)
        return ItemViewHolder(view)
    }
    //각 Item 보여주는(bind 되는) 함수
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(listData[position])
    }

    //RecyclerView의 실행될 횟수 입니다.
    override fun getItemCount(): Int {
        return listData.size
    }

    //외부에서 item 추가
    fun addItem(data: Data) {
        listData.add(data)
    }

    //리스트 리셋
    fun resetItem() {
        listData.removeAll(listData)
    }

    //subView 세팅하는 ViewHolder
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //뷰 선언
        val myText:TextView
        val myUserName:TextView
        val myMessageDate:TextView
        val whoText:TextView
        val whoUserName:TextView
        val whoMessageDate:TextView
        //뷰 객체와 레이아웃의 뷰 id를 바인드
        init {
            myText = itemView.findViewById(R.id.myText)
            myUserName = itemView.findViewById(R.id.myUserName)
            myMessageDate = itemView.findViewById(R.id.myMessageDate)
            whoText = itemView.findViewById(R.id.whoText)
            whoUserName = itemView.findViewById(R.id.whoUserName)
            whoMessageDate = itemView.findViewById(R.id.whoMessageDate)
        }
        //뷰의 값 변경
        fun onBind(data: Data) {
            //내가 쓴 글이라면?
            if(data.uid.equals(currentUserEmail)) {
                //내용
                myText.setText(data.text)
                myText.visibility = View.VISIBLE
                //사용자 이름
                myUserName.setText(data.userName)
                myUserName.visibility = View.VISIBLE
                //날짜
                val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
                myMessageDate.setText(dateFormat.format(data.messageDate))
                myMessageDate.visibility = View.VISIBLE
                //message.xml에서 상대 글 UI를 감춤.
                whoText.visibility = View.GONE
                whoUserName.visibility = View.GONE
                whoMessageDate.visibility = View.GONE
            }
            //내가 쓴 글이 아니라면?
            else {
                //내용
                whoText.setText(data.text)
                whoText.visibility = View.VISIBLE
                //사용자 이름
                whoUserName.setText(data.userName)
                whoUserName.visibility = View.VISIBLE
                //날짜
                val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
                whoMessageDate.setText(dateFormat.format(data.messageDate))
                whoMessageDate.visibility = View.VISIBLE
                //message.xml에서 자신 글 뷰 UI를 감춤.
                myText.visibility = View.GONE
                myUserName.visibility = View.GONE
                myMessageDate.visibility = View.GONE
            }
        }
    }
}