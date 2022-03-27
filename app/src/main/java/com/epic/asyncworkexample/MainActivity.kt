package com.epic.asyncworkexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val GITHUB_LOGO_IMAGE_URL_VAL2 =
            "https://github.githubassets.com/images/modules/logos_page/Octocat.png"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_view).setOnClickListener {
            DownloadImageAsyncTask(
                object : DownloadImageAsyncTask.Callback {
                    override fun onSuccess(result: Bitmap) {
                        findViewById<ImageView>(R.id.image_view).setImageBitmap(result)
                    }
                }
            ).execute(GITHUB_LOGO_IMAGE_URL_VAL2)
        }
    }

    class DownloadImageAsyncTask(
        private val callback: Callback
    ): AsyncTask<String, Unit, Bitmap>() {

        interface Callback {
            fun onSuccess(result: Bitmap)
        }

        override fun doInBackground(vararg imageUrlString: String): Bitmap {
            // Эта функция работает на Бэкграунд потоке.
            // Здесь мы можем совершать сетевые запросы.
            // Функция вызывается, когда мы выше вызвали метод execute()

            // Загружаем из сети изображение в виде массива байтов.
            val resultByteArray = HttpUrlConnectionExample
                .getUrlBytes(imageUrlString.first())
            // Преобразуем массив байтов в изображение Bitmap и возвращаем как
            // результат выполнения функции.
            return BitmapFactory.decodeByteArray(
                resultByteArray,
                0,
                resultByteArray.size,
                BitmapFactory.Options().also {
                    it.inMutable = true
                }
            )
        }

        override fun onPostExecute(result: Bitmap) {
            // Эта функция работает на UI потоке. Отсюда мы можем обновлять UI.
            // Она автоматически вызывается, когда doInBackground() вернет результат.
            callback.onSuccess(result)
        }
    }
}