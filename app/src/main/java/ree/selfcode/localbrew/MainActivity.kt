package ree.selfcode.localbrew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ree.selfcode.localbrew.ui.navigation.NavGraph
import ree.selfcode.localbrew.ui.theme.LocalbrewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalbrewTheme {
                NavGraph()
            }
        }
    }
}
