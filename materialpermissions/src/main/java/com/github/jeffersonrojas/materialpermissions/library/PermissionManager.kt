package com.github.jeffersonrojas.materialpermissions.library

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Jefferson Rojas on 4/06/2017.
 */

@Suppress("MemberVisibilityCanBePrivate")
class PermissionManager(val activity: AppCompatActivity) {

    var requestCode: Int = 2323

    var autoRequestPermission: Boolean = true

    private var onClickLater: () -> Unit = {}

    private var flagGoToSettings: Boolean = false

    private var time: Long = 0

    private val permissionFragmentDialog: PermissionFragmentDialog = PermissionFragmentDialog()

    private var listPermissions: ArrayList<String> = ArrayList()

    fun addPermission(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isValidPermission(permission) && !listPermissions.contains(permission)) {
            listPermissions.add(permission)
        }
    }

    fun havePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listPermissions.forEach {
                if (ActivityCompat.checkSelfPermission(activity, it) == PermissionChecker.PERMISSION_DENIED)
                    return false
            }
        }
        return true
    }

    fun requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        time = Calendar.getInstance().timeInMillis
        if (listPermissions.any { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) } || flagGoToSettings) {
            val permissionsDenied = getDeniedPermissions()
            permissionFragmentDialog.show(activity.supportFragmentManager, permissionsDenied, this::onClickContinue, onClickLater)
        } else {
            ActivityCompat.requestPermissions(activity, listPermissions.toTypedArray(), requestCode)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, @Suppress("UNUSED_PARAMETER") permissions: Array<String>, grantResults: IntArray): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (requestCode != this.requestCode) return false
        time = Calendar.getInstance().timeInMillis - time
        flagGoToSettings = time < 500
        val permissionDenied = grantResults.contains(PackageManager.PERMISSION_DENIED)
        if (permissionDenied && (autoRequestPermission || flagGoToSettings)) {
            requestPermissions()
        }
        return !permissionDenied
    }

    private fun getDeniedPermissions(): ArrayList<PermissionModel> {
        val permissionsDenied: ArrayList<PermissionModel> = ArrayList()
        listPermissions.filter {
            ActivityCompat.checkSelfPermission(activity, it) == PermissionChecker.PERMISSION_DENIED
        }.mapTo(permissionsDenied) {
                    createPermissionModel(it)
                }
        return permissionsDenied
    }

    private fun onClickContinue() {
        if (flagGoToSettings) {
            goToSettings()
        } else {
            ActivityCompat.requestPermissions(activity, listPermissions.toTypedArray(), requestCode)
        }
    }

    private fun goToSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + activity.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivityForResult(intent, requestCode)
    }

    private fun isValidPermission(permission: String): Boolean {
        return when (permission) {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> true
            else -> false
        }
    }

    private fun createPermissionModel(permission: String): PermissionModel {
        return when (permission) {
            Manifest.permission.READ_CALENDAR -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_calendar,
                    rationaleMessage = R.string.permission_rational_read_calendar,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.WRITE_CALENDAR -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_calendar,
                    rationaleMessage = R.string.permission_rational_write_calendar,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.CAMERA -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_camera,
                    rationaleMessage = R.string.permission_rational_camera,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.READ_CONTACTS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_contacts,
                    rationaleMessage = R.string.permission_rational_read_contacts,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.WRITE_CONTACTS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_contacts,
                    rationaleMessage = R.string.permission_rational_write_contacts,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.GET_ACCOUNTS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_contacts,
                    rationaleMessage = R.string.permission_rational_get_accounts,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.ACCESS_FINE_LOCATION -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_location,
                    rationaleMessage = R.string.permission_rational_access_fine_location,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.ACCESS_COARSE_LOCATION -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_location,
                    rationaleMessage = R.string.permission_rational_access_coarse_location,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.RECORD_AUDIO -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_microphone,
                    rationaleMessage = R.string.permission_rational_record_audio,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.READ_PHONE_STATE -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_read_phone_state,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.CALL_PHONE -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_call_phone,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.READ_CALL_LOG -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_read_call_log,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.WRITE_CALL_LOG -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_write_call_log,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.ADD_VOICEMAIL -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_add_voice_mail,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.USE_SIP -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_use_sip,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.PROCESS_OUTGOING_CALLS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_phone,
                    rationaleMessage = R.string.permission_rational_process_outgoing_calls,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.BODY_SENSORS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_body_sensors,
                    rationaleMessage = R.string.permission_rational_body_sensors,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.SEND_SMS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_sms,
                    rationaleMessage = R.string.permission_rational_send_sms,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.RECEIVE_SMS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_sms,
                    rationaleMessage = R.string.permission_rational_receive_sms,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.READ_SMS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_sms,
                    rationaleMessage = R.string.permission_rational_read_sms,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.RECEIVE_WAP_PUSH -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_sms,
                    rationaleMessage = R.string.permission_rational_receive_wap_push,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.RECEIVE_MMS -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_sms,
                    rationaleMessage = R.string.permission_rational_receive_mms,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.READ_EXTERNAL_STORAGE -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_storage,
                    rationaleMessage = R.string.permission_rational_read_external_storage,
                    goToSettings = flagGoToSettings
            )
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> PermissionModel(
                    permission = permission,
                    icon = R.drawable.permission_ic_storage,
                    rationaleMessage = R.string.permission_rational_write_external_storage,
                    goToSettings = flagGoToSettings
            )
            else -> throw IllegalStateException("Permission $permission is not valid")
        }
    }

}