package com.example.firstapp

import com.google.gson.annotations.SerializedName
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class TrainerModel (
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("photo_url"  ) var photoUrl  : String? = null
)

data class AllTrainerModel (
    var items: List<TrainerModel>? = null
)

data class WorkoutItems (
    @SerializedName("id"              ) var id            : Int?    = null,
    @SerializedName("title"           ) var title         : String? = null,
    @SerializedName("desc"            ) var desc          : String? = null,
    @SerializedName("type"            ) var type          : String? = null,
    @SerializedName("added"           ) var added         : Int?    = null,
    @SerializedName("nbr_rounds"      ) var nbrRounds     : Int?    = null,
    @SerializedName("preview_img_url" ) var previewImgUrl : String? = null,
    @SerializedName("level"           ) var level         : String? = null,
    @SerializedName("trainer_id"      ) var trainerId     : Int?    = null
)

data class FightcampModel (
    var items: List<WorkoutItems>? = null
)