package com.chullian.terminustest.utils

/**
 * Created by binMammootty on 02/01/2022.
 */

const val PROGRESS_BAR_GONE = 0x001
const val PROGRESS_BAR_VISIBLE = 0x002


sealed interface UiStates {
    object Idle : UiStates
    data class Error(val message: String) : UiStates
    data class Success(val message:String) : UiStates
}