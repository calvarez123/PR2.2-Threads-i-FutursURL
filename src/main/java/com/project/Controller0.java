package com.project;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class Controller0 {
    Random random = new Random();
        

    @FXML
    private Button button0, boton1, boton2, boton3;
    @FXML
    private AnchorPane container;
    @FXML
    private Label percentatge0, percentatge1 , percentatge2;
    @FXML
    private ProgressBar progress0, progress1 , progress2;

    private int progresoactual1 ;
    private int progresoactual2 ;
    private int progresoactual3 ;
    
    private ExecutorService executor = Executors.newFixedThreadPool(3); // Creem una pool de dos fils
    private Future<?> tarea1 ,tarea2,tarea3;
    private boolean runningtarea1 = false;
    private boolean runningtarea2 = false;
    private boolean runningtarea3 = false;


    

    @FXML
    private void animateToView1(ActionEvent event) {
        UtilsViews.setViewAnimating("View1");
    }

    @FXML
    private void runtareas(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        //esto es para el boton 1
        if (sourceButton== boton1){
            if (runningtarea1 == true){
                tarea1.cancel(true);
                runningtarea1 = false;
                boton1.setText("iniciar");
            }else {
                tarea1 = backgroundTask(0, progresoactual1);
                runningtarea1 = true;
                boton1.setText("Stop");
            }
            
        }
        if (sourceButton== boton2){
            if (runningtarea2 == true){
                tarea2.cancel(true);
                runningtarea2 = false;
                boton2.setText("iniciar");
            }else {
                tarea2 = backgroundTask(1, progresoactual2);
                runningtarea2 = true;
                boton2.setText("Stop");
            }
            
        }
        if (sourceButton== boton3){
            if (runningtarea3 == true){
                tarea3.cancel(true);
                runningtarea3 = false;
                boton3.setText("iniciar");
            }else {
                tarea3 = backgroundTask(2, progresoactual3);
                runningtarea3 = true;
                boton3.setText("Stop");
            }
            
        }
        
    }


    private Future<?> backgroundTask(int index, int numprogreso) {
        // Executar la tasca
        final int finalvalor = numprogreso;
        
        return executor.submit(() -> {
            int valor = finalvalor;
            Random random = new Random();
            while (valor < 100) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                valor++;
                if (valor > 100) {
                    valor = 100;
                }
                final int currentValue;
                if (valor < 100){
                    currentValue = valor;
                    
                }else {
                    currentValue = 0;
                   
                }
                if (index == 0) {
                    // Actualitzar el Label en el fil d'aplicació de l'UI
                    
                    Platform.runLater(() -> {
                        progresoactual1=currentValue;
                        percentatge0.setText(String.valueOf(currentValue) + "%");                            
                        progress0.setProgress(currentValue/100.0);
                        progresoactual1=currentValue;
              
                        if (progresoactual1 == 99) {
                            progresoactual1 = 0;
                            
                            boton1.setText("Activar");
                        }
                        
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Vuelve a establecer la bandera de interrupción
                        Thread.currentThread().interrupt();
                    }

                }
                //linea 2
                if (index == 1) {
                    // Actualitzar el Label en el fil d'aplicació de l'UI
                    
                    int randomNumber = random.nextInt(3) + 2; 
                    int numeroAleatorio = (random.nextInt(3) + 3);
                    numeroAleatorio = numeroAleatorio*1000;
                    
                    int numero = valor+randomNumber;
                    
                    Platform.runLater(() -> {
                        progresoactual2=currentValue;
                        percentatge1.setText(String.valueOf(numero) + "%");
                        progress1.setProgress(numero/100.0);
                        if (progresoactual2 == 99){
                            stopExecutor();
                            boton2.setText("Activar");
                        }

                    });
                    try {
                        Thread.sleep(numeroAleatorio);
                    } catch (InterruptedException e) {
                        // Vuelve a establecer la bandera de interrupción
                        Thread.currentThread().interrupt();
                    }
                    
                }

                if (index == 2) {
                    int randomNumber = random.nextInt(3) + 4;
                    int numero = currentValue+randomNumber;
                    final int finalnumero = numero+randomNumber;
                    System.err.println("current value = "+currentValue);
                    System.out.println("aleatorio= "+randomNumber);
                    System.out.println("resultado =" +numero);

                    int numeroAleatorio = random.nextInt(6) * 1000 + 3000;
                    // Actualitzar el Label en el fil d'aplicació de l'UI
                    Platform.runLater(() -> {
                        
                        progresoactual3=currentValue;
                        
                        percentatge2.setText(String.valueOf(finalnumero) + "%");
                        progress2.setProgress(numero/100.0);
                        if (progresoactual3 == 99){
                            stopExecutor();
                            boton3.setText("Activar");
                        }
                    });
                    try {
                        Thread.sleep(numeroAleatorio);
                    } catch (InterruptedException e) {
                        // Vuelve a establecer la bandera de interrupción
                        Thread.currentThread().interrupt();
                    }
                    
                }

                //System.out.println("Updating label: " + index + ", Value: " + currentValue);
            }
        });
    }

    
    
    // Aquesta funció la cridaries quan vulguis tancar l'executor (per exemple, quan tanquis la teva aplicació)
    public void stopExecutor() {
        executor.shutdown();
    }

}