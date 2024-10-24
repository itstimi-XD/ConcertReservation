package io.hhplus.concertreservationservice.config.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class LoggingFilter : Filter {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        val startTime = System.currentTimeMillis()
        chain.doFilter(request, response)
        val duration = System.currentTimeMillis() - startTime

        log.info("Request: {} {} from {}, Response: {}, Duration: {} ms",
            req.method, req.requestURI, req.remoteAddr, res.status, duration)
    }

    override fun init(filterConfig: FilterConfig?) {}
    override fun destroy() {}
}
