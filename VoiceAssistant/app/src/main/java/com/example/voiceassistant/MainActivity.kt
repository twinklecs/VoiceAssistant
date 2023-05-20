package com.example.voiceassistant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import java.net.URL
import java.util.*

data class MessageJson(val message:String)

class MessageAdapter(private var context: Context,
                     private val messages : ArrayList<Message>) : BaseAdapter() {

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(position: Int): Any {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View? {
        var convertView: View
        val holder = MessageViewHolder()
        val messageInflater =
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val message: Message = messages[i]
        if (message.isBelongsToCurrentUser()) {
            convertView = messageInflater.inflate(R.layout.my_message, null)
            holder.messageBody = convertView.findViewById<View>(R.id.message_body) as TextView
            convertView.tag = holder
            holder.messageBody!!.text = message.text
        } else {
            convertView = messageInflater.inflate(R.layout.their_message, null)
            holder.avatar = convertView.findViewById(R.id.avatar) as View
            holder.name = convertView.findViewById<View>(R.id.name) as TextView
            holder.messageBody = convertView.findViewById<View>(R.id.message_body) as TextView
            convertView.tag = holder
            holder.messageBody!!.text = message.text
            val drawable = holder.avatar!!.background as GradientDrawable
        }
        return convertView
    }
}

internal class MessageViewHolder {
    var avatar: View? = null
    var name: TextView? = null
    var messageBody: TextView? = null
}

class Message(val text: String?, private val isBelongsToCurrentUser: Boolean){

    @JvmName("getText1")
    fun getText(): String? {
        return text
    }

    @JvmName("isBelongsToCurrentUser1")
    fun isBelongsToCurrentUser(): Boolean {
        return isBelongsToCurrentUser
    }
}

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null

    lateinit var micIV: ImageView
    lateinit var sendMessage: ImageButton
    private lateinit var editText: EditText

    private lateinit var listView: ListView

    private val messages : ArrayList<Message> = ArrayList()

    fun onMessage(text:String?, isUser: Boolean) {

        val message = Message(text , isUser)
        messages.add(message)

        val adapter = MessageAdapter(this, messages)

        listView.adapter = adapter

        scrollMyListViewToBottom()
    }

    private val REQUEST_CODE_SPEECH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        micIV = findViewById(R.id.idIVMic)
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.messages_view)
        sendMessage = findViewById(R.id.sendMessage)


        tts = TextToSpeech(this, this)

        if (Build.VERSION.SDK_INT > 9) {
            val gfgPolicy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(gfgPolicy)
        }


        micIV.setOnClickListener {
            tts!!.stop()

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Говорите...")

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {

                Toast
                    .makeText(
                        this@MainActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }

        sendMessage.setOnClickListener{
            hideKeyboard()

            val message = editText.text.toString()
            if (message != null) {
                if (message.length > 0) {
                    onMessage(message, true)
                    try{
                        GetRequest(message)
                    }catch (e: Exception){
                        onMessage("Отсутсвует подключение к интернету!", true)
                    }
                    editText!!.text.clear()
                }
            }
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    private fun GetRequest(request: String) {
        val webPage = URL("http://192.168.0.106:5000/" + request)
        val data = webPage.readText()

        val gson = Gson()
        val td = gson.fromJson(data, MessageJson::class.java)

        onMessage(td.message, false)
        speakOut(td.message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {

            if (resultCode == RESULT_OK && data != null) {

                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                onMessage(Objects.requireNonNull(res)[0], true)
                GetRequest(Objects.requireNonNull(res)[0])
            }
        }
    }

    private fun scrollMyListViewToBottom() {
        listView.post(Runnable {
            listView.setSelection(listView.getCount() - 1)
        })
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","Этот язык не поддерживается!")
            }
        }
    }
}