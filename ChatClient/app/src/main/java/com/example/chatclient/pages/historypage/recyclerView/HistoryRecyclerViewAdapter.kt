package com.example.chatclient.pages.historypage.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.network.dataclasses.HistoryResponse
import com.example.chatclient.pages.historypage.HistoryPagePresenterImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryRecyclerViewAdapter(val presenter : HistoryPagePresenterImpl, private val navController: NavController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data : MutableList<HistoryResponse> = mutableListOf()
    private lateinit var dataRequestHandler: DataRequestHandler

    private var clickListener = View.OnClickListener {
        val args = Bundle()
        args.putString("friendNickName", data[it.tag as Int].friend_nickname)
        args.putInt("chatId", data[it.tag as Int].chat_id)
        navController.navigate(R.id.action_historyPageFragment_to_chatPageFragment, args)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_recycler_view_item, parent, false)
        view.setOnClickListener(clickListener)
        return HistoryRecyclerViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val historyRecyclerViewViewHolder = holder as HistoryRecyclerViewViewHolder
        historyRecyclerViewViewHolder.setUpView(data[position])
        historyRecyclerViewViewHolder.itemView.tag = historyRecyclerViewViewHolder.adapterPosition

        if(position == data.size - 1 && data.size % 10 == 0) {
            dataRequestHandler.requestNewDataLazyLoading(position)
        }
    }

    fun setUpView(dataRequestHandler: DataRequestHandler) {
        this.dataRequestHandler = dataRequestHandler
    }

    interface DataRequestHandler {
        fun requestNewDataLazyLoading(position: Int)
    }

    fun addDataForLazyLoading(newData: MutableList<HistoryResponse>){
        val oldSize = data.size
        data.addAll(newData)
        notifyItemRangeChanged(oldSize, data.size)
    }

    fun changeData(newData: MutableList<HistoryResponse>){
        data = newData
        notifyDataSetChanged()
    }

    fun returnAllData() : List<HistoryResponse> {
        return data
    }

}