package com.sob3r.chattilo.auth

import android.app.Activity
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebClient(activity: Activity) : WebViewClient() {
    lateinit var accessToken: String
    private val act = activity

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        if (url!!.contains("#access_token=")){
            accessToken = getAccessToken(url)
            act.finish()
        }
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    // Если вдруг изменится authURL то обязательно изменить функцию
    private fun getAccessToken(url: String): String {
        return url.substring(32, 62)
    }

}