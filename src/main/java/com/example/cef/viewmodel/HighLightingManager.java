package com.example.cef.viewmodel;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import com.example.cef.model.Document;
import com.example.cef.model.Language;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighLightingManager {
    private static final Pattern HTML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*>))|(?<CONTENT>\\\\?[^<>]+)");
    private final Document document;

    public HighLightingManager(Document document) {
        this.document = document;
    }

    public static Node highlightHtmlSyntax(String text) {
        TextFlow textFlow = new TextFlow();
        Matcher matcher = HTML_TAG.matcher(text);
        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                textFlow.getChildren().add(new Text(text.substring(lastEnd, matcher.start())));
            }

            if (matcher.group("ELEMENT") != null) {
                textFlow.getChildren().add(createStyledText(matcher.group("ELEMENT"), Color.BLUE, Font.font("monospace", FontWeight.BOLD, 12)));
            } else if (matcher.group("CONTENT") != null) {
                textFlow.getChildren().add(new Text(matcher.group("CONTENT")));
            }

            lastEnd = matcher.end();
        }

        if (lastEnd < text.length()) {
            textFlow.getChildren().add(new Text(text.substring(lastEnd)));
        }

        return textFlow;
    }

    private static Text createStyledText(String text, Color color, Font font) {
        Text styledText = new Text(text);
        styledText.setFill(color);
        styledText.setFont(font);
        return styledText;
    }
}
