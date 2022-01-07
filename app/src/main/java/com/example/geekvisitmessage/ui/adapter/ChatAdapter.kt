package com.example.geekvisitmessage.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekvisitmessage.databinding.MassFragBinding
import com.example.geekvisitmessage.databinding.MassFragMeBinding

class ChatAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<ChatModel> = ArrayList()

    val right: Int = 1
    val left: Int = 2

    @SuppressLint("NotifyDataSetChanged")
    fun addText(list: ChatModel) {
        this.list.add(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == right) {
            return ViewHolderOne(
                MassFragBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
            )
        } else {
            return ViewHolderTwo(
                MassFragMeBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].message.equals("Hello")) {
            val holder1 = holder as ViewHolderOne
            holder1.bind(list[position])
        } else {
            val holder2 = holder as ViewHolderTwo
            holder2.bind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].message.equals("Hello")) {
            return right
        } else {
            return left
        }

    }


    inner class ViewHolderOne(private val binding: MassFragBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(s: ChatModel) {
            binding.textMessage.text = s.message

        }
    }

    inner class ViewHolderTwo(private val binding: MassFragMeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(s: ChatModel) {
            binding.sendTextMessage.text = s.message
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}