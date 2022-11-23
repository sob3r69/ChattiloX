package com.sob3r.chattilo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi

class AuthActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val authURL = "https://id.twitch.tv/oauth2/authorize" +
                "?response_type=token" +
                "&client_id=vh91xa0cpprldqdt2787gl7ul263pc" +
                "&redirect_uri=https://localhost" +
                "&scope=chat%3Aread+chat%3Aedit"

        val webView: WebView = findViewById(R.id.authWebView)
        webView.webViewClient = MyWebClient(this)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(authURL)
    }
}

class MyWebClient(activity: Activity) : WebViewClient() {
    lateinit var accessToken: String
    private val act = activity

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        if (url!!.contains("#access_token=")){
            accessToken = getAccessToken(url)
            println("$accessToken")
            act.finish()
        }
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    // Если вдруг изменится authURL то обязательно изменить функцию
    private fun getAccessToken(url: String): String {
        return url.substring(32, 62)
    }

}