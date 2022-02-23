package com.alab.oauthwithvk_example

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Представляет приложение.
 */
class App : Application() {
    /**
     * Возвращает или устанавливает сервис хранения настроект.
     */
    lateinit var accountService: IAccountService

    /**
     * Возвращает или устанавливает экземпляр ретрофита.
     */
    lateinit var retrofit: Retrofit

    companion object {
        lateinit var application: App
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        accountService = VKAccountService(getSharedPreferences("vk_account", MODE_PRIVATE))
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}