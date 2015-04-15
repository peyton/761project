package ruil.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
public class ObjectSerializer {
 
    public static void Write(String filePathName, Object obj) {
        try {
            FileOutputStream outStream = new FileOutputStream(filePathName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outStream);
 
            objectOutputStream.writeObject(obj);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static Object Read(String filePathName) {
        FileInputStream freader = null;
        Object obj = null;
        try {
            freader = new FileInputStream(filePathName);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
 
            try {
                obj = objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            objectInputStream.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}