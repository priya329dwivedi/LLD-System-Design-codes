# Problem Statement 3: Text Formatter

## Problem Description

Build a **TextEditor** that accepts a **FormattingStrategy** to format text in different ways:
- **PlainText**
- **Markdown**
- **HTML**

Same input text produces different output based on the selected strategy.

## Design Focus

- **Different Output Behaviors**: Same input, different formatted output
- **Isolated Formatting Logic**: Each strategy handles only its own formatting
- **Unit Testing**: Each strategy can be tested independently with edge cases

## Project Structure

```
org/textformatter/
├── README.md
├── FormattingStrategy.java     # Interface
├── PlainTextFormatter.java     # Concrete strategy
├── MarkdownFormatter.java      # Concrete strategy
├── HtmlFormatter.java          # Concrete strategy
├── TextEditor.java             # Context class
└── TextFormatterDemo.java      # Demo/test class
```

## Class Diagram

```
+----------------------+         +------------------+
|  FormattingStrategy  |<--------|   TextEditor     |
|     <<interface>>    |         +------------------+
+----------------------+         | - strategy       |
| + format(text): String|        +------------------+
| + getName(): String   |        | + format(text)   |
+----------------------+         | + setStrategy()  |
         ^                       +------------------+
         |
    +----+----+----+
    |         |    |
+-------+ +--------+ +------+
| Plain | |Markdown| | Html |
| Text  | |Formatter| |Format|
+-------+ +--------+ +------+
```

## Expected Behavior

| Input | PlainText | Markdown | HTML |
|-------|-----------|----------|------|
| "Hello World" | Hello World | Hello World | Hello World |
| "**bold**" | **bold** | **bold** | `<strong>bold</strong>` |
| "# Title" | # Title | # Title | `<h1>Title</h1>` |
| "- item" | - item | - item | `<li>item</li>` |

## Design Pattern

**Strategy Pattern** - Encapsulates formatting algorithms, allowing them to be interchangeable at runtime.

## Key Principles

- **Single Responsibility**: Each formatter handles only its format
- **Open/Closed**: Add new formats (e.g., JSON, XML) without modifying TextEditor
- **Testability**: Each strategy can be unit tested in isolation

## Unit Testing Focus

Test edge cases for each strategy:
- Empty string
- Null input
- Special characters
- Nested formatting (e.g., bold inside italic)
- Multiple paragraphs
- Line breaks

## Expected Usage

```java
// Create editor with a strategy
TextEditor editor = new TextEditor(new MarkdownFormatter());
String output = editor.format("# Hello **World**");

// Switch strategy at runtime
editor.setStrategy(new HtmlFormatter());
output = editor.format("# Hello **World**");
// Output: <h1>Hello <strong>World</strong></h1>
```
