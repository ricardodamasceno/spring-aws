package com.spring.aws.controller

import com.spring.aws.commons.RoutePaths
import com.spring.aws.s3.S3FileDownloaderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(RoutePaths.version + RoutePaths.downloadFile)
class FileDownloadController(val fileDownloader: S3FileDownloaderService) {

    @GetMapping
    fun downloadFile(@RequestHeader("filePath") filePath: String){
        fileDownloader.downloadFile(filePath)
    }

}