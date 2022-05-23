package fr.isen.osirisnft.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MetaData(
    @SerializedName("contract_address") val contract_address: String,
    @SerializedName("transaction_hash") val transaction_hash: String
): Serializable {}