package com.testing.findyourbudy.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChatResponse (
    var id: String,
    var createdAt: String,
    var name: String,
    var avatar: String,
    var message: String,
    var whosend: Boolean
)



