module EBubblePack {
	// requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
}
