package com.example.cef.viewmodel;

import com.example.cef.model.Language;
import com.example.cef.view.EditorPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CodeTab extends Tab {

    private static final Pattern HTML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*>))|(?<CONTENT>\\\\?[^<>]+)");
    private static final Pattern CSS_RULE = Pattern.compile("(?<SELECTOR>\\w+\\s*\\{)|(?<PROPERTY>\\w+\\s*:)|(\\})|(?<VALUE>.+?;)");

    private CodeArea codeArea;
    public CodeTab(String title, String codeAreaContent, Language language) {
        if (codeAreaContent != null) {
            codeArea = new CodeArea();
            codeArea.replaceText(0, 0, codeAreaContent);
            setContent(codeArea);
        } else {
            codeArea = new CodeArea();
            setContent(codeArea);
        }

        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText(), language));

        VirtualizedScrollPane<CodeArea> vsPane = new VirtualizedScrollPane<>(codeArea);
        StackPane stackPane = new StackPane(vsPane);
        this.setContent(stackPane);
        this.setText(title);

        codeArea.setWrapText(true);

        // Создание нумерации строк
        IntFunction<Node> lineNumberFactory = LineNumberFactory.get(codeArea);

        codeArea.setParagraphGraphicFactory(line -> {
            Node lineNumber = lineNumberFactory.apply(line);
            lineNumber.getStyleClass().add("line-number");
            return lineNumber;
        });

        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText(), language));
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text, Language language) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        Matcher matcher;

        switch (language) {
            case HTML:
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
                break;
            case CSS:
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
                break;
            default:
                // Обработка других языков или отсутствие подсветки синтаксиса
                break;
        }

        return spansBuilder.create();
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }
}
