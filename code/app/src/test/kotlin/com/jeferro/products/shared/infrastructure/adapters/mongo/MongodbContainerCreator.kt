package com.jeferro.products.shared.infrastructure.adapters.mongo

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.MountableFile

object MongodbContainerCreator {

    fun create(): MongoDBContainer {
        return MongoDBContainer("mongo:6.0.7")
    }

}