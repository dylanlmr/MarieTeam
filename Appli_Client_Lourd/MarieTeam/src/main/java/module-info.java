module fr.xkgd.marieteam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;
    requires itextpdf;
    requires io;
    requires java.desktop;
    requires javafx.swing;
    requires org.testng;
    requires org.controlsfx.controls;

    opens fr.xkgd.marieteam to javafx.fxml;

    exports fr.xkgd.marieteam;
    exports fr.xkgd.marieteam.database;
    exports fr.xkgd.marieteam.controller;
    opens fr.xkgd.marieteam.controller to javafx.fxml;
    exports fr.xkgd.marieteam.model;
    exports fr.xkgd.marieteam.customexception;
    opens fr.xkgd.marieteam.customexception to javafx.fxml;
}