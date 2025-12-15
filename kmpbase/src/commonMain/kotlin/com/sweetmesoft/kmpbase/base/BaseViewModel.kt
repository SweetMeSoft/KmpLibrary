package com.sweetmesoft.kmpbase.base

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.russhwolf.settings.Settings
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory

open class BaseViewModel : ViewModel() {
    var baseState by mutableStateOf(BaseState())
        internal set

    data class BaseState(
        val drawerState: DrawerState = DrawerState(DrawerValue.Closed)
    )

    companion object {
        lateinit var navigator: Navigator
        lateinit var bottomSheetNavigator: BottomSheetNavigator
        lateinit var controller: PermissionsController
        lateinit var factory: PermissionsControllerFactory
    }

    val settings = Settings()

    protected suspend fun requestPermission(permission: Permission): Boolean {
        var permissionState by mutableStateOf(PermissionState.Granted)
        if (!controller.isPermissionGranted(permission)) {
            try {
                controller.providePermission(permission)
            } catch (e: DeniedAlwaysException) {
                controller.openAppSettings()
            } catch (e: Exception) {
                println(e)
            }

            permissionState = controller.getPermissionState(permission)
        }

        return permissionState == PermissionState.Granted
    }

    fun updateTab(index: Int) {
        BaseDrawerScreen.currentTab.value = index
    }

    fun updateBottomTab(index: Int) {
        BaseBottomBarScreen.currentTab.value = index
    }

    suspend fun openDrawer() {
        baseState.drawerState.open()
    }

    suspend fun closeDrawer() {
        baseState.drawerState.close()
    }
}