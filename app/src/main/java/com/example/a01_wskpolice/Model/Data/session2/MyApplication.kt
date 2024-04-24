package com.example.a01_wskpolice.Model.Data.session2

import android.app.Application
import com.yandex.mapkit.MapKitFactory


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("secret")
    }
}
