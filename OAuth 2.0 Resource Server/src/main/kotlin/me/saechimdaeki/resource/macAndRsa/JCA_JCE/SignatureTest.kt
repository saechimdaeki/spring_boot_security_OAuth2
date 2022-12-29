package me.saechimdaeki.resource.macAndRsa.JCA_JCE

import java.security.KeyPairGenerator
import java.security.Signature
import java.security.SignatureException


object SignatureTest {
    fun signature(message: String) {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.genKeyPair()
        val data = message.toByteArray(charset("UTF-8"))
        val signature = Signature.getInstance("SHA256WithRSA")
        signature.initSign(keyPair.private)
        signature.update(data)
        val sign = signature.sign()
        signature.initVerify(keyPair.public)
        signature.update(data)
        var verified = false
        try {
            verified = signature.verify(sign)
        } catch (e: SignatureException) {
            println("전자서명 실행 중 오류발생")
            e.printStackTrace()
        }
        if (verified) println("전자서명 검증 성공") else println("전자서명 검증 실패")
    }
}