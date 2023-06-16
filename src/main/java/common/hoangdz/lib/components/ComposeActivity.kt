package common.hoangdz.lib.components

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

abstract class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            ComposeContent()
        }
    }

    @Preview(showBackground = false)
    @Composable
    fun PreviewContent() {
        ComposeContent()
    }

    @Composable
    open fun ComposeContent() {

    }
}