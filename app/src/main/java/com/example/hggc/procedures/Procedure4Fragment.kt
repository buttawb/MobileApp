package com.example.hggc.procedures

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.example.hggc.PdfTextToSpeechHelper
import com.example.hggc.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 * Use the [Policy1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Procedure4Fragment : Fragment() {
    private var mMediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    // TODO: Rename and change types of parameters
    private lateinit var pdfTextToSpeechHelper: PdfTextToSpeechHelper
    private var isReading = false
    private var tab_select = true
    private lateinit var tabLayout: TabLayout
    private lateinit var urdu_scrollview: ScrollView
    private lateinit var english_scrollview: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_procedure4, container, false)
        val urduText = "\"دستی ہینڈلنگ:\\n\" +\n" +
                "                \"1. جب ممکن ہو تو مکینیکل ایڈز یا مدد استعمال کریں\\n\" +\n" +
                "                \"2. ایک ہی وقت میں کبھی نہ جھکیں، نہ اٹھائیں اور نہ موڑیں!\\n\" +\n" +
                "                \"3. اپنے گھٹنوں کو موڑیں اور اپنی ٹانگیں اٹھانے کے لیے استعمال کریں!\\n\" +\n" +
                "                \"4۔ دونوں ہاتھوں سے پکڑو\\n\" +\n" +
                "                \"5. جسم کے قریب رہیں\\n\" +\n" +
                "                \"6۔ اپنی ٹانگیں موڑیں اور اپنی پیٹھ سیدھی رکھیں\\n\" +\n" +
                "                \"7. ضرورت پڑنے پر مدد حاصل کریں۔ اگر بوجھ بہت زیادہ ہے تو اسے اکیلے اٹھانے کی کوشش نہ کریں\\n\" +\n" +
                "                \"8. ٹانگوں سے اٹھائیں -- پیچھے نہیں\\n\" +\n" +
                "                \"9۔ وزن کو جسم کے قریب رکھیں\\n\" +\n" +
                "                \"10۔ جھٹکے سے نہ اٹھائیں\\n\" +\n" +
                "                \"11. کندھے کی اونچائی سے اوپر نہ اٹھائیں\""
        // PDF content (replace with your actual PDF text)
        val engText = "MANUAL HANDLING:\n" +
                "1. Use mechanical aids or assistance when possible\n" +
                "2. Never bend, lift, and twist at the same time!\n" +
                "3. Bend your knees and use your legs to lift!\n" +
                "4. Hold with both hands\n" +
                "5. Keep close to the body\n" +
                "6. Bend your legs and keep your back straight\n" +
                "7. Get Help if Needed. If the load is too heavy, DON'T TRY TO LIFT IT ALONE\n" +
                "8. Lift with the Legs -- NOT THE BACK\n" +
                "9. Keep the weight close to the body\n" +
                "10. Do not lift with a jerk\n" +
                "11. Do not lift above shoulder height"
        pdfTextToSpeechHelper = PdfTextToSpeechHelper(requireContext())

        // Find the "Read PDF" button

        // Find the "Read PDF" button
        val floatingButton = view.findViewById<FloatingActionButton>(R.id.floating_button)
        floatingButton.setOnClickListener {
            if (isReading) {
                // Stop reading and change button text
                isReading = false
                pdfTextToSpeechHelper.stopSpeaking()
                //                btnRead.text = "Start Reading"
                floatingButton.setImageResource(R.drawable.baseline_voice_over_off_24)

            } else if (isPlaying) {
                isPlaying = false
                mMediaPlayer!!.release()
                mMediaPlayer = null
                floatingButton.setImageResource(R.drawable.baseline_voice_over_off_24)


            } else {
                // Start reading and change button text
                if (tab_select) {
                    // Read the first text
                    isReading = true
                    pdfTextToSpeechHelper.startSpeaking(
                        engText,
                        object : PdfTextToSpeechHelper.OnCompletionListener {
                            override fun onCompletion() {
                                // This is called when the speech is completed
                                floatingButton.setImageResource(R.drawable.baseline_voice_over_off_24)
                                onPause()
                                activity?.runOnUiThread {
                                    // You can add any actions to perform when the first text is done reading
                                }
                            }
                        })
                    floatingButton.setImageResource(R.drawable.baseline_record_voice_over_24)


                } else {
                    // Read the second text
                    mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.pr4)
                    mMediaPlayer!!.isLooping = false

// Set up an OnCompletionListener

// Set up an OnCompletionListener
                    mMediaPlayer!!.setOnCompletionListener { mediaPlayer ->
                        // The audio playback has completed, so stop the MediaPlayer
                        floatingButton.setImageResource(R.drawable.baseline_voice_over_off_24)
                        onStop()
                        isPlaying = false // Update the flag
                    }

// Start the audio playback

// Start the audio playback
                    mMediaPlayer!!.start()
                    isPlaying = true


                    floatingButton.setImageResource(R.drawable.baseline_record_voice_over_24)
                }


            }

        }

        // Find the TabLayout
        english_scrollview = view.findViewById(R.id.english_scrollview)
        urdu_scrollview = view.findViewById(R.id.urdu_scrollview)

        // Find the TabLayout
        tabLayout = view.findViewById(R.id.tabLayout)

// Create tabs with titles
        val englishTab = tabLayout.newTab().setText("English")
        val urduTab = tabLayout.newTab().setText("Urdu")

// Add the tabs to the TabLayout
        tabLayout.addTab(englishTab)
        tabLayout.addTab(urduTab)

// Set up TabLayout listener to handle tab changes
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> { // English tab is selected
                            english_scrollview.visibility = View.VISIBLE
                            urdu_scrollview.visibility = View.GONE
                            tab_select = true
                        }

                        1 -> { // Urdu tab is selected
                            english_scrollview.visibility = View.GONE
                            urdu_scrollview.visibility = View.VISIBLE
                            tab_select = false
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not needed in this case
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not needed in this case
            }
        })

        return view


    }

    // 2. Pause playback

    override fun onPause() {
        super.onPause()
        // Stop TTS when the app goes into the background or is paused
        if (isReading) {
            pdfTextToSpeechHelper.stopSpeaking()
            isReading = false

        }

        if (isPlaying) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
            isPlaying = false
        }
    }

    override fun onDestroy() {
        pdfTextToSpeechHelper.shutdown()
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
}
