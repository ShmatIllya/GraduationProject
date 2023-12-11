//module com.project.client {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires com.dlsc.formsfx;
//    requires DataLib;
//    requires java.sql;
//
//    opens com.project.client to javafx.graphics;
//}
module practise {
   requires javafx.controls;
   requires javafx.fxml;

   requires com.dlsc.formsfx;
    requires DataLib;
   requires java.sql;
    requires mysql.connector.java;

    opens practise to javafx.graphics, javafx.fxml;
   opens practise.controllers to javafx.fxml;
   opens practise.items to javafx.base;
}
