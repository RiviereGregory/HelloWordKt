package training.android

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebviewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    val webViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Log.i("WebviewActivity", "Loading URL=$url")

            val containsStudio = url?.contains("studio") ?: false
            if (containsStudio) {
                Toast.makeText(
                    this@WebviewActivity,
                    "Chargement Android Studio",
                    Toast.LENGTH_SHORT
                )
            }

            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        webView = findViewById(R.id.webview)
        webView.webViewClient = webViewClient

        val settings = webView.settings
        settings.javaScriptEnabled = true

        webView.loadUrl("https://developer.android.com")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}