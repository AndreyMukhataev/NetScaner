

import java.io.*;

import java.util.LinkedList;
import java.util.List;


public class FileHandler {
    public static final String FILENAME ="members.csv "; //наименование файла
    public static final String DELIMITER = ", ";  //Разделитель
    private static  final String TEMPFILENAME= "members.temp";  // Наименование временного файла

   /* //Добавление новой строки в файл CSV
    public void appendFile(String mem){
        try(BufferedWriter writer = new BufferedWriter (new FileWriter(FILENAME,true)))
        {
            writer.append(mem);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //Создание нового файла с данными
    public void newWriteFile(LinkedList<String> s ){
        //Удаление предыдущего файла
        File deletedFile = new File(FILENAME);
        deletedFile.delete();

        try(BufferedWriter writer = new BufferedWriter (new FileWriter(FILENAME,true))) {
            for (String elementList : s) {
                writer.write(elementList);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException exp){
            exp.printStackTrace();
        }


    }


}
