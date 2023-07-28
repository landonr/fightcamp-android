import android.util.Log
import com.example.firstapp.BuildConfig

class Logger {
    companion object {
        fun debugLog(tag: String, log: String) {
            if (BuildConfig.BUILD_TYPE.equals("debug")) {
                Log.d(tag, log)
            }
        }
    }
}