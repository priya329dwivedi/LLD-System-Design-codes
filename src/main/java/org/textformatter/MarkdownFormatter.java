package org.textformatter;

public class MarkdownFormatter implements FormattingStrategy{
    @Override
    public void format(String text) {
        System.out.println("******* MarkdownFormatter ********");
    }

    @Override
    public void getName() {
        System.out.println("Markdown Formatter");
    }
}
