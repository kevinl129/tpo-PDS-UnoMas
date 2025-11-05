package com.tpopdsunomas;

import java.util.List;

import com.tpopdsunomas.model.EmailService;
import com.tpopdsunomas.model.Geolocation;
import com.tpopdsunomas.model.Localizador;

public class Main {
    public static void main(String[] args) {
       /*Test Geolocalizacion*/
   
       try {
            String address = "7167";
            double[] coords = Geolocation.geocodeAddress(address);
            System.out.printf("📍 Address: %s%nLat: %.6f, Lon: %.6f%n%n", address, coords[0], coords[1]);
             List<String> ubicaciones = Localizador.findSportsFields(coords[0], coords[1],2000);
            
                   for (String ubicacione : ubicaciones) {
                System.out.println(ubicacione);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

         /*Test Geolocalizacion*/

         /*Test Mail */
         /*
        String emailRemitente = "alvalenteuade@gmail.com";
        String password = "iijf blbr zrdk zgjp";
        EmailService emailService = new EmailService(emailRemitente, password);
        emailService.enviarCorreo("alejandromvalente@gmail.com", "test", "test");
 */
         /*Test Mail */

        
    }
}