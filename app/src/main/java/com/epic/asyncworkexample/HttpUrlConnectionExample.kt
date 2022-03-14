package com.epic.asyncworkexample

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object HttpUrlConnectionExample {

    fun getUrlBytes(urlString: String): ByteArray {
        val url = URL(urlString)
        val httpUrlConnection = url.openConnection() as HttpURLConnection

        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val inputStream = httpUrlConnection.inputStream

            if (httpUrlConnection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("${httpUrlConnection.responseMessage}: with $urlString")
            }

            var bytesRead: Int
            val bufferByteArray = ByteArray(1024)
            while ((inputStream.read(bufferByteArray).also { bytesRead = it }) > 0) {
                byteArrayOutputStream.write(bufferByteArray, 0, bytesRead)
            }
            byteArrayOutputStream.close()

            return byteArrayOutputStream.toByteArray()
        } finally {
            httpUrlConnection.disconnect()
        }
    }
}