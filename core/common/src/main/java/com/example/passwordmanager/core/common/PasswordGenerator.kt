package com.example.passwordmanager.core.common

import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

data class PasswordGeneratorConfig(
    val length: Int = 16,
    val includeUppercase: Boolean = true,
    val includeLowercase: Boolean = true,
    val includeDigits: Boolean = true,
    val includeSymbols: Boolean = true
)

@Singleton
class PasswordGenerator @Inject constructor() {

    private val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lowercase = "abcdefghijklmnopqrstuvwxyz"
    private val digits = "0123456789"
    private val symbols = "!@#\$%^&*()-_=+[]{}|;:,.<>?"

    fun generate(config: PasswordGeneratorConfig = PasswordGeneratorConfig()): String {
        val charPool = buildString {
            if (config.includeUppercase) append(uppercase)
            if (config.includeLowercase) append(lowercase)
            if (config.includeDigits) append(digits)
            if (config.includeSymbols) append(symbols)
        }

        if (charPool.isEmpty()) return ""

        val random = SecureRandom()
        val password = StringBuilder(config.length)

        // Ensure at least one character from each selected pool
        val requiredChars = mutableListOf<Char>()
        if (config.includeUppercase) requiredChars.add(uppercase[random.nextInt(uppercase.length)])
        if (config.includeLowercase) requiredChars.add(lowercase[random.nextInt(lowercase.length)])
        if (config.includeDigits) requiredChars.add(digits[random.nextInt(digits.length)])
        if (config.includeSymbols) requiredChars.add(symbols[random.nextInt(symbols.length)])

        // Fill remaining length
        val remainingLength = config.length - requiredChars.size
        for (i in 0 until remainingLength) {
            password.append(charPool[random.nextInt(charPool.length)])
        }

        // Insert required characters at random positions
        for (c in requiredChars) {
            val pos = random.nextInt(password.length + 1)
            password.insert(pos, c)
        }

        return password.toString()
    }
}
