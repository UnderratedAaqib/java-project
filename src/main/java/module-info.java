module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.DLL;
    opens com.example.demo.DLL to javafx.fxml;
    exports com.example.demo.BLL;
    opens com.example.demo.BLL to javafx.fxml;
}