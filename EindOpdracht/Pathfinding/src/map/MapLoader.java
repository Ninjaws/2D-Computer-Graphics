package map;

import java.io.*;

public class MapLoader {

    public static void writeMapToObjFile(TileMap map, String filePath){

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static TileMap loadMapFromObjFile(String filePath){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TileMap map = null;
        try {
            map = (TileMap)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }


    public static void writeMapToObjFile(TileMap map, File file){

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static TileMap loadMapFromObjFile(File file){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TileMap map = null;
        try {
            map = (TileMap)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

}
