package org.textformatter;

public class HtmlFormatter implements FormattingStrategy{
    @Override
    public void format(String text) {
        System.out.println("****** Html Formatter *******");
    }

    @Override
    public void getName() {
        System.out.println("Html Formatter");
    }
}
