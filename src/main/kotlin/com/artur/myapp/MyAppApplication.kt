package com.artur.myapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class MyAppApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<MyAppApplication>(*args)
}
