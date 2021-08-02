package com.spring.aws.service

import com.spring.aws.entity.User
import org.springframework.stereotype.Service

@Service
class UserService {
    fun saveUser(user: User){
        println("Mensagem read on service")
        println("\n")
        println("User Name: ${user.name}")
        println("User Age: ${user.age}")
        println("User Gender: ${user.gender}")
    }
}