package game.tools;

import java.io.*;

public class FileLoader {


    public static String loadTxtFile(final String file) {
        String content = "";

        BufferedReader bufferedReader = null;

        try{

            bufferedReader = new BufferedReader(new FileReader(file));

            String line;

            while((line = bufferedReader.readLine()) != null){
                content += line;
            }

        } catch(IOException e){
            System.out.println("ERROR: "+file+" No se pudo cargar el archivo");
            e.printStackTrace();
        }


        return content;
    }


}
