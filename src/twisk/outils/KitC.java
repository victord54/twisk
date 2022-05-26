/**
 * Classe représentant les outils destinés à écrire le code c.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.outils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {
    /**
     * Constructeur par défaut de la classe.
     */
    public KitC() {
    }

    /**
     * Méthode créant le répertoire twisk dans /tmp et qui copie les fichiers objects et header nécessaires.
     */
    public void creerEnvironnement() {
        try {
            // Création du répertoire twisk sous /tmp. Ne déclenche pas d’erreur si le répertoire existe déjà
            Path directories = Files.createDirectories(Paths.get("/tmp/twisk"));
            // copie des deux fichiers programmeC.o et def.h depuis le projet sous /tmp/twisk
            String[] liste = {"programmeC.o", "def.h", "codeNatif.o"};
            for (String nom : liste) {
                InputStream source = getClass().getResource("/codeC/" + nom).openStream();
                File destination = new File("/tmp/twisk/" + nom);
                copier(source, destination);
                // Path source = Paths.get(getClass().getResource("/codeC/" + nom).getPath());
                // Path newdir = Paths.get("/tmp/twisk/");
                // Files.copy(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de copier-coller un fichier.
     *
     * @param source Répertoire source.
     * @param dest   Répertoire de destination.
     * @throws IOException Exception d'entrée-sortie.
     */
    private void copier(InputStream source, File dest) throws IOException {
        OutputStream destinationFile = new FileOutputStream(dest);
        // Lecture par segment de 0.5Mo
        byte[] buffer = new byte[512 * 1024];
        int nbLecture;
        while ((nbLecture = source.read(buffer)) != -1) {
            destinationFile.write(buffer, 0, nbLecture);
        }
        destinationFile.close();
        source.close();
    }

    /**
     * Méthode permettant de créer un fichier.
     *
     * @param codeC Nom du fichier à créer.
     */
    public void creerFichier(String codeC) {
        try {
            File chemin = new File("client.c");
            PrintWriter flotFiltre = new PrintWriter(chemin);
            flotFiltre.println("#include <stdlib.h>");
            flotFiltre.println("#include <sys/types.h>");
            flotFiltre.println("#include <unistd.h>");
            flotFiltre.println("#include <time.h>");
            flotFiltre.println("#include \"def.h\"\n");
            flotFiltre.println("void simulation(int ids) {");
            flotFiltre.print(codeC);
            flotFiltre.println("}");
            flotFiltre.close();

            Path newdir = Paths.get("/tmp/twisk/");
            Path source = Paths.get("client.c");
            Files.move(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de compiler du code c.
     */
    public void compiler() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("gcc -Wall -fPIC -c /tmp/twisk/client.c -o /tmp/twisk/client.o");
            // récupération des messages sur la sortie standard et la sortie d’erreur de la commande exécutée
            // à reprendre éventuellement et à adapter à votre code
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String ligne;
            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }
            while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de compiler et construire une librairie c.
     */
    public void construireLaLibrairie(int n) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/codeNatif.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk" + n +".so");
            // récupération des messages sur la sortie standard et la sortie d’erreur de la commande exécutée
            // à reprendre éventuellement et à adapter à votre code
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String ligne;
            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }
            while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
