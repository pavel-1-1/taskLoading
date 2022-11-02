package inputOutputStreams.taskLoading;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress doctor = new GameProgress(100, 5, 23, 10);
        GameProgress swin = new GameProgress(100, 3, 17, 25);
        GameProgress bob = new GameProgress(100, 4, 20, 20);

        saveGame("C:\\D\\Games\\savegames\\doctor.dat", doctor);
        saveGame("C:\\D\\Games\\savegames\\swin.dat", swin);
        saveGame("C:\\D\\Games\\savegames\\bob.dat", bob);

        zipFiles("C:\\D\\Games\\savegames\\doctorZip.zip", "C:\\D\\Games\\savegames\\doctor.dat");
        zipFiles("C:\\D\\Games\\savegames\\swinZip.zip", "C:\\D\\Games\\savegames\\swin.dat");
        zipFiles("C:\\D\\Games\\savegames\\bobZip.zip", "C:\\D\\Games\\savegames\\bob.dat");

        File dir = new File("C:\\D\\Games\\savegames");
        File[] or = dir.listFiles((dir1, name) -> name.endsWith(".dat"));
        assert or != null;
        for (File item : or) {
            item.delete();
        }

    }

    static void saveGame(String way, GameProgress obg) {
        try (FileOutputStream out = new FileOutputStream(way); ObjectOutputStream object = new ObjectOutputStream(out)) {
            object.writeObject(obg);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void zipFiles(String wayZip, String wayDat) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(wayZip));
             FileInputStream fileIn = new FileInputStream(wayDat)) {
            Path path = Paths.get(wayDat);
            String fileName = String.valueOf(path.getFileName());
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] buffer = new byte[fileIn.available()];
            fileIn.read(buffer);
            zipOut.write(buffer);
            zipOut.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
