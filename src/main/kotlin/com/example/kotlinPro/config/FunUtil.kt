package com.example.kotlinPro.config

import com.example.kotlinPro.tripException.ErrorCode
import com.example.kotlinPro.tripException.TripException
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.UUID

fun fileUploader(key: String, file: MultipartFile?): String {
    val (directoryPath, urlPrefix) = when (key) {
        "profileImage" -> System.getProperty("user.home") + "/profile-images" to "/profileImages"
        "postImage" -> System.getProperty("user.home") + "/post-images" to "/postImages"
        else -> throw TripException(HttpStatus.BAD_REQUEST, ErrorCode.FILE_SAVE_FAILED)
    }

    val directory = File(directoryPath)
    if (!directory.exists()) directory.mkdirs()

    val fileName = UUID.randomUUID().toString() + "_" + file?.originalFilename
    val saveFile = File(directory, fileName)

    try {
        file?.transferTo(saveFile)
    } catch (e: TripException) {
        throw TripException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.FILE_SAVE_FAILED)
    }

    return "$urlPrefix/$fileName"
}