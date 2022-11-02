package inputOutputStreams.taskLoading;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress doctor = new GameProgress(100, 5, 23, 10);
        GameProgress swin = new GameProgress(100, 3, 17, 25);
        GameProgress bob = new GameProgress(100, 4, 20, 20);
        List<String> dat = new ArrayList<>();
        dat.add(saveGame("C:\\D\\Games\\savegames\\doctor.dat", doctor));
        dat.add(saveGame("C:\\D\\Games\\savegames\\swin.dat", swin));
        dat.add(saveGame("C:\\D\\Games\\savegames\\bob.dat", bob));

        String way = "C:\\D\\Games\\savegames\\saveZip.zip";

        zipFiles(way, dat);

        File dir = new File("C:\\D\\Games\\savegames");
        File[] or = dir.listFiles((dir1, name) -> name.endsWith(".dat"));
        assert or != null;
        for (File item : or) {
            item.delete();
        }

    }

    static String saveGame(String way, GameProgress obg) {
        try (FileOutputStream out = new FileOutputStream(way); ObjectOutputStream object = new ObjectOutputStream(out)) {
            object.writeObject(obg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return way;
    }

    static void zipFiles(String wayZip, List<String> listDat) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(wayZip))) {
            for (String list : listDat) {
                FileInputStream fileIn = new FileInputStream(list);
                Path path = Paths.get(list);
                String fileName = String.valueOf(path.getFileName());
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);
                byte[] buffer = new byte[fileIn.available()];
                fileIn.read(buffer);
                zipOut.write(buffer);
                zipOut.closeEntry();
                fileIn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
