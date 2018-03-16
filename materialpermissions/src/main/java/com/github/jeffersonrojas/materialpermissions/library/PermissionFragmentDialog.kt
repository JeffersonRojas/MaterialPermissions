package com.github.jeffersonrojas.materialpermissions.library

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by JeffersonRojas on 1/22/2018.
 */

class PermissionFragmentDialog : DialogFragment() {

    private var onClickContinue: () -> Unit = {}
    private var onClickLater: () -> Unit = {}
    private var permissions: ArrayList<PermissionModel> = ArrayList()
    private var position: Int = 0

    private lateinit var tvRationale: TextView
    private lateinit var tvPosition: TextView
    private lateinit var ivPermissionIcon: ImageView

    fun show(manager: FragmentManager, permissions: ArrayList<PermissionModel>, onClickContinue: () -> Unit, onClickLater: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions.isNotEmpty()) {
            this.permissions = permissions
            this.onClickContinue = onClickContinue
            this.onClickLater = onClickLater
            isCancelable = false
            position = 0
            show(manager, PermissionFragmentDialog::class.java.simpleName)
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hostActivity = activity ?: return super.onCreateDialog(savedInstanceState)
        val inflater = hostActivity.layoutInflater
        val view = inflater.inflate(R.layout.permission_dialog, null)
        val btnContinue = view.findViewById(R.id.permission_btn_continue) as Button
        btnContinue.setOnClickListener { onContinue() }
        val btnLater = view.findViewById(R.id.permission_btn_later) as Button
        btnLater.setOnClickListener { onLater() }
        tvRationale = view.findViewById(R.id.permission_tv_rationale) as TextView
        tvPosition = view.findViewById(R.id.permission_tv_position) as TextView
        ivPermissionIcon = view.findViewById(R.id.permission_iv_icon) as ImageView
        val builder = AlertDialog.Builder(hostActivity)
        builder.setView(view)
        return builder.create()
    }

    private fun onContinue() {
        position++
        if (position >= permissions.size) {
            Handler().postDelayed({
                dismiss()
                onClickContinue()
            }, 200)
        } else {
            onBind(permissions[position])
        }
    }

    private fun onLater() {
        Handler().postDelayed({
            dismiss()
            onClickLater()
        }, 200)
    }

    private fun onBind(permissionModel: PermissionModel) {
        var rationaleText = getString(permissionModel.rationaleMessage)
        if (permissionModel.goToSettings) {
            rationaleText += getString(R.string.permission_got_to_settings)
        }
        tvRationale.text = rationaleText
        ivPermissionIcon.setImageResource(permissionModel.icon)
        if (permissions.size > 1) {
            val positionText = "${position + 1} ${getString(R.string.permission_position_indicator)} ${permissions.size}"
            tvPosition.text = positionText
        } else {
            tvPosition.text = ""
        }
    }

    override fun onResume() {
        super.onResume()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        if (position < permissions.size)
            onBind(permissions[position])
        else
            dismiss()
    }

}