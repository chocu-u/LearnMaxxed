package VideoMaterials.ICT.Controllers;

import java.util.ResourceBundle;
import java.net.URL;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.MediaView;

public class WebDevChap1VidController implements Initializable {

    @FXML
    private JFXButton add10Button, minus10Button, pauseButton, playButton, resetButton;

    @FXML
    private MediaView mediaView;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        file = new File("Videos/How Web Servers Work _.mp4");
        if (file.exists()) {
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }

    @FXML
    public void playMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    public void pauseMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    public void resetMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getStartTime());
        }
    }

    @FXML
    public void add10Seconds() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(10)));
        }
    }

    @FXML
    public void minus10Seconds() {
        if (mediaPlayer != null) {
            javafx.util.Duration newTime = mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(10));
            if (newTime.lessThan(javafx.util.Duration.ZERO)) {
                newTime = javafx.util.Duration.ZERO;
            }
            mediaPlayer.seek(newTime);
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
