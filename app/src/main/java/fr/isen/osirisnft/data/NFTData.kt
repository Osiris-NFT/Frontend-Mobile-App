package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NFTData(
    @SerializedName("id") val _id: String,
    @SerializedName("wallet") val wallet: String,
    @SerializedName("is_published") val is_published: Boolean,
    @SerializedName("metadata") val metadata: MetaData
): Serializable {}