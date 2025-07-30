package io.github.sseregit.realtimelab.longpolling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LongPollingController {

    @GetMapping("/longpolling")
    ResponseEntity<String> longPolling() {
        return ResponseEntity.ok("LongPolling");
    }
}
