package com.wee3ventures.fontier.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList

enum class PermissionType {
    CAMERA,STORAGE,GPS,PHONE,STORAGE_CAMERA
}

object Permissions {
    private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    private val STORAGE_PERMISSION = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val CAMERA_STORAGE_PERMISSION = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
    private val GPS_PERMISSION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
    private val PHONE_PERMISSION = arrayOf(Manifest.permission.READ_PHONE_STATE)

    fun checkPermission(context: Context, permissions : PermissionType) : Boolean{
        return when (permissions){
            PermissionType.CAMERA->{
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            }
            PermissionType.STORAGE->{
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            }
            PermissionType.GPS->{
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            }
            PermissionType.PHONE->{
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
            }
            PermissionType.STORAGE_CAMERA -> {
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    fun checkPermission(context: Context, permissions : String) : Boolean {
        return ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED
    }

    fun askPermission(activity: Activity, permissions : PermissionType, permissionCode : Int){
        when (permissions){
            PermissionType.CAMERA->{
                ActivityCompat.requestPermissions(activity, CAMERA_PERMISSION, permissionCode)
            }
            PermissionType.STORAGE->{
                ActivityCompat.requestPermissions(activity, STORAGE_PERMISSION, permissionCode)
            }
            PermissionType.GPS->{
                ActivityCompat.requestPermissions(activity, GPS_PERMISSION, permissionCode)
            }
            PermissionType.PHONE->{
                ActivityCompat.requestPermissions(activity, PHONE_PERMISSION, permissionCode)
            }
            PermissionType.STORAGE_CAMERA-> {
                ActivityCompat.requestPermissions(activity, CAMERA_STORAGE_PERMISSION, permissionCode)
            }
        }
    }

    fun askPermission(activity: Activity, permissions: List<String> , permissionCode: Int){
        ActivityCompat.requestPermissions(activity, permissions.toTypedArray(),permissionCode)
    }
}