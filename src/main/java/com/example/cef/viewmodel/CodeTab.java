package com.example.cef.viewmodel;

import com.example.cef.view.EditorPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.function.IntFunction;

public class CodeTab extends Tab {

    private CodeArea codeArea;
    public CodeTab(String s, String codeAreaContent) {
        if (codeAreaContent != null) {
            this.codeArea = new CodeArea(codeAreaContent);
        } else {
            this.codeArea = new CodeArea();
        }

        VirtualizedScrollPane<CodeArea> vsPane = new VirtualizedScrollPane<>(codeArea);
        StackPane stackPane = new StackPane(vsPane);
        this.setContent(stackPane);
        this.setText(s);

        codeArea.setWrapText(true);

        // Создание нумерации строк
        IntFunction<Node> lineNumberFactory = LineNumberFactory.get(codeArea);

        codeArea.setParagraphGraphicFactory(line -> {
            Node lineNumber = lineNumberFactory.apply(line);
            lineNumber.getStyleClass().add("line-number");
            return lineNumber;
        });
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }
}
