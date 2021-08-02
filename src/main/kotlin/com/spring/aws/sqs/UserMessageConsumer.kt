package com.spring.aws.sqs

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.spring.aws.commons.QueueNames
import com.spring.aws.entity.User
import com.spring.aws.exceptions.ProcessUserEventException
import com.spring.aws.exceptions.ReadUserEventException
import com.spring.aws.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import javax.validation.Valid

@Component
class UserMessageConsumer(val userService: UserService) {

    val logger: Logger = LoggerFactory.getLogger(UserMessageConsumer::class.java)

    @SqsListener(value = [QueueNames.userQueue], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun getMessageFromSqs(@Valid message: String?, @Header("MessageId") messageId: String?) {
        if (message != null) {
            userService.saveUser(parseUserMessage(message))
        } else {
            logger.error("USER_QUEUE_READ_ERROR : Failed to read message - $message")
            throw ReadUserEventException("Failed to read message fom USER_QUEUE")
        }
    }

    private fun parseUserMessage(message: String) : User{
        try {
            return jacksonObjectMapper().readValue<User>(message)
        } catch (e: Exception){
            logger.error("USER_QUEUE_PROCESS_ERROR : Failed to parse message - $message - StackTrace - ${e.message}")
            throw ProcessUserEventException("Failed to parse message fom USER_QUEUE")
        }
    }
}