package org.textformatter;

public class TextEditor {
    public FormattingStrategy formattingStrategy;
    public TextEditor(FormattingStrategy formattingStrategy){
        this.formattingStrategy=formattingStrategy;
    }
    void format(String text){
        formattingStrategy.getName();
        formattingStrategy.format(text);
    }
}
