package io.dodn.springboot.core.domain

import io.dodn.springboot.core.api.config.AsyncConfig
import io.dodn.springboot.core.api.config.AsyncConfigToBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder

class AsyncServiceTest {

    @Test
    fun gracefulShutdown_asIs() {
        val context = SpringApplicationBuilder(
            AsyncConfig::class.java, // 기존 AsyncConfig
            AsyncService::class.java
        )
            .web(WebApplicationType.NONE)
            .run()
        val asyncService = context.getBean(AsyncService::class.java)

        asyncService.longRunningTask()
        Thread.sleep(500)
        context.close()

        // 실패
        assertThat(asyncService.isTaskCompleted.get()).isTrue()
    }

    @Test
    fun gracefulShutdown_toBe() {
        val context = SpringApplicationBuilder(
            AsyncConfigToBe::class.java, // 변경될 AsyncConfig
            AsyncService::class.java
        )
            .web(WebApplicationType.NONE)
            .run()
        val asyncService = context.getBean(AsyncService::class.java)

        asyncService.longRunningTask()
        Thread.sleep(500)
        context.close()

        // 성공
        assertThat(asyncService.isTaskCompleted.get()).isTrue()
    }

}