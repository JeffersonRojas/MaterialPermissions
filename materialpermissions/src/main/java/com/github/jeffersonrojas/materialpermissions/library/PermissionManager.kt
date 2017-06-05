package com.github.jeffersonrojas.materialpermissions.library

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import java.util.*

/**
 * Created by Jefferson Rojas on 4/06/2017.
 */

@Suppress("unused")
class PermissionManager(val activity: AppCompatActivity) {

    var requestCode: Int = Permissions.PERMISSIONS_RC
    var time: Long = 0
    var listPermissions: ArrayList<String> = ArrayList()
    val permissionDialog: PermissionDialog = PermissionDialog(activity)

    init {
        permissionDialog.onClickContinue = {
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                if (permissionDialog.flagGoToSettings) {
                    goToSettings()
                } else {
                    showDialogPermissions()
                }
            }
        }
    }

    fun havePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listPermissions.forEach {
                if (ActivityCompat.checkSelfPermission(activity, it) == Permissions.PERMISSION_DENIED )
                    return false
            }
        }
        return true
    }

    fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            time = Calendar.getInstance().timeInMillis
            if (!showRationalePermission()) {
                showDialogPermissions()
                return
            }
            permissionDialog.show(false)
        }
    }

    private fun showDialogPermissions() {
        ActivityCompat.requestPermissions(activity, getPermissions(), requestCode)
    }

    private fun goToSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + activity.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivity(intent)
    }

    private fun showRationalePermission(): Boolean {
        return listPermissions.any { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }
    }

    fun permissionsResult(requestCode: Int, grantResults: IntArray): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode != this.requestCode) return false
            time = Calendar.getInstance().timeInMillis - time
            if (time < 500) {
                permissionDialog.show(true)
                return false
            }
            val havePermission = !grantResults.contains(PackageManager.PERMISSION_DENIED)
            if (havePermission) {
                return true
            } else {
                requestPermissions()
                return false
            }
        }
        return true
    }

    fun addPermission(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listPermissions.add(permission)
            permissionDialog.addPermission(permission)
        }
    }

    private fun getPermissions(): Array<String?> {
        val permissions = arrayOfNulls<String>(listPermissions.size)
        for (i in permissions.indices) {
            permissions[i] = listPermissions[i]
        }
        return permissions
    }
}