package site.sayaz.ts3client.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Base64

fun base64Sha1(password: String): String {
    return try {
        val mDigest = MessageDigest.getInstance("SHA1")
        val result = mDigest.digest(password.toByteArray())
        Base64.getEncoder().encodeToString(result)
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }
}