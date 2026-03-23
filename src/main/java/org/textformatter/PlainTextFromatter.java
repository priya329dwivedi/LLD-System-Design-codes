package org.textformatter;

public class PlainTextFromatter implements FormattingStrategy{
    @Override
    public void format(String text) {
        System.out.println("****** PlainText Fromatter *******");
    }

    @Override
    public void getName() {
        System.out.println("PlainText Fromatter");
    }
}
