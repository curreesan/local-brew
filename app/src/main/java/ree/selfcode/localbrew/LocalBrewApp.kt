package ree.selfcode.localbrew

import android.app.Application
import ree.selfcode.localbrew.di.Graph

class LocalBrewApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(applicationContext)
    }
}
