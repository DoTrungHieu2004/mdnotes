package com.hieu10.mdnotes.sample

import com.hieu10.mdnotes.db.models.Note
import java.util.UUID

val singleNoteSample = Note(
    id = UUID.randomUUID().toString(),
    folderId = null,
    title = "Sample note",
    content = """
        # Project Title

        This is a sample paragraph in a Markdown file. It supports **bold text**, *italics*, and [links](https://google.com) [1, 6].

        ## Features
        *   **Plain Text:** Easy to read and edit.
        *   **Portable:** Works everywhere [4].
        *   **Fast:** Quick formatting using symbols.

        ### Usage Example
        1.  Use `#` for headers.
        2.  Use `*` or `-` for bullet points.
        3.  Use `>` for blockquotes.

        > "Markdown is designed to be easy to read and write."

        ---

        ### Code Sample
        ```python
        def hello_world():
            print("Hello, world!")
        ```

        ### Table Example
        | Item | Price |
        | :--- | :--- |
        | Item A | \$10 |
        | Item B | \$20 |
    """.trimIndent(),
    createdAt = System.currentTimeMillis(),
    updatedAt = System.currentTimeMillis(),
    isArchived = false,
    isPinned = true,
    isFavourite = true,
    isTrashed = false,
    trashedAt = null
)

val noteSamples = listOf(
    Note(
        id = "sample-001",
        title = "Weekly Meeting Notes",
        content = "Discussed Q4 roadmap, new feature prioritization, and team capacity planning. Need to follow up on the API integration timeline with the backend team.",
        createdAt = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000,  // 2 days ago
        updatedAt = System.currentTimeMillis() - 30 * 60 * 1000,              // 30 min ago
        isPinned = true,
        isFavourite = true,
        wordCount = 28
    ),
    Note(
        id = "sample-002",
        title = "Markdown Syntax Guide",
        content = "# Headings\n## Subheadings\n**Bold text**\n*Italic text*\n- List items\n[Links](url)\n\nUse triple backticks for code blocks.",
        createdAt = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000,  // 7 days ago
        updatedAt = System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000,  // 5 days ago
        isPinned = true,
        isFavourite = false,
        wordCount = 22
    ),
    Note(
        id = "sample-003",
        title = "Book Recommendations 2024",
        content = "1. Deep Work by Cal Newport\n2. Atomic Habits by James Clear\n3. The Pragmatic Programmer\n4. Designing Data-Intensive Applications",
        createdAt = System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000, // 14 days ago
        updatedAt = System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000, // 14 days ago
        isPinned = false,
        isFavourite = true,
        wordCount = 18
    ),
    Note(
        id = "sample-004",
        title = "Project Ideas",
        content = "Note-taking app with Kotlin and Jetpack Compose. Features: Markdown support, FTS search, tags, folders, cross-note linking, and revision history.",
        createdAt = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000,  // 1 day ago
        updatedAt = System.currentTimeMillis() - 2 * 60 * 60 * 1000,         // 2 hours ago
        isPinned = true,
        isFavourite = false,
        wordCount = 24
    ),
    Note(
        id = "sample-005",
        title = "Grocery List",
        content = "- Eggs\n- Milk\n- Bread\n- Avocados\n- Chicken breast\n- Spinach\n- Greek yogurt\n- Olive oil",
        createdAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000,  // 3 days ago
        updatedAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000,  // 3 days ago
        isPinned = false,
        isFavourite = false,
        wordCount = 12
    ),
    Note(
        id = "sample-006",
        title = "Fitness Routine",
        content = "**Monday:** Chest & Triceps\n**Tuesday:** Back & Biceps\n**Wednesday:** Rest or light cardio\n**Thursday:** Shoulders & Abs\n**Friday:** Legs\n**Weekend:** Active recovery",
        createdAt = System.currentTimeMillis() - 21 * 24 * 60 * 60 * 1000, // 21 days ago
        updatedAt = System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000, // 10 days ago
        isPinned = false,
        isFavourite = false,
        wordCount = 27
    ),
    Note(
        id = "sample-007",
        title = "Code Snippets — Kotlin",
        content = "// Extension function for formatting dates\nfun Long.toReadableDate(): String {\n    val sdf = SimpleDateFormat(\"MMM dd, yyyy\", Locale.getDefault())\n    return sdf.format(Date(this))\n}\n\n// Sealed class for navigation\nsealed class Screen(val route: String) {\n    object Home : Screen(\"home\")\n}",
        createdAt = System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000,  // 5 days ago
        updatedAt = System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000,  // 4 days ago
        isPinned = true,
        isFavourite = true,
        wordCount = 35
    ),
    Note(
        id = "sample-008",
        title = "Travel Packing Checklist",
        content = "Passport, tickets, phone charger, power bank, adapter, toiletries, swimwear, sunglasses, hiking shoes, rain jacket, first-aid kit, snacks for the flight.",
        createdAt = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000, // 30 days ago
        updatedAt = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000, // 30 days ago
        isPinned = false,
        isFavourite = false,
        wordCount = 16
    )
)