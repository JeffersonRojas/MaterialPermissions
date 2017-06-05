package com.github.jeffersonrojas.materialpermissions.library

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by Jefferson Rojas on 4/06/2017.
 */

class PermissionDialog constructor(activity: AppCompatActivity) : Dialog(activity, R.style.permission_dialog_theme) {

    var onClickContinue: () -> Unit = {}
    var tvRationale: TextView? = null
    var ivPermissionIcon: ImageView? = null
    var flagGoToSettings: Boolean = false

    fun postAnimation(run: () -> Unit) {
        postDelayed(run, 200)
    }

    fun postDelayed(run: () -> Unit, delayMillis: Long) {
        Handler().postDelayed(run, delayMillis)
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.permission_dialog, null)
            setContentView(view)
            setCancelable(false)
            val btnContinue = view.findViewById(R.id.permission_btn_continue) as Button
            btnContinue.setOnClickListener {
                postAnimation {
                    dismiss()
                    onClickContinue()
                }
            }
            btnContinue.typeface = getTypeface(activity, R.string.permission_button_text_font)
            val btnLater = view.findViewById(R.id.permission_btn_later) as Button
            btnLater.setOnClickListener {
                postAnimation {
                    dismiss()
                }
            }
            btnLater.typeface = getTypeface(activity, R.string.permission_button_text_font)
            tvRationale = view.findViewById(R.id.permission_tv_rationale) as TextView
            tvRationale?.typeface = getTypeface(activity, R.string.permission_rational_text_font)
            ivPermissionIcon = view.findViewById(R.id.permission_iv_icon) as ImageView
        }
    }

    private fun getTypeface(context: Context, font: Int): Typeface {
        val fontPath = "fonts/" + context.getString(font)
        val assetManager = context.assets
        try {
            return Typeface.createFromAsset(assetManager, fontPath)
        } catch (e: RuntimeException) {
            Log.e(context.javaClass.simpleName, "Error loading the font ", e)
        }
        return Typeface.DEFAULT
    }

    fun addPermission(permission: String) {
        when (permission) {
            Permissions.READ_CALENDAR -> {
                tvRationale?.setText(R.string.permission_rational_read_calendar)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_calendar)
            }
            Permissions.WRITE_CALENDAR -> {
                tvRationale?.setText(R.string.permission_rational_write_calendar)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_calendar)
            }
            Permissions.CAMERA -> {
                tvRationale?.setText(R.string.permission_rational_camera)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_camera)
            }
            Permissions.READ_CONTACTS -> {
                tvRationale?.setText(R.string.permission_rational_read_contacts)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_contacts)
            }
            Permissions.WRITE_CONTACTS -> {
                tvRationale?.setText(R.string.permission_rational_write_contacts)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_contacts)
            }
            Permissions.GET_ACCOUNTS -> {
                tvRationale?.setText(R.string.permission_rational_get_accounts)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_contacts)
            }
            Permissions.ACCESS_FINE_LOCATION -> {
                tvRationale?.setText(R.string.permission_rational_access_fine_location)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_location)
            }
            Permissions.ACCESS_COARSE_LOCATION -> {
                tvRationale?.setText(R.string.permission_rational_access_coarse_location)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_location)
            }
            Permissions.RECORD_AUDIO -> {
                tvRationale?.setText(R.string.permission_rational_record_audio)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_microphone)
            }
            Permissions.READ_PHONE_STATE -> {
                tvRationale?.setText(R.string.permission_rational_read_phone_state)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.CALL_PHONE -> {
                tvRationale?.setText(R.string.permission_rational_call_phone)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.READ_CALL_LOG -> {
                tvRationale?.setText(R.string.permission_rational_read_call_log)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.WRITE_CALL_LOG -> {
                tvRationale?.setText(R.string.permission_rational_write_call_log)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.ADD_VOICEMAIL -> {
                tvRationale?.setText(R.string.permission_rational_add_voice_mail)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.USE_SIP -> {
                tvRationale?.setText(R.string.permission_rational_use_sip)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.PROCESS_OUTGOING_CALLS -> {
                tvRationale?.setText(R.string.permission_rational_process_outgoing_calls)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_phone)
            }
            Permissions.BODY_SENSORS -> {
                tvRationale?.setText(R.string.permission_rational_body_sensors)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_body_sensors)
            }
            Permissions.SEND_SMS -> {
                tvRationale?.setText(R.string.permission_rational_send_sms)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_sms)
            }
            Permissions.RECEIVE_SMS -> {
                tvRationale?.setText(R.string.permission_rational_receive_sms)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_sms)
            }
            Permissions.READ_SMS -> {
                tvRationale?.setText(R.string.permission_rational_read_sms)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_sms)
            }
            Permissions.RECEIVE_WAP_PUSH -> {
                tvRationale?.setText(R.string.permission_rational_receive_wap_push)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_sms)
            }
            Permissions.RECEIVE_MMS -> {
                tvRationale?.setText(R.string.permission_rational_receive_mms)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_sms)
            }
            Permissions.READ_EXTERNAL_STORAGE -> {
                tvRationale?.setText(R.string.permission_rational_read_external_storage)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_storage)
            }
            Permissions.WRITE_EXTERNAL_STORAGE -> {
                tvRationale?.setText(R.string.permission_rational_write_external_storage)
                ivPermissionIcon?.setImageResource(R.drawable.permission_ic_storage)
            }
        }
    }

    fun show(goToSettings: Boolean) {
        flagGoToSettings = goToSettings
        if (goToSettings) {
            val textToSettings = "${tvRationale?.text} ${context.getText(R.string.permission_got_to_settings)}"
            tvRationale?.text = textToSettings
        }
        show()
    }
}