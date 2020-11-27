package com.artur.myapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories("com.artur.myapp.repository")
class MyAppApplication

fun main(args: Array<String>) {
	runApplication<MyAppApplication>(*args)
}
