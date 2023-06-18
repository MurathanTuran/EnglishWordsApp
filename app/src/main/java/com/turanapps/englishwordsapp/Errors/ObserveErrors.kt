package com.turanapps.englishwordsapp.Errors

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class ObserveErrors() {

    companion object {
        fun observe(lifecycleOwner: LifecycleOwner, context: Context, vararg errors: Error) {
            errors.forEach { error ->
                error.errorB.observe(lifecycleOwner, Observer {
                    if (it == true) {
                        showToastMessage(context, error.error)
                        error.errorB.value = false
                    }
                })
            }
        }

        private fun showToastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}