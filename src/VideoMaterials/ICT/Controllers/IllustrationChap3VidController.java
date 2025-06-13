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
public class IllustrationChap3VidController implements Initializable {
    
    @FXML
    private JFXButton add10Button, minus10Button, pauseButton, playButton, resetButton;

    @FXML
    private MediaView mediaView;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        file = new File("Videos/Learning to Draw Digitally for Beginners.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    @FXML
    public void playMedia() {
        mediaPlayer.play();
    }

    @FXML
    public void pauseMedia() {
        mediaPlayer.pause();
    }

    @FXML
    public void resetMedia() {
        mediaPlayer.seek(mediaPlayer.getStartTime());
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
