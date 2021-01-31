package components;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsynchronousComponent {
    @Async
    public void displayNumbers() {
        // ...
    }

//    @Async
//    public Future<String> doSomething() {
//		return null;
//        // ...
//    }

    @Async
    public CompletableFuture<String> doSomething() {
		return null;
        // ...
    }
}