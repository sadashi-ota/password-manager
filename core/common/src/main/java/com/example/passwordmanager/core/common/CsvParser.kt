package com.example.passwordmanager.core.common

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

data class CsvRow(
    val serviceName: String,
    val userName: String,
    val password: String,
    val note: String
)

sealed class CsvParseResult {
    data class Success(val rows: List<CsvRow>) : CsvParseResult()
    data class Error(val message: String) : CsvParseResult()
}

@Singleton
class CsvParser @Inject constructor() {

    private val expectedHeaders = listOf("サービス名", "ユーザー名", "パスワード", "メモ")

    fun parse(inputStream: InputStream): CsvParseResult {
        return try {
            val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))
            val lines = reader.readLines()
            reader.close()

            if (lines.isEmpty()) {
                return CsvParseResult.Error("CSVファイルが空です。")
            }

            // Validate header
            val headerLine = lines.first()
            val headers = parseCsvLine(headerLine)
            if (headers.size != expectedHeaders.size) {
                return CsvParseResult.Error(
                    "カラム数が正しくありません。期待: ${expectedHeaders.size}列, 実際: ${headers.size}列"
                )
            }
            for (i in expectedHeaders.indices) {
                if (headers[i].trim() != expectedHeaders[i]) {
                    return CsvParseResult.Error(
                        "ヘッダーが正しくありません。期待: ${expectedHeaders[i]}, 実際: ${headers[i].trim()}"
                    )
                }
            }

            // Parse data rows
            val rows = mutableListOf<CsvRow>()
            for (i in 1 until lines.size) {
                val line = lines[i]
                if (line.isBlank()) continue
                val columns = parseCsvLine(line)
                if (columns.size != 4) {
                    return CsvParseResult.Error(
                        "行 ${i + 1} のカラム数が正しくありません。期待: 4列, 実際: ${columns.size}列"
                    )
                }
                rows.add(
                    CsvRow(
                        serviceName = columns[0].trim(),
                        userName = columns[1].trim(),
                        password = columns[2].trim(),
                        note = columns[3].trim()
                    )
                )
            }
            CsvParseResult.Success(rows)
        } catch (e: Exception) {
            CsvParseResult.Error("CSVファイルの読み込みに失敗しました: ${e.message}")
        }
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val sb = StringBuilder()
        var inQuotes = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            when {
                c == '"' && !inQuotes -> inQuotes = true
                c == '"' && inQuotes -> {
                    if (i + 1 < line.length && line[i + 1] == '"') {
                        sb.append('"')
                        i++
                    } else {
                        inQuotes = false
                    }
                }
                c == ',' && !inQuotes -> {
                    result.add(sb.toString())
                    sb.clear()
                }
                else -> sb.append(c)
            }
            i++
        }
        result.add(sb.toString())
        return result
    }
}
