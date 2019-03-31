package com.example.cafe.web

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class VueRoutePathFilter : WebFilter {

    companion object {
        val BOOT_PATHS = listOf(
            "/actuator/",
            "/api/",
            "/js/",
            "/css/",
            "/img/"
        )

        const val SPA_PATH = "/index.html"
    }

    override fun filter(exchange: ServerWebExchange,
                        chain: WebFilterChain): Mono<Void> {
        if (isApiPath(exchange.request.uri.path)) {
            return chain.filter(exchange)
        }

        return chain
            .filter(exchange
                .mutate()
                .request(exchange.request
                    .mutate().path(SPA_PATH)
                    .build())
                .build())
    }

    private fun isApiPath(path: String): Boolean {
        return BOOT_PATHS.any { path.startsWith(it) }
    }
}
