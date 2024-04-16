package com.john.wathermvvm.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


class AddressAutoComplete : ActivityResultContract<Unit, Place?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        val fields = listOf(Place.Field.ADDRESS, Place.Field.NAME)


        return Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields
        )
            .build(context)
    }


    override fun parseResult(resultCode: Int, intent: Intent?): Place? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return Autocomplete.getPlaceFromIntent(intent!!)
    }

}