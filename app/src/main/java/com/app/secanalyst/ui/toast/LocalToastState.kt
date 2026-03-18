package com.app.secanalyst.ui.toast

import androidx.compose.runtime.compositionLocalOf

val LocalToastState = compositionLocalOf<ToastState> { error("No ToastState provided") }
