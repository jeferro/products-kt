package com.jeferro.products.components.products.mongo

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.MountableFile

object MongodbContainerCreator {

    fun create(): MongoDBContainer {
        return MongoDBContainer("mongo:6.0.7")
    }

}