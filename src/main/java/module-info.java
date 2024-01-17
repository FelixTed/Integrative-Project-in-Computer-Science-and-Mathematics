module com.example.githubtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.githubtest to javafx.fxml;
    exports com.example.githubtest;
}