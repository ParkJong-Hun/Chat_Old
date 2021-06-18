package com.example.chattutorial

import java.util.*
//MessageAdapter에서 사용할 data 구성
class Data {
    //내용
    var text: String? = null
    //사용자 이름
    var userName: String? = null
    //작성 날짜
    var messageDate: Date? = null
    //사용자 uid(불변)
    var uid: String? = null
}