package com.example.geekvisitmessage.ui.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.geekvisitmessage.R
import com.example.geekvisitmessage.databinding.FragmentMessageBinding
import com.example.geekvisitmessage.ui.adapter.ChatAdapter
import com.example.geekvisitmessage.ui.adapter.ChatModel

class MessageFragment() : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    var image: String = ""
    private val chatAdapter = ChatAdapter(
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        sendMessage()
        initFields()
        sendVoice()
    }

    private fun init() {
        binding.recyclerMessage.adapter = chatAdapter
        binding.recyclerMessage.layoutManager = LinearLayoutManager(context)
    }


    private fun sendMessage() = with(binding) {
        imageSendMessage.setOnClickListener {
            val name = editInputMessage.text.toString()
            editInputMessage.text.clear()
            val list = ChatModel(name, image)
            chatAdapter.addText(list)
        }
    }

    private fun initFields() = with(binding) {
        editInputMessage.addTextChangedListener {
            val string = editInputMessage.text.toString()
            if (string.isEmpty() || string == "Запись") {
                imageSendMessage.visibility = View.GONE
                imagePhotoMessage.visibility = View.VISIBLE
                imageVoiceMessage.visibility = View.VISIBLE
                emojiImage.visibility = View.VISIBLE
            } else {
                imageSendMessage.visibility = View.VISIBLE
                imagePhotoMessage.visibility = View.GONE
                imageVoiceMessage.visibility = View.GONE
                emojiImage.visibility = View.VISIBLE
            }
        }
        imagePhotoMessage.setOnClickListener {
            mGetContent.launch("image/*")
        }

    }

    val mGetContent = registerForActivityResult<String, Uri>(
        ActivityResultContracts.GetContent()
    ) { uri ->
        image = uri.toString()
        Glide.with(binding.imagePhotoMessage).load(image).centerCrop()
            .into(binding.imagePhotoMessage)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun sendVoice() = with(binding) {
        imageVoiceMessage.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
//                    TODO record
                editInputMessage.setText("Запись")
                imagePhotoMessage.visibility = View.GONE
                imageVoiceMessage.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        R.color.design_default_color_primary_dark
                    )
                )
            } else if (event.action == MotionEvent.ACTION_UP) {
//                    TODO stop record
                editInputMessage.setText("")
                imagePhotoMessage.visibility = View.VISIBLE
                imageVoiceMessage.colorFilter = null
            }
            true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


