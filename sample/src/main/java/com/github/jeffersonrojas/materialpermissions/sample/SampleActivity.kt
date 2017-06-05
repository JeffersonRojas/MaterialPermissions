package com.github.jeffersonrojas.materialpermissions.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.jeffersonrojas.materialpermissions.library.PermissionManager
import com.github.jeffersonrojas.materialpermissions.library.Permissions



class SampleActivity : AppCompatActivity() {

    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        permissionManager = PermissionManager(this)
        permissionManager.addPermission(Permissions.ACCESS_COARSE_LOCATION)
        permissionManager.requestCode = 99
        if (permissionManager.havePermission()) {
            Log.i("tag","permiso aprovado")
        } else {
            permissionManager.requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionManager.permissionsResult(requestCode, grantResults)) {
            Log.i("tag","permiso aprovado")
        }
    }

}
