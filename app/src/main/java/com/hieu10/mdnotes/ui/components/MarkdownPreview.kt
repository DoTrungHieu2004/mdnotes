package com.hieu10.mdnotes.ui.components

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.hieu10.mdnotes.sample.data.singleNoteSample
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import io.noties.markwon.Markwon
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.image.ImagesPlugin

@Composable
fun MarkdownPreview(
    content: String,
    modifier: Modifier = Modifier
) {
    val markdownColors = LocalMarkdownColors.current
    val context = LocalContext.current

    val markwon = remember {
        Markwon.builder(context)
            .usePlugin(CorePlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(context))
            .usePlugin(TaskListPlugin.create(context))
            .usePlugin(ImagesPlugin.create())
            .build()
    }

    AndroidView(
        factory = { ctx ->
            TextView(ctx).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = { textView ->
            markwon.setMarkdown(textView, content)
        },
        modifier = modifier.fillMaxWidth().padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewMarkdownLight() {
    MDNotesTheme(darkTheme = false) {
        MarkdownPreview(content = singleNoteSample.content)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMarkdownDark() {
    MDNotesTheme(darkTheme = true) {
        MarkdownPreview(content = singleNoteSample.content)
    }
}