
import android.os.Build
import android.os.Bundle
import com.example.firstapp.datamodel.TrainerModel
import com.example.firstapp.datamodel.WorkoutItem
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

val TrainerModel.fullTitle: String
    get() = (firstName ?: "") + " " + lastName ?: ""

val WorkoutItem.dateString: String
    get() {
        added?.let { time ->
            val date = Date(time.toLong() * 1000)
            val format = SimpleDateFormat("MM/dd/yyyy")
            return format.format(date)
        }
        return ""
    }

class WorkoutAndTrainer(var workout: WorkoutItem, var trainer: TrainerModel?): Serializable {}

fun <T : Serializable?> Bundle.getSerializableSafe(key: String, m_class: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getSerializable(key, m_class)!!
    else
        this.getSerializable(key) as T
}