package org.textformatter;

public class TextFormatterDemo {

    public static void main(String[] args) {
        System.out.println("=== TEXT FORMATTER DEMO ===\n");

        String sampleText = "Hello World";

        // Demo 1: PlainText Formatter
        System.out.println("--- Demo 1: PlainText Formatter ---");
        TextEditor editor1 = new TextEditor(new PlainTextFromatter());
        editor1.format(sampleText);

        System.out.println();

        // Demo 2: Markdown Formatter
        System.out.println("--- Demo 2: Markdown Formatter ---");
        TextEditor editor2 = new TextEditor(new MarkdownFormatter());
        editor2.format(sampleText);

        System.out.println();

        // Demo 3: HTML Formatter
        System.out.println("--- Demo 3: HTML Formatter ---");
        TextEditor editor3 = new TextEditor(new HtmlFormatter());
        editor3.format(sampleText);

        System.out.println();
        System.out.println("=== DEMO COMPLETE ===");
    }
}
