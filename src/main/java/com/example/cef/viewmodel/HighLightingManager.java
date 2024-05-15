package com.example.cef.viewmodel;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import com.example.cef.model.Document;
import com.example.cef.model.Language;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighLightingManager {
    private static final Pattern HTML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*>))|(?<CONTENT>\\\\?[^<>]+)");
    private static final Pattern CSS_RULE = Pattern.compile("(?<SELECTOR>\\w+\\s*\\{)|(?<PROPERTY>\\w+\\s*:)|(\\})|(?<VALUE>.+?;)");
    private static final Pattern JAVASCRIPT_KEYWORD = Pattern.compile("\\b(break|case|catch|continue|default|delete|do|else|finally|for|function|if|in|instanceof|new|return|switch|this|throw|try|typeof|var|void|while|with)\\b");

    private final Document document;
    private Language language;

    public HighLightingManager(Document document) {
        this.document = document;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void highlightSyntax(TextArea codeArea, TextFlow textFlow) {
        textFlow.getChildren().clear();

        String text = codeArea.getText();
        if (text != null && !text.isEmpty()) {
            Matcher matcher = getPatternForLanguage().matcher(text);
            int lastEnd = 0;
            while (matcher.find()) {
                // Add plain text before the matched pattern
                if (matcher.start() > lastEnd) {
                    textFlow.getChildren().add(new Text(text.substring(lastEnd, matcher.start())));
                }

                // Add styled text for the matched pattern
                if (matcher.group("ELEMENT") != null) {
                    textFlow.getChildren().add(createStyledText("element", matcher.group("ELEMENT")));
                } else if (matcher.group("SELECTOR") != null) {
                    textFlow.getChildren().add(createStyledText("selector", matcher.group("SELECTOR")));
                } else if (matcher.group("PROPERTY") != null) {
                    textFlow.getChildren().add(createStyledText("property", matcher.group("PROPERTY")));
                } else if (matcher.group("VALUE") != null) {
                    textFlow.getChildren().add(createStyledText("value", matcher.group("VALUE")));
                } else {
                    textFlow.getChildren().add(createStyledText("keyword", matcher.group()));
                }

                lastEnd = matcher.end();
            }

            // Add remaining plain text after the last match
            if (lastEnd < text.length()) {
                textFlow.getChildren().add(new Text(text.substring(lastEnd)));
            }
        }
    }

    private Text createStyledText(String style, String text) {
        Text styledText = new Text(text);
        styledText.getStyleClass().add(style);
        return styledText;
    }

    private Pattern getPatternForLanguage() {
        switch (language) {
            case HTML:
                return HTML_TAG;
            case CSS:
                return CSS_RULE;
            case JAVASCRIPT:
                return JAVASCRIPT_KEYWORD;
            default:
                return Pattern.compile("\\b");
        }
    }
}
