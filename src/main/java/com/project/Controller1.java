package com.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Controller1 implements initialize {

    @FXML
    private Button button0, button1,stop;
    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10
    ,img11,img12,img13,img14,img15,img16,img17,img18,img19,img20,img21,img22,img23,img24;
    @FXML
    private Label progreso;
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private AnchorPane container;
    
    

    private List<String> imageUrls = List.of(
        "/assets/image1.png", "/assets/image2.png", "/assets/image3.png",
        "/assets/image4.png", "/assets/image5.png", "/assets/image6.png",
        "/assets/image7.png", "/assets/image8.png", "/assets/image9.png",
        "/assets/image10.png", "/assets/image11.png", "/assets/image12.png",
        "/assets/image13.png", "/assets/image14.png", "/assets/image15.png",
        "/assets/image16.png", "/assets/image17.png", "/assets/image18.png",
        "/assets/image19.png", "/assets/image20.png", "/assets/image21.png",
        "/assets/image22.png", "/assets/image23.png", "/assets/image24.png");    
    private int currentImageIndex = 0;
    private int contadorProgreso = 0;
    private volatile boolean stopThread = false;
    private volatile boolean stopProgressUpdate = false;
 

    @FXML
    private void animateToView0(ActionEvent event) {
        UtilsViews.setViewAnimating("View0");
    }
    @FXML
    private void stopExecutor(ActionEvent event) {
        stopThread = true;
        stopProgressUpdate = true;
        clearImageViews();
        
    }

    @FXML
    private void loadImage() {
    System.out.println("Loading images...");

    img1.setImage(null);
    loadNextImage();
}

    private void loadNextImage() {
        if (currentImageIndex < imageUrls.size()) {
            String imageUrl = imageUrls.get(currentImageIndex);
            ImageView targetImageView = getImageViewById(currentImageIndex);
            
    
            loadImageBackground(imageUrl, (image) -> {
                System.out.println("Image loaded");
                targetImageView.setImage(image);
                
                contadorProgreso++;
                
                System.out.println(contadorProgreso);
                if (!stopProgressUpdate) {
                    Platform.runLater(() -> {
                        progreso.setText(String.valueOf(contadorProgreso) + " de 24:");
                        progressBar.setProgress(contadorProgreso / 24.0);
                    });
                }
                currentImageIndex++;
                loadNextImage();
                
            });
        }
    }
    
    private ImageView getImageViewById(int index) {
        return switch (index) {
            case 0 -> img1;
            case 1 -> img2;
            case 2 -> img3;
            case 3 -> img4;
            case 4 -> img5;
            case 5 -> img6;
            case 6 -> img7;
            case 7 -> img8;
            case 8 -> img9;
            case 9 -> img10;
            case 10 -> img11;
            case 11 -> img12;
            case 12 -> img13;
            case 13 -> img14;
            case 14 -> img15;
            case 15 -> img16;
            case 16 -> img17;
            case 17 -> img18;
            case 18 -> img19;
            case 19 -> img20;
            case 20 -> img21;
            case 21 -> img22;
            case 22 -> img23;
            case 23 -> img24;
            default -> null;
        };
    }


    public void loadImageBackground(String imageUrl, Consumer<Image> callBack) {
        // Use a thread to avoid blocking the UI
        CompletableFuture<Image> futureImage = CompletableFuture.supplyAsync(() -> {
            if (stopThread) {
                return null; // Salir temprano si se debe detener el hilo
            }
            try {
                // Simulate a loading time
                Random random = new Random();
                int numeroAleatorio = random.nextInt(46) + 5;
                numeroAleatorio = numeroAleatorio*1000;
                System.out.println(numeroAleatorio);
                Thread.sleep(numeroAleatorio);

                // Load the data from the assets file
                Image image = new Image(getClass().getResource(imageUrl).toString());
                return image;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        })
        .exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        futureImage.thenAcceptAsync(result -> {
        // Verificar nuevamente si se debe detener antes de mostrar la imagen
        if (!stopThread) {
            callBack.accept(result);
        }
    }, Platform::runLater);
    }
    private void clearImageViews() {
        ImageView[] imageViews = {img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12, img13, img14, img15, img16, img17, img18, img19, img20, img21, img22, img23, img24};
        for (ImageView imageView : imageViews) {
            imageView.setImage(null);
        }
    }
}
