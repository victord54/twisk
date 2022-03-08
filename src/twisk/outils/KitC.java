package twisk.outils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {
    public KitC() {
    }

    public void creerEnvironnement(){
        try {
            // création du répertoire twisk sous /tmp. Ne déclenche pas d’erreur si le répertoire existe déjà
            Path directories = Files.createDirectories(Paths.get("/tmp/twisk"));
            // copie des deux fichiers programmeC.o et def.h depuis le projet sous /tmp/twisk
            String[] liste = {"programmeC.o", "def.h"};
            for (String nom : liste) {
                Path source = Paths.get(getClass().getResource("/codeC/" + nom).getPath());
                Path newdir = Paths.get("/tmp/twisk/");
                Files.copy(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void creerFichier(String codeC){
        try {
            File chemin = new File("client.c");
            PrintWriter flotFiltre = new PrintWriter(chemin);
            flotFiltre.println("#include \"def.h\"");
            flotFiltre.print(codeC);
            flotFiltre.close();

            Path newdir = Paths.get("/tmp/twisk/");
            Path source = Paths.get("client.c");
            Files.move(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
