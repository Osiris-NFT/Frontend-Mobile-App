package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MetaData(
    @SerializedName("response") val response: String,
    @SerializedName("error") val error: ErrorData
    ): Serializable {}