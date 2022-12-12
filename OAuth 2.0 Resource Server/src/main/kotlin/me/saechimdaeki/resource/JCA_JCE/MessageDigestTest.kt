package me.saechimdaeki.resource.JCA_JCE

import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*


object MessageDigestTest {
    fun messageDigest(message: String) {
        createMD5(message)
        validateMD5(message)
    }

    private fun createMD5(message: String) {
        val random = SecureRandom()
        val salt = ByteArray(10)
        random.nextBytes(salt)
        val messageDigest = MessageDigest.getInstance("MD5")
        messageDigest.update(salt)
        messageDigest.update(message.toByteArray(charset("UTF-8")))
        val digest = messageDigest.digest()
        val fileOutputStream =
            FileOutputStream("/Users/a1101783/Desktop/개인공부/spring_boot_security_OAuth2/OAuth 2.0 Resource Server/src/main/resources/message.txt")
        fileOutputStream.write(salt)
        fileOutputStream.write(digest)
        fileOutputStream.close()
    }

    private fun validateMD5(message: String) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val fis = FileInputStream("/Users/a1101783/Desktop/개인공부/spring_boot_security_OAuth2/OAuth 2.0 Resource Server/src/main/resources/message.txt")
        var theByte = 0
        while (fis.read().also { theByte = it } != -1) byteArrayOutputStream.write(theByte)
        fis.close()
        val hashedMessage = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.reset()
        val salt = ByteArray(10)
        System.arraycopy(hashedMessage, 0, salt, 0, 10)
        val md = MessageDigest.getInstance("MD5")
        md.update(salt)
        md.update(message.toByteArray(charset("UTF-8")))
        val digest = md.digest()
        val digestInFile = ByteArray(hashedMessage.size - 10)
        System.arraycopy(hashedMessage, 10, digestInFile, 0, hashedMessage.size - 10)
        if (Arrays.equals(digest, digestInFile)) println("message matches.") else println("message does not matches.")
    }
}