package com.example.bottomnavigationviewpagger2retrofit2.mars.model

import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(
    @field:SerializedName("photos") val photos: List<RoverPictures>
)

class RoverPictures {
    var img_src: String = ""
    var earth_date: String = ""
}
