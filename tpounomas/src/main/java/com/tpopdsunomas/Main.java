package com.tpopdsunomas;

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
            Localizador.findSportsFields(coords[0], coords[1]);
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