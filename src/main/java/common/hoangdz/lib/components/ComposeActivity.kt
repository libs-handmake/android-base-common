package common.hoangdz.lib.components

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

abstract class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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