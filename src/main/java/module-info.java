module com.example.githubtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.IntegrativeProject to javafx.fxml;
    exports com.example.IntegrativeProject;
}