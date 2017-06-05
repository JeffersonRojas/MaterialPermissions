package com.github.jeffersonrojas.materialpermissions.library

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Created by Jefferson Rojas on 4/06/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
object Permissions {

    // Group Calendar
    val READ_CALENDAR = Manifest.permission.READ_CALENDAR
    val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR

    // Group Camera
    val CAMERA = Manifest.permission.CAMERA

    // Group Contacts
    val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS
    val GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS

    // Group Location
    val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

    // Group Microphone
    val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO

    // Group Phone
    val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
    val CALL_PHONE = Manifest.permission.CALL_PHONE
    val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
    val WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG
    val ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL
    val USE_SIP = Manifest.permission.USE_SIP
    val PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS

    // Group Sensors
    val BODY_SENSORS = Manifest.permission.BODY_SENSORS

    // Group SMS
    val SEND_SMS = Manifest.permission.SEND_SMS
    val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
    val READ_SMS = Manifest.permission.READ_SMS
    val RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH
    val RECEIVE_MMS = Manifest.permission.RECEIVE_MMS

    // Group Storage
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    // Request Code
    val PERMISSIONS_RC = 2323

    // Permission Status
    val PERMISSION_DENIED = PackageManager.PERMISSION_DENIED
    val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
}