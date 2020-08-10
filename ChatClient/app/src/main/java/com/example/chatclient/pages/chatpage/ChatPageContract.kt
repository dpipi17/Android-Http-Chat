package com.example.chatclient.pages.chatpage

import com.example.chatclient.network.dataclasses.MessageEntity
import com.example.chatclient.pages.chatpage.customviews.CollapsibleToolBarLayoutModel
import com.example.chatclient.pages.chatpage.recyclerview.MessageCellModel
import com.example.chatclient.pages.chatpage.responsedataclasses.ChatPageResponse

interface ChatPageContract {

    interface View {
        fun setUpToolBar(collapsibleToolBarLayoutModel: CollapsibleToolBarLayoutModel)
        fun updateRecyclerView(cells: List<MessageCellModel>)
    }

    interface Presenter {
        fun fetchMessages(myNickName: String, friendNickName: String)
        fun sendMessage(from: String, to: String, text: String)
        fun onMessageFetch(chatPageResponse: ChatPageResponse)
    }

    interface Model {
        fun fetchMessages(myNickName: String, friendNickName: String)
        fun sendMessage(message: MessageEntity)
    }
}