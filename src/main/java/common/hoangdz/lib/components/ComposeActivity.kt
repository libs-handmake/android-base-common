package common.hoangdz.lib.components

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import common.hoangdz.lib.extensions.configureWindowForTransparency
import common.hoangdz.lib.lifecycle.ActivityLifecycleManager

abstract class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityLifecycleManager.add(this)
        configureWindowForTransparency()
        setContent {
            ComposeContent()
        }
    }

    override fun onRestart() {
        super.onRestart()
        ActivityLifecycleManager.add(this)
    }

    override fun onResume() {
        ActivityLifecycleManager.add(this)
        super.onResume()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        ActivityLifecycleManager.add(this)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?, persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        ActivityLifecycleManager.add(this)
    }

    override fun onDestroy() {
        ActivityLifecycleManager.remove(this)
        super.onDestroy()
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