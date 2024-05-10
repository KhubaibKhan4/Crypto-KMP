import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.company.app.App
import org.company.app.di.appModule
import org.koin.core.context.startKoin
import java.awt.Dimension

fun main() = application {
    Window(
        title = "Crypto-KMP",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(1280, 720)
        startKoin {
            modules(appModule)
        }
        App()
    }
}