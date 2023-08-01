
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItemList
import com.example.firstapp.services.ApiWorker
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FightCampApiService {
    @GET("workouts")
    suspend fun getWorkouts(@Query("offset") page: Int): Response<WorkoutItemList>

    @GET("trainers/{id}")
    suspend fun getTrainerById(@Path("id") trainerId: Int): Response<TrainerModel>
}

final class ApiConstants {
    companion object {
        val fightCampBaseURL: String = "https://android-trial.fightcamp.io/"
    }
}

object ApiService {
    private val TAG = "--ApiService"

    fun fightCampApi() = Retrofit.Builder()
        .baseUrl(ApiConstants.fightCampBaseURL)
        .addConverterFactory(ApiWorker.gsonConverter)
        .client(ApiWorker.client)
        .build()
        .create(FightCampApiService::class.java)!!
}

fun <T>resultFromResponse(call: Response<T>): Result<T> {
    if (call.isSuccessful) {
        call.body()?.run {
            return Result.success(this)
        }
        return Result.failure(Throwable("Value missing"))
    }
    call.errorBody()?.string()?.run {
        return Result.failure(Throwable(this))
    }
    return Result.failure(Throwable("Value missing"))
}