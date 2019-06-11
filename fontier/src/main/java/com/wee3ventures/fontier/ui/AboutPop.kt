package com.wee3ventures.fontier.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.wee3ventures.fontier.Enumaration.Fonts
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.model.AboutModel
import kotlinx.android.synthetic.main.fragment_about_form_common_gdvo.*

class AboutPop (val response : AboutModel) : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_about_form_common_gdvo,container,false)
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
        toolbar.setNavigationOnClickListener { dismiss() }
        toolbar.title = " "
        return view
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null){
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        setUtility()
    }

    @Suppress("SENSELESS_COMPARISON")
    @SuppressLint("SetTextI18n")
    private fun setUtility(){
        if (response != null){
            setFont(response.app_name_fontName,response.version_fontName,response.descFontName,response.common_fontName)
            when {
                response.app_name != null -> appNamelabel.text = "${response.app_name}"
                response.description != null -> desclabel.text = "${response.description}"
                response.app_version != null -> versionlabel.text = "${resources.getString(R.string.version)} ${response.app_version}"
                response.toolbar_title != null -> toolbar.title = "${response.toolbar_title}"
            }
            Glide.with(this.activity!!.applicationContext)
                .load(response.logo)
                .circleCrop()
                .into(logoView)
        }
    }

    private fun setFont(@Nullable app_name_font : Fonts ?= Fonts.MERRIEWEATHER_BOLD, @Nullable version_fontName : Fonts ?= Fonts.POPPINS_BOLD, @Nullable descFont : Fonts ?= Fonts.POPPINS_REGULAR, @Nullable commonFont : Fonts ?= null){
        when {
            app_name_font != null -> com.wee3ventures.fontier.utils.Fonts.setFont(view = appNamelabel,fontName = app_name_font)
            descFont != null -> com.wee3ventures.fontier.utils.Fonts.setFont(view = desclabel,fontName = descFont)
            version_fontName != null -> com.wee3ventures.fontier.utils.Fonts.setFont(view = versionlabel,fontName = version_fontName)
            commonFont != null -> com.wee3ventures.fontier.utils.Fonts.setFont(views = listOf(appNamelabel,desclabel,versionlabel),fontName = commonFont)
        }
    }

}