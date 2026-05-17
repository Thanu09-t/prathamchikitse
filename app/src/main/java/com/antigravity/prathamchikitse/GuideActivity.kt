package com.antigravity.prathamchikitse

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.antigravity.prathamchikitse.databinding.ActivityGuideBinding
import com.antigravity.prathamchikitse.models.EmergencyTopic
import java.util.Locale

class GuideActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityGuideBinding
    private var topic: EmergencyTopic? = null
    private var tts: TextToSpeech? = null
    private var isKannada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topic = intent.getSerializableExtra("topic") as? EmergencyTopic
        
        binding.tvGuideTitle.text = topic?.title

        val adapter = GuidePagerAdapter(this, topic?.steps ?: emptyList(), isKannada)
        binding.viewPager.adapter = adapter

        tts = TextToSpeech(this, this)

        binding.btnToggleLanguage.setOnClickListener {
            isKannada = !isKannada
            adapter.isKannada = isKannada
            adapter.notifyDataSetChanged()
        }

        binding.btnTTS.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            val step = topic?.steps?.getOrNull(currentItem)
            step?.let {
                val text = if (isKannada) it.descriptionKa else it.descriptionEn
                val lang = if (isKannada) Locale("kn", "IN") else Locale.US
                tts?.language = lang
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
