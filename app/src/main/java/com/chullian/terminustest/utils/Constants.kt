package com.chullian.terminustest.utils

/**
 * Created by binMammootty on 02/01/2022.
 */

const val PROGRESS_BAR_GONE = 0x001
const val PROGRESS_BAR_VISIBLE = 0x002

const val SECOND_MILLIS = 1000
const val MINUTE_MILLIS = 60 * SECOND_MILLIS
const val HOUR_MILLIS = 60 * MINUTE_MILLIS
const val DAY_MILLIS = 24 * HOUR_MILLIS


sealed interface UiStates {
    object Idle : UiStates
    data class Error(val message: String) : UiStates
    data class Success(val message:String) : UiStates
}