package com.hieu10.mdnotes.ui.components

import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun HighlightedSnippet(
    snippet: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 3
) {
    val markdownColors = LocalMarkdownColors.current

    val context = LocalContext.current
    val spanned = remember(snippet) {
        // FTS snippet uses <b>...</b> for matches
        HtmlCompat.fromHtml(snippet, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    AndroidView(
        factory = { ctx ->
            TextView(ctx).apply {
                movementMethod = LinkMovementMethod.getInstance()
                this.maxLines = maxLines
                setTextColor(
                    markdownColors.textSecondary.toArgb()
                )
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            }
        },
        update = { textView ->
            textView.text = spanned
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewHighlightedSnippetLight() {
    MDNotesTheme(darkTheme = false) {
        HighlightedSnippet(snippet = "This is a snippet")
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHighlightedSnippetDark() {
    MDNotesTheme(darkTheme = true) {
        HighlightedSnippet(snippet = "This is a snippet")
    }
}