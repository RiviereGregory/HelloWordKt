package training.android

import com.google.gson.annotations.SerializedName

class GetData(
    @SerializedName("origin") val ip: String,
    val url: String
) {

}