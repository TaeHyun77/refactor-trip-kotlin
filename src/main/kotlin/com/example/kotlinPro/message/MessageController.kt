package com.example.kotlinPro.message

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/message")
@RestController
class MessageController(
    private val messageService: MessageService
) {

    @PostMapping("/send")
    fun sendMessage(@RequestBody messageReqDto: MessageReqDto) {

        return messageService.sendMessage(messageReqDto)
    }

    @GetMapping("/list")
    fun messageList(): List<MessageResDto> {

        return messageService.messageList();
    }

}