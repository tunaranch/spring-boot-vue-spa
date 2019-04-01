package com.example.cafe.web

import io.kotlintest.specs.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class VueRoutePathFilterTest() : BehaviorSpec() {

    @Autowired
    lateinit var webClient: WebTestClient

    init {
        Given("Boot app") {
            Then("/ returns static index page") {
                webClient
                    .get().uri("/")
                    .exchange()
                    .expectStatus().isOk
                    .expectHeader().contentType(MediaType.TEXT_HTML)
            }
            And("Dummy /api path 404s") {
                webClient
                    .get().uri("/api/dummy")
                    .exchange()
                    .expectStatus().isNotFound
            }
            And("Actuator health check works") {
                webClient
                    .get().uri("/actuator")
                    .exchange()
                    .expectStatus().isOk
            }
            And("Dummy actuator path is 404") {
                webClient
                    .get().uri("/actuator/xxxxxxx")
                    .exchange()
                    .expectStatus().isNotFound
            }
            And("other paths fallback index page") {
                webClient
                    .get().uri("/dummy")
                    .exchange()
                    .expectStatus().isOk
                    .expectHeader().contentType(MediaType.TEXT_HTML)
            }
        }
    }
}

