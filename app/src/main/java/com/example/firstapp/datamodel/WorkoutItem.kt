package com.example.firstapp.datamodel

import com.google.gson.annotations.SerializedName

data class WorkoutItem (
    @SerializedName("id"              ) var id            : Int,
    @SerializedName("title"           ) var title         : String? = null,
    @SerializedName("desc"            ) var desc          : String? = null,
    @SerializedName("type"            ) var type          : String? = null,
    @SerializedName("added"           ) var added         : Int?    = null,
    @SerializedName("nbr_rounds"      ) var nbrRounds     : Int?    = null,
    @SerializedName("preview_img_url" ) var previewImgUrl : String? = null,
    @SerializedName("level"           ) var level         : String? = null,
    @SerializedName("trainer_id"      ) var trainerId     : Int
)

data class WorkoutItemList (
    var items: List<WorkoutItem>? = null
)