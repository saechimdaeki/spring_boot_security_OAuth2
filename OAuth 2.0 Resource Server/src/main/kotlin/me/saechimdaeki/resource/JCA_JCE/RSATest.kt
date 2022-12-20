package me.saechimdaeki.resource.JCA_JCE

import me.saechimdaeki.resource.JCA_JCE.RSAGen.decrypt
import me.saechimdaeki.resource.JCA_JCE.RSAGen.encrypt
import me.saechimdaeki.resource.JCA_JCE.RSAGen.genKeyPair
import me.saechimdaeki.resource.JCA_JCE.RSAGen.getPrivateKeyFromKeySpec
import me.saechimdaeki.resource.JCA_JCE.RSAGen.getPublicKeyFromKeySpec
import java.util.*


object RSATest {
    fun rsa(message: String) {
        val keyPair = genKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private
        val encrypted = encrypt(message, publicKey)
        val decrypted = decrypt(encrypted, privateKey)
        println("message : $message")
        println("decrypted : $decrypted")

        // 키 스펙 전환하기
        val bytePublicKey = publicKey.encoded
        val base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey)
        val bytePrivateKey = privateKey.encoded
        val base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey)


        // X.509 표준형식
        val X509PublicKey = getPublicKeyFromKeySpec(base64PublicKey)
        val encrypted2 = encrypt(message, X509PublicKey)
        val decrypted2 = decrypt(encrypted2, privateKey)
        println("message : $message")
        println("decrypted2 : $decrypted2")


        // PKCS8 표준형식
        val PKCS8PrivateKey = getPrivateKeyFromKeySpec(base64PrivateKey)
        val decrypted3 = decrypt(encrypted2, PKCS8PrivateKey)
        println("message : $message")
        println("decrypted3 : $decrypted3")
    }
}