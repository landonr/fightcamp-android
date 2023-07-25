import android.app.Activity
import android.os.Build
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItems
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

val TrainerModel.fullTitle: String
    get() = (firstName ?: "") + " " + lastName ?: ""

val WorkoutItems.dateString: String
    get() {
        added?.let { time ->
            val date = Date(time.toLong() * 1000)
            val format = SimpleDateFormat("MM/dd/yyyy")
            return " ⦁ " + format.format(date)
        }
        return ""
    }

class WorkoutAndTrainer(var workout: WorkoutItems, var trainer: TrainerModel?): Serializable {}

fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): T
{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        activity.intent.getSerializableExtra(name, clazz)!!
    else
        activity.intent.getSerializableExtra(name) as T
}