package com.rent.carrent.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseControllerSpecification extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Value('http://localhost:${local.server.port}')
    String serviceUrl
}
