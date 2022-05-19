package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ErrorData(
    @SerializedName("status_code") val status_code: Int,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
    ): Serializable {}