package com.sob3r.chattilo.auth

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.sob3r.chattilo.R

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

        // OverridedWebClient чекает текущий url,
        // И если он содержит auth_token то парсит его
        webView.webViewClient = OverridedWebClient(this)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(authURL)
    }
}