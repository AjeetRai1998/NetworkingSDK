import android.os.Handler
import android.os.HandlerThread
import android.os.Process

class MyThreadExecutor {
    fun execute(runnable: Runnable?) {
        val handlerThread = HandlerThread("PriorityHandlerThread",Process.THREAD_PRIORITY_DEFAULT)
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post(runnable!!)
    }
}