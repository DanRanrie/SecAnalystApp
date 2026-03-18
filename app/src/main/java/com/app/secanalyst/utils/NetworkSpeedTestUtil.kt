package com.app.secanalyst.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

object NetworkSpeedTestUtil {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun downloadSpeed(): Double = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("https://httpbin.org/stream-bytes/5000000")
                .build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext 0.0
            val input = response.body?.byteStream() ?: return@withContext 0.0
            val buffer = ByteArray(4096)
            var totalRead = 0L
            val startTime = System.nanoTime()
            while (true) {
                val read = input.read(buffer)
                if (read == -1) break
                totalRead += read
            }
            input.close()
            val elapsed = (System.nanoTime() - startTime) / 1e9
            (totalRead * 8) / (elapsed * 1e6)
        } catch (_: Exception) {
            0.0
        }
    }

    suspend fun uploadSpeed(context: Context): Double = withContext(Dispatchers.IO) {
        try {
            val size = 1024 * 1024
            val testFile = File.createTempFile("upload_test", ".bin", context.cacheDir).apply {
                deleteOnExit()
                FileOutputStream(this).use { out ->
                    val buf = ByteArray(1024)
                    var remaining = size
                    while (remaining > 0) {
                        out.write(buf, 0, minOf(remaining, buf.size))
                        remaining -= buf.size
                    }
                }
            }
            val conn = URL("https://httpbin.org/post").openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/octet-stream")
            conn.connectTimeout = 5000
            conn.readTimeout = 10000
            var totalWritten = 0L
            val startTime = System.nanoTime()
            testFile.inputStream().use { input ->
                conn.outputStream.use { output ->
                    val buffer = ByteArray(8192)
                    while (true) {
                        val read = input.read(buffer)
                        if (read == -1) break
                        output.write(buffer, 0, read)
                        totalWritten += read
                    }
                }
            }
            if (conn.responseCode != 200) return@withContext 0.0
            val elapsed = (System.nanoTime() - startTime) / 1e9
            testFile.delete()
            (totalWritten * 8) / (elapsed * 1e6)
        } catch (_: Exception) {
            0.0
        }
    }
}
