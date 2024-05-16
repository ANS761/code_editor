package com.example.cef.viewmodel;

import com.example.cef.model.Language;
import com.example.cef.view.EditorPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.richtext.TextExt;

import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CodeTab extends Tab {

    private static final Pattern HTML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*>))|(?<CONTENT>\\\\?[^<>]+)");
    private static final Pattern CSS_RULE = Pattern.compile("(?<SELECTOR>\\w+\\s*\\{)|(?<PROPERTY>\\w+\\s*:)|(\\})|(?<VALUE>.+?;)");

    private CodeArea codeArea;
    private TextFlow textFlow;
    public CodeTab(String title, String codeAreaContent, Language language) {
        super(title);
        textFlow = new TextFlow();
        codeArea = new CodeArea("");
        if (codeAreaContent != null) {
            codeArea.replaceText(0, 0, codeAreaContent);
        }

        highlightHtmlSyntax(codeArea.getText());
        codeArea.getChildrenUnmodifiable().add(textFlow);
//        codeArea.getChildrenUnmodifiable().add(new TextFlow());

//        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
//        codeArea.textProperty().addListener((observable, oldValue, newValue) -> codeArea.setStyleSpans(0, computeHighlighting(newValue)));



        VirtualizedScrollPane<CodeArea> vsPane = new VirtualizedScrollPane<>(codeArea);
        StackPane stackPane = new StackPane(vsPane);
        this.setContent(stackPane);

        codeArea.setWrapText(true);

        IntFunction<Node> lineNumberFactory = LineNumberFactory.get(codeArea);
        codeArea.setParagraphGraphicFactory(line -> {
            Node lineNumber = lineNumberFactory.apply(line);
            lineNumber.getStyleClass().add("line-number");
            return lineNumber;
        });

        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
        codeArea.textProperty().addListener((observable, oldValue, newValue) -> codeArea.setStyleSpans(0, computeHighlighting(newValue)));


    }

    public void highlightHtmlSyntax(String htmlCode) {
        textFlow.getChildren().clear();
        Matcher matcher = HTML_TAG.matcher(htmlCode);
        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                textFlow.getChildren().add(new Text(htmlCode.substring(lastEnd, matcher.start())));
            }

            if (matcher.group("ELEMENT") != null) {
                textFlow.getChildren().add(createStyledText(matcher.group("ELEMENT"), Color.BLUE, Font.font("monospace", FontWeight.BOLD, 12)));
            } else if (matcher.group("CONTENT") != null) {
                textFlow.getChildren().add(new Text(matcher.group("CONTENT")));
            }

            lastEnd = matcher.end();
        }

        if (lastEnd < htmlCode.length()) {
            textFlow.getChildren().add(new Text(htmlCode.substring(lastEnd)));
        }
    }

    private Text createStyledText(String text, Color color, Font font) {
        Text styledText = new Text(text);
        styledText.setFill(color);
        styledText.setFont(font);
        return styledText;
    }

//    public void highlightSyntax() {
//        codeArea.getChildren().clear();
//        String text = codeArea.getText();
//        if (text != null && !text.isEmpty()) {
//            Matcher matcher = HTML_TAG.matcher(text);
//            int lastEnd = 0;
//            while (matcher.find()) {
//                if (matcher.start() > lastEnd) {
//                    codeArea.getChildren().add(new Text(text.substring(lastEnd, matcher.start())));
//                }
//
//                if (matcher.group("ELEMENT") != null) {
//                    codeArea.getChildren().add(createStyledText(matcher.group("ELEMENT"), Color.BLUE, Font.font("monospace", FontWeight.BOLD, 12)));
//                } else if (matcher.group("CONTENT") != null) {
//                    codeArea.getChildren().add(new Text(matcher.group("CONTENT")));
//                }
//
//                lastEnd = matcher.end();
//            }
//
//            if (lastEnd < text.length()) {
//                codeArea.getChildren().add(new Text(text.substring(lastEnd)));
//            }
//        }
//    }

//    private Text createStyledText(String text, Color color, Font font) {
//        Text styledText = new Text(text);
//        styledText.setFill(color);
//        styledText.setFont(font);
//        return styledText;
//    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        Matcher matcher;

//        switch (language) {
//            case HTML:
                matcher = HTML_TAG.matcher(text);
                int lastEnd = 0;
                while (matcher.find()) {
                    if (matcher.group("ELEMENT") != null) {
                        spansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);
                        spansBuilder.add(Collections.singleton("element"), matcher.end() - matcher.start());
                    } else {
                        spansBuilder.add(Collections.emptyList(), matcher.end() - matcher.start());
                    }
                    lastEnd = matcher.end();
                }
                spansBuilder.add(Collections.emptyList(), text.length() - lastEnd);
//                break;
//            case CSS:
                matcher = CSS_RULE.matcher(text);
                lastEnd = 0;
                while (matcher.find()) {
                    if (matcher.group("SELECTOR") != null) {
                        spansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);
                        spansBuilder.add(Collections.singleton("selector"), matcher.end() - matcher.start());
                    } else if (matcher.group("PROPERTY") != null) {
                        spansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);
                        spansBuilder.add(Collections.singleton("property"), matcher.end() - matcher.start());
                    } else if (matcher.group("VALUE") != null) {
                        spansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);
                        spansBuilder.add(Collections.singleton("value"), matcher.end() - matcher.start());
                    } else {
                        spansBuilder.add(Collections.emptyList(), matcher.end() - matcher.start());
                    }
                    lastEnd = matcher.end();
                }
                spansBuilder.add(Collections.emptyList(), text.length() - lastEnd);
//                break;
//            default:
                // Обработка других языков или отсутствие подсветки синтаксиса
//                break;
//        }

        return spansBuilder.create();
    }
//
//    private static TextFlow createTextCodec(Color color, Font font) {
//        TextFlow textFlow = new TextFlow();
//        textFlow.setStyle(String.format("-fx-fill: %s; -fx-font-family: '%s'; -fx-font-weight: %s; -fx-font-size: %s;",
//                colorToHex(color), font.getFamily(), font.getSize(), font.getSize()));
//        return textFlow;
//    }
//
//    private static String colorToHex(Color color) {
//        return String.format("#%02X%02X%02X",
//                (int) (color.getRed() * 255),
//                (int) (color.getGreen() * 255),
//                (int) (color.getBlue() * 255));
//    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }
}
