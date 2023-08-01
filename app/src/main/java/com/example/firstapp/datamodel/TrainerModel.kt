package com.example.firstapp.datamodel

import com.google.gson.annotations.SerializedName

data class AllTrainerModel(
    var items: List<TrainerModel>? = null
)

data class TrainerModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("photo_url") var photoUrl: String? = null
)