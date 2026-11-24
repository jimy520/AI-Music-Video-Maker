package com.beyond.musicmaker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {
    private lateinit var selectedAudioText: TextView
    private lateinit var titleInput: EditText
    private lateinit var lyricInput: EditText
    private lateinit var statusText: TextView
    private var audioUri: Uri? = null

    private val audioPicker = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            audioUri = uri
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            selectedAudioText.text = "Audio dipilih: ${uri.lastPathSegment ?: "file audio"}"
            statusText.text = "Audio siap. Tambahkan lirik untuk koreksi dan sinkronisasi."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(11,16,32)
        window.navigationBarColor = android.graphics.Color.rgb(11,16,32)

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20))
            background = getDrawable(R.drawable.app_bg)
        }
        val scroll = ScrollView(this)
        scroll.addView(page)

        page.addView(text("♪  AI MUSIC VIDEO MAKER", 20f, "#FFFFFF", true).apply {
            setPadding(0, dp(18), 0, dp(4))
        })
        page.addView(text("HYBRID LOCAL STUDIO  •  ANDROID", 10f, "#A998FF", true))
        page.addView(text("Buat video musik\nlangsung dari HP.", 29f, "#FFFFFF", true).apply {
            setPadding(0, dp(28), 0, dp(8))
        })
        page.addView(text("Target: visual AI 3D, subtitle karaoke per kata, opening title, MP4 720p.", 14f, "#AAB5D0", false))

        page.addView(section("01  •  DETAIL LAGU"))
        titleInput = EditText(this).apply {
            hint = "Judul lagu (tampil di opening saja)"
            setTextColor(android.graphics.Color.WHITE)
            setHintTextColor(android.graphics.Color.GRAY)
            setSingleLine(true)
            setPadding(dp(14))
            setBackgroundColor(android.graphics.Color.rgb(20,28,48))
        }
        page.addView(titleInput, matchWrap())

        val choose = button("PILIH FILE AUDIO")
        choose.setOnClickListener { audioPicker.launch(arrayOf("audio/*")) }
        page.addView(choose, matchWrap())
        selectedAudioText = text("Belum ada audio dipilih", 12f, "#AAB5D0", false)
        page.addView(selectedAudioText)

        page.addView(section("02  •  LIRIK"))
        page.addView(text("Masukkan lirik resmi untuk mengurangi kesalahan transkripsi. Sinkronisasi timestamp per kata akan menjadi tahap pemrosesan berikutnya.", 12f, "#8996B3", false))
        lyricInput = EditText(this).apply {
            hint = "Tempel lirik lagu di sini..."
            minLines = 5
            gravity = Gravity.TOP
            setTextColor(android.graphics.Color.WHITE)
            setHintTextColor(android.graphics.Color.GRAY)
            setPadding(dp(14))
            setBackgroundColor(android.graphics.Color.rgb(20,28,48))
        }
        page.addView(lyricInput, matchWrap())

        page.addView(section("03  •  PENGATURAN VIDEO"))
        page.addView(text("Resolusi: 720p (1280 × 720)\nVisual: AI 3D Animation\nSubtitle: Karaoke highlight per kata\nJudul: Opening saja\nMode: Hybrid lokal", 13f, "#D7DDF0", false))

        val buildButton = button("SIAPKAN PROYEK VIDEO")
        buildButton.setOnClickListener {
            when {
                audioUri == null -> statusText.text = "Pilih file audio terlebih dahulu."
                titleInput.text.isNullOrBlank() -> statusText.text = "Isi judul lagu terlebih dahulu."
                lyricInput.text.isNullOrBlank() -> statusText.text = "Masukkan lirik resmi agar bisa dikoreksi."
                else -> statusText.text = "Input tersimpan untuk alur produksi. Mesin timestamp karaoke, generator AI 3D, dan render lokal belum terpasang pada build prototipe ini."
            }
        }
        page.addView(buildButton, matchWrap())
        statusText = text("Prototipe native berjalan di perangkat. Belum membutuhkan server laptop.", 12f, "#A998FF", false)
        page.addView(statusText)

        page.addView(text("Catatan: versi ini adalah fondasi APK native. Generasi video AI 3D lokal di Galaxy A55 perlu model khusus yang ukurannya besar dan pengujian performa/termal.", 11f, "#77839E", false).apply {
            setPadding(0, dp(26), 0, dp(28))
        })
        setContentView(scroll)
    }

    private fun section(s: String) = text(s, 12f, "#A998FF", true).apply {
        setPadding(0, dp(24), 0, dp(10))
    }

    private fun text(s: String, size: Float, color: String, bold: Boolean): TextView =
        TextView(this).apply {
            text = s
            textSize = size
            setTextColor(android.graphics.Color.parseColor(color))
            if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(0, dp(5), 0, dp(5))
        }

    private fun button(s: String) = Button(this).apply {
        text = s
        isAllCaps = false
        setTextColor(android.graphics.Color.WHITE)
        backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.rgb(118,89,237))
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply { bottomMargin = dp(8); topMargin = dp(6) }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
}
