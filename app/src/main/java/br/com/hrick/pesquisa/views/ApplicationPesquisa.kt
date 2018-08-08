package br.com.hrick.pesquisa.views

import android.app.Application
import com.j256.ormlite.android.apptools.OpenHelperManager

class ApplicationPesquisa : Application() {

    override fun onTerminate() {
        OpenHelperManager.releaseHelper()
        super.onTerminate()
    }
}