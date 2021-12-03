module myJavaFX {
	exports miniProject;
	
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires java.sql;
	requires java.base;
	requires itext;
	requires javaSDK;
	requires json.simple;

    opens miniProject to javafx.fxml,java.sql;
}