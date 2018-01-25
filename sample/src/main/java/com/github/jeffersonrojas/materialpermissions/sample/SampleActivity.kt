package com.github.jeffersonrojas.materialpermissions.sample

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.jeffersonrojas.materialpermissions.library.PermissionManager

class SampleActivity : AppCompatActivity() {

    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        permissionManager = PermissionManager(this)
        permissionManager.autoRequestPermission = true
        permissionManager.addPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionManager.addPermission(Manifest.permission.CAMERA)
        if (permissionManager.havePermission()) {
            Log.i("tag", "permiso aprovado")
        } else {
            permissionManager.requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            Log.i("tag", "permiso aprovado")
        }
    }

}
