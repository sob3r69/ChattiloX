package com.sob3r.chattilo.auth

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sob3r.chattilo.userdata.UserData
import com.sob3r.chattilo.userdata.UserDataDB
import kotlinx.coroutines.runBlocking

class OverridedWebClient(activity: AppCompatActivity) : WebViewClient() {
    var accessToken: String? = null
    private val act = activity

    val userDatabase by lazy { UserDataDB.getDatabase(activity).userDataDao() }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        if (url!!.contains("#access_token=")){
            accessToken = getAccessToken(url)
            addTokenToDb(accessToken!!)
            act.finish()
        }
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    private fun getAccessToken(url: String): String {
        return url.substring(32, 62)
    }

    private fun addTokenToDb(token: String) = runBlocking {
        userDatabase.insertAll(UserData(1, "user", token))
    }
}