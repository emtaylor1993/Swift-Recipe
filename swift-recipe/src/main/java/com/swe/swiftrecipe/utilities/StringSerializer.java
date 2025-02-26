/**
 * SWIFTRECIPE STRING SERIALIZER CLASS
 * 
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class provides functionality to serialize and deserialized string,
 *    allowing recipe instructions to be stored and retrieved as byte arrays.
 *    It leverages Java's built-in serialization mechanism.
 * 
 * @packages
 *    Java IO (ByteArrayInputStream, ByteArrayOutputStream, IOException, ObjectInputStream, ObjectOutputStream)
 *    Spring Framework Stereotype (Service)
 *    Lombok (AllArgsConstructor)
 */

package com.swe.swiftrecipe.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

/**
 * Lombok annotations to reduce Java boilerplate code for getters, setters, and
 * constructors. Registers the class as an Service Bean to signify the presence
 * of business logic.
 */
@AllArgsConstructor
@Service
public class StringSerializer {

    /**
     * This method converts a String into a binary format using Java's
     * {@link OutputObjectStream} and {@link ByteArrayOutputStream}, allowining
     * it to be stored or transmitted efficiently.
     * 
     * @param string - The string to be serialized.
     * @return byte[] - A byte array representing the serialized string.
     * @throws IOException - Throws if an I/O error occurs during serialization.
     */
    public byte[] serializeString(String string) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(string);
        }
        return bos.toByteArray();
    }

    /**
     * This method reads the binary data from a byte array and reconstructs
     * the original string using Java's {@link ObjectInputStream}.
     * 
     * @param bytes - The byte array containing the serialized string data.
     * @return String - The deserialized String.
     * @throws IOException - Throws if an I/O error occurs during deserialization.
     * @throws ClassNotFoundException - Throws if the class definition for the 
     *      serialized object is not found.
     */
    public String deserializeString(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        String string;
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            string = (String) ois.readObject();
        }
        return string;
    }
}