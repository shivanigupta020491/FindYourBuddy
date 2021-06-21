package com.testing.findyourbudy.utils

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startIntentSenderForResult

class ReUsuable_Function {


// For show alert dialog
    fun builderMessage1(builder: AlertDialog.Builder, msg: String?, title: String?) {
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(
            "Ok"
        ) { dialogInterface, i ->
            //clearAllEditTextData();
            dialogInterface.dismiss()


            //showPopUp();
            //                                        dialogInterface.dismiss();
        }
        builder.show()
    }



}