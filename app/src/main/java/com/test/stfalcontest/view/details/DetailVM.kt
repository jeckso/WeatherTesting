package com.test.stfalcontest.view.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailVM (
    val name: String,
    val value: String
):Parcelable