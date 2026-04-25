package com.hieu10.mdnotes.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.ui.theme.extensions.MarkdownShapes
import com.hieu10.mdnotes.ui.theme.extensions.SemanticShapes

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

val MarkdownShapesDefault = MarkdownShapes(
    codeBlock = RoundedCornerShape(12.dp),
    quoteBlock = RoundedCornerShape(8.dp),
    callout = RoundedCornerShape(12.dp),
    inlineCode = RoundedCornerShape(6.dp)
)

val SemanticShapesDefault = SemanticShapes(
    success = RoundedCornerShape(12.dp),
    warning = RoundedCornerShape(12.dp),
    info = RoundedCornerShape(12.dp)
)