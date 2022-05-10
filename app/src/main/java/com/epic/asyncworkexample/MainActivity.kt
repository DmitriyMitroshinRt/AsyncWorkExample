package com.epic.asyncworkexample

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation

class MainActivity : AppCompatActivity() {

    companion object {
        private const val GITHUB_LOGO_IMAGE_URL_VAL =
            "https://github.githubassets.com/images/modules/logos_page/Octocat.png"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.image_view)

        findViewById<Button>(R.id.button_view).setOnClickListener {
            imageView.load(GITHUB_LOGO_IMAGE_URL_VAL) {
                listener(
                    onError = { _: ImageRequest, result: ErrorResult ->
                        Toast.makeText(
                            applicationContext,
                            "Error: ${result.throwable.message}. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                crossfade(8500)
                transformations(
                    RoundedCornersTransformation(180F)
                )
                error(findViewById<ImageView>(R.id.image_view).drawable)
            }
        }
    }

    /*
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
     */
}