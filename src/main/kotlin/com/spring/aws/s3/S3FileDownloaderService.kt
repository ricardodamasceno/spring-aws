package com.spring.aws.s3

import com.spring.aws.utils.FileUtil
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.File


@Service
class S3FileDownloaderService (val resourceLoader: ResourceLoader){

    fun downloadFile(filePath: String) : File{
        val resource: Resource = resourceLoader.getResource(filePath)
        val file = FileUtil.createTempFile(filePath)
        FileUtil.streamToFile(resource.inputStream, file.path)
        return file
        // to delete the file
        //file.delete()
    }



}