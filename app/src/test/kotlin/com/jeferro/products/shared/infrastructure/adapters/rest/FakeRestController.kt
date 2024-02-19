package com.jeferro.products.shared.infrastructure.adapters.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class FakeRestController {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/fake"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun fake(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}