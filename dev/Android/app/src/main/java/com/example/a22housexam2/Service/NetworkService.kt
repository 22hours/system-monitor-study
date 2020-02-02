package com.example.a22housexam2.Service

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

object NetworkService {
    var isNowTotal : Boolean = false
    var isNowRoom : Boolean = false
    var isNowPc : Boolean = false
    var nowSearchPc : String = ""
}