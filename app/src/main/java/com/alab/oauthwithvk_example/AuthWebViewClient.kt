package com.alab.oauthwithvk_example

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.URLEncoder

/**
 * Представляет WebView клиент.
 * @param context Контекст.
 * @param onStatusChange Обработчик смены статуса аутентификации.
 */
class AuthWebViewClient(
    private val context: Context,
    private val onStatusChange: (status: AuthStatus) -> Unit
) : WebViewClient() {
    private var _currentUrl = ""

    override fun shouldOverrideUrlLoading(wv: WebView, url: String): Boolean {
        wv.loadUrl(url)
        return true
    }

    override fun onPageFinished(wv: WebView, url: String) {
        if (_currentUrl != url) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //если открыто окно аутентификации.
            if (url.contains("https://oauth.vk.com/authorize")) {
                val scope = URLEncoder.encode(VKAccountService.SCOPE, "UTF-8")
                // Если открыто окно ввода логина и пароля.
                if (url.contains(scope)) {
                    imm.showSoftInput(wv, 0)
                    wv.visibility = View.VISIBLE
                    onStatusChange(AuthStatus.AUTH)
                }
                // Если открыто окно подтверждения разрешений.
                if (url.contains("q_hash")) {
                    onStatusChange(AuthStatus.CONFIRM)
                }
                // Если открыто окно с сообщением об неверно введеном пароле.
                if (url.contains("email")) {
                    onStatusChange(AuthStatus.ERROR)
                }
            }
            // Если открыто окно заблокированного пользователя.
            if (url.contains("https://m.vk.com/login\\?act=blocked")) {
                onStatusChange(AuthStatus.BLOCKED)
            }
            // Если открыто окно для считывания токена.
            if (url.contains("https://oauth.vk.com/blank.html")) {
                wv.visibility = View.INVISIBLE
                onStatusChange(AuthStatus.SUCCESS)
            }
        }
        _currentUrl = url
    }
}