/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.htlstp.fotoherfert4school_admin.others;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Michael Moschinger
 * Klasse/Katalognummer: 5BHIF/12
 * DV-Nummer: 20110386
 */
public class HashingUtilities {
    
    public static String erzeugeSalt(int numBits, int radix){
        Random rd = new SecureRandom();
        return new BigInteger(numBits, rd).toString(radix);
    }
    
    public static String hashStringMitSalt(String str, String salt){
        return Hashing.sha256().hashString(str + salt, Charsets.UTF_8).toString();
    }
}
