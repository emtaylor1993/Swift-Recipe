/**
 * SWIFTRECIPE STRING SERIALIZER CLASS
 * 
 * @author Sade Jn Baptiste
 * @author Lakshmi Kotikalapudi
 * @author Andy Nguyen
 * @author Shivani Samarla
 * @author Emmanuel Taylor
 * 
 * @description
 *    This class contains functionality to serialize/deserialize strings
 *    for the recipe instructions.
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

@AllArgsConstructor
@Service
public class StringSerializer {

    /**
     * Serializes a String to an array of bytes
     * 
     * @param string - String to be serialized
     * @return byte[] - The serialized byte array
     * @throws IOException - For IO exceptions
     */
    public byte[] serializeString(String string) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(string);
        oos.close();
        return bos.toByteArray();
    }

    /**
     * Deserializes the byte array to a String
     * 
     * @param bytes - Byte array to be deserialized
     * @return String - The deserialized String
     * @throws IOException - For IO exceptions
     * @throws ClassNotFoundException - For class not found exceptions
     */
    public String deserializeString(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        String string = (String) ois.readObject();
        ois.close();
        return string;
    }
}