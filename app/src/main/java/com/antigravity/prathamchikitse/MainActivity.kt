package com.antigravity.prathamchikitse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.antigravity.prathamchikitse.databinding.ActivityMainBinding
import com.antigravity.prathamchikitse.models.EmergencyTopic
import com.antigravity.prathamchikitse.models.GuideStep

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topics = getEmergencyTopics()
        
        binding.rvEmergencies.layoutManager = GridLayoutManager(this, 2)
        binding.rvEmergencies.adapter = EmergencyAdapter(topics) { topic ->
            val intent = Intent(this, GuideActivity::class.java)
            intent.putExtra("topic", topic)
            startActivity(intent)
        }

        binding.btnHospitalFinder.setOnClickListener {
            startActivity(Intent(this, HospitalFinderActivity::class.java))
        }
    }

    private fun getEmergencyTopics(): List<EmergencyTopic> {
        return listOf(
            EmergencyTopic(
                id = "choking",
                title = getString(R.string.choking),
                iconResId = android.R.drawable.ic_dialog_alert,
                steps = listOf(
                    GuideStep(1, "Assess Situation", "Ask 'Are you choking?'. If they cannot speak or cough, proceed.", "ಪರಿಸ್ಥಿತಿಯನ್ನು ಅಂದಾಜಿಸಿ", "ಅವರು ಉಸಿರುಗಟ್ಟುತ್ತಿದ್ದರೆ, ಮಾತನಾಡಲು ಸಾಧ್ಯವಾಗದಿದ್ದರೆ ಮುಂದುವರಿಯಿರಿ."),
                    GuideStep(2, "Give 5 Back Blows", "Stand behind them. Give 5 firm back blows between shoulder blades.", "೫ ಬಾರಿ ಬೆನ್ನಿಗೆ ಹೊಡೆಯಿರಿ", "ಅವರ ಹಿಂದೆ ನಿಂತು ಭುಜದ ನಡುವೆ ೫ ಬಾರಿ ಹೊಡೆಯಿರಿ."),
                    GuideStep(3, "Give 5 Abdominal Thrusts", "Perform Heimlich maneuver until the object is dislodged.", "೫ ಬಾರಿ ಹೊಟ್ಟೆಗೆ ಒತ್ತಡ ಹಾಕಿ", "ವಸ್ತುವು ಹೊರಬರುವವರೆಗೆ ಹೈಮ್ಲಿಕ್ ತಂತ್ರವನ್ನು ಮಾಡಿ.")
                )
            ),
            EmergencyTopic(
                id = "burns",
                title = getString(R.string.burns),
                iconResId = android.R.drawable.ic_dialog_info,
                steps = listOf(
                    GuideStep(1, "Cool the Burn", "Hold burned skin under cool running water for 10-15 minutes.", "ಸುಟ್ಟ ಗಾಯವನ್ನು ತಂಪುಗೊಳಿಸಿ", "೧೦-೧೫ ನಿಮಿಷ ತಣ್ಣೀರಿನಲ್ಲಿ ಇಡಿ."),
                    GuideStep(2, "Protect the Burn", "Cover loosely with sterile, non-stick bandage.", "ಗಾಯವನ್ನು ರಕ್ಷಿಸಿ", "ಅಂಟಿಕೊಳ್ಳದ ಶುದ್ಧ ಬ್ಯಾಂಡೇಜ್ ನಿಂದ ಮುಚ್ಚಿ.")
                )
            ),
            EmergencyTopic(
                id = "snake_bite",
                title = getString(R.string.snake_bite),
                iconResId = android.R.drawable.ic_dialog_alert,
                steps = listOf(
                    GuideStep(1, "Stay Calm and Still", "Do not panic. Keep the bitten area still and lower than the heart.", "ಶಾಂತವಾಗಿರಿ", "ಭಯಪಡಬೇಡಿ. ಕಚ್ಚಿದ ಭಾಗವನ್ನು ಹೃದಯಕ್ಕಿಂತ ಕೆಳಗೆ ಇರಿಸಿ."),
                    GuideStep(2, "Remove Constrictions", "Remove rings or tight clothing near the bite.", "ಬಿಗಿಯಾದ ವಸ್ತುಗಳನ್ನು ತೆಗೆಯಿರಿ", "ಉಂಗುರ ಅಥವಾ ಬಿಗಿಯಾದ ಬಟ್ಟೆಗಳನ್ನು ತೆಗೆಯಿರಿ.")
                )
            ),
            EmergencyTopic(
                id = "fracture",
                title = getString(R.string.fracture),
                iconResId = android.R.drawable.ic_dialog_alert,
                steps = listOf(
                    GuideStep(1, "Stop Bleeding", "Apply pressure to the wound with a sterile bandage.", "ರಕ್ತಸ್ರಾವ ನಿಲ್ಲಿಸಿ", "ಗಾಯಕ್ಕೆ ಶುದ್ಧ ಬ್ಯಾಂಡೇಜ್ ನಿಂದ ಒತ್ತಡ ಹಾಕಿ."),
                    GuideStep(2, "Immobilize the Area", "Do not try to realign the bone. Splint if possible.", "ಜಾಗವನ್ನು ಅಲುಗಾಡಿಸಬೇಡಿ", "ಮೂಳೆಯನ್ನು ಸರಿಪಡಿಸಲು ಪ್ರಯತ್ನಿಸಬೇಡಿ.")
                )
            ),
            EmergencyTopic(
                id = "heart_attack",
                title = getString(R.string.heart_attack),
                iconResId = android.R.drawable.ic_dialog_alert,
                steps = listOf(
                    GuideStep(1, "Call Emergency Services", "Call for help immediately.", "ತುರ್ತು ಸೇವೆಗೆ ಕರೆ ಮಾಡಿ", "ತಕ್ಷಣ ಸಹಾಯಕ್ಕೆ ಕರೆ ಮಾಡಿ."),
                    GuideStep(2, "Chew Aspirin", "If not allergic, have the person chew an aspirin.", "ಆಸ್ಪಿರಿನ್ ಅಗಿಯಲು ಹೇಳಿ", "ಅಲರ್ಜಿ ಇಲ್ಲದಿದ್ದರೆ, ಆಸ್ಪಿರಿನ್ ಅಗಿಯಲು ಹೇಳಿ.")
                )
            )
        )
    }
}
