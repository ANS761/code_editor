module com.example.cef {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
//    requires org.reactfx


    opens com.example.cef to javafx.fxml;
    exports com.example.cef;
}