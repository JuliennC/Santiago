package serialisationXML;

import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class XMLTools {

    private XMLTools() {}
	
    /**
     * Serialisation d'un objet dans un fichier
     * @param object objet a serialiser
     * @param filename chemin du fichier
     */
    public static void encodeToFile(Object object, String fileName) throws FileNotFoundException, IOException {
        // ouverture de l'encodeur vers le fichier
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileName));
        try {
            // serialisation de l'objet
            encoder.writeObject(object);
            encoder.flush();
        } finally {
            // fermeture de l'encodeur
            encoder.close();
        }
    }
    
    /**
     * Sérialisation d'un objet sans constructeur vide dans un fichier
     * @param object
     * @param fileName
     * @param persistenceDelegate
     * @throws IOException
     */
    public static void encodeToFile(Object object, String fileName, PersistenceDelegate persistenceDelegate) throws IOException {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));

        // association du PersistenceDelegate à la classe de l'objet.
        encoder.setPersistenceDelegate(object.getClass(), persistenceDelegate);

        try {
            encoder.writeObject(object);
            encoder.flush();
        } finally {
            encoder.close();
        }
    }
    
    /**
     * Deserialisation d'un objet depuis un fichier
     * @param filename chemin du fichier
     */
    public static Object decodeFromFile(String fileName) throws FileNotFoundException, IOException {
        Object object = null;
        // ouverture de decodeur
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName));
        try {
            // deserialisation de l'objet
            object = decoder.readObject();
        } finally {
            // fermeture du decodeur
            decoder.close();
        }
        return object;
    }
}