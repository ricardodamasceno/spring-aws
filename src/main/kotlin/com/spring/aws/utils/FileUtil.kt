package com.spring.aws.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object FileUtil {

    private val logger: Logger = LoggerFactory.getLogger(FileUtil::class.java)

    @JvmStatic
    fun streamToFile(inputStream: InputStream, filePath: String) {
        val newFile = File(filePath)
        newFile.parentFile.mkdirs()
        newFile.createNewFile()
        Files.copy(inputStream, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    @JvmStatic
    fun createTempFile(file: String) : File {
        return kotlin.runCatching {
            File(Files.createTempDirectory("FOLDER_").toFile(), File(file).name)
        }.onFailure {
            logger.error("Failed trying to create temp file")
            throw it
        }.getOrThrow()
    }

}