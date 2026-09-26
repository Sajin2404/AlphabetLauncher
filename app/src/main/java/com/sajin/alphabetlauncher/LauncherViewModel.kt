package com.sajin.alphabetlauncher

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sajin.alphabetlauncher.data.AppInfo
import com.sajin.alphabetlauncher.data.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AppRepository(application)

    var apps by mutableStateOf<List<AppInfo>>(
        emptyList()
    )
        private set

    var selectedLetter by mutableStateOf<Char?>(null)
        private set

    var isDragging by mutableStateOf(false)
        private set

    init {
        loadApps()
    }

    private fun loadApps() {

        viewModelScope.launch(Dispatchers.IO) {

            val result =
                repository.getLaunchableApps()

            withContext(Dispatchers.Main) {
                apps = result
            }
        }
    }

    fun selectLetter(letter: Char) {
        selectedLetter = letter
    }

    // IMPORTANT: renamed from setDragging()
    // to avoid conflict with the Boolean property setter.
    fun updateDragging(value: Boolean) {

        isDragging = value

        if (!value) {
            selectedLetter = null
        }
    }

    fun getAppsForLetter(
        letter: Char
    ): List<AppInfo> {

        return apps.filter {

            it.name
                .firstOrNull()
                ?.uppercaseChar() == letter
        }
    }

    fun launchApp(app: AppInfo) {
        repository.launchApp(app)
    }
}