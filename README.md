# Circuit Breaker

# Notes on the circuit breaker

---

1. When should the circuit trip?
- Last `n` request to consider for this decision
- How many of those should fail
- Timeout duration

2. When does the circuit comes back online again?

# Example

---

1. Last n request to consider for the decision: 5
2. How many of this should fail: 2
3. Timeout duration: 3s
4. How long to wait [sleeping window]: 10s

# Gotchas

---

Tuning these numbers are tricky. Things to consider:

- `req/sec`
- Number of thread pool of the server
- tune based on trial and error and stat

# Problem with Hystrix Proxy

---

![Circuit%20Breaker%20342ebc323df04cd49bc57f0a0875afa4/Hystrix-proxy.png](Circuit%20Breaker%20342ebc323df04cd49bc57f0a0875afa4/Hystrix-proxy.png)

Hystrix Proxy class

We use `@CircuitBreaker` command in class level. In the root of Spring Boot class. Then inside a `@Service` class, we annotate a method with `@HystrixCommand` and mention a fallback method.

Problem with this is everything is happening inside our `API class` . But when we annotate the class, every time we refer the instance of the class, we actually refer the `Hystrix Proxy class` . 

So, when the circuit trips, we want the `Hystrix Proxy`  class to intercept the call and fall back to the default method. But it does not happen. Because everything is happening inside our API class. 

That's why we need to use the `@HystrixCommand` in a separate class and use that class's instance. That way we get the `Hystrix wrapper instance.` 

# Code that does not work

---

```java
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentServiceAPI paymentServiceAPI;

    @Override
    public OrderCheckOutResponse checkoutOrder(OrderCheckOutRequest orderCheckOutRequest) {

        log.info("Dumping into db {}", orderCheckOutRequest);
        
        PaymentAPIResponse paymentAPIResponse = makePayment(orderCheckOutRequest);
        
        var orderCheckOutResponse = OrderCheckOutResponse.builder();

        if (paymentAPIResponse.getCode() == 333) {
            orderCheckOutResponse
                    .code(paymentAPIResponse.getCode())
                    .message(paymentAPIResponse.getMessage());
        } else {
            orderCheckOutResponse
                    .code(555)
                    .message("Payment failed");
        }
        return orderCheckOutResponse.build();
    }

		@HystrixCommand(fallbackMethod = "defaultMakePayment")
		private makePayment(OrderCheckOutRequest orderCheckOutRequest) {
		
				return paymentServiceAPI.makePayment(orderCheckOutRequest)
		}

		public PaymentAPIResponse defaultMakePayment(OrderCheckOutRequest orderCheckOutRequest) {

        log.info("Hystrix Fall back payment method called");

        return PaymentAPIResponse.builder()
                .code(555)
                .message("Payment failed")
                .build();
    }
}
```

# Code that works

---

`[PaymentServiceImpl.java](http://paymentserviceimpl.java)` 

```java
@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentServiceAPI paymentServiceAPI;

    @Override
    @HystrixCommand(fallbackMethod = "defaultMakePayment")
    public PaymentAPIResponse makePayment(OrderCheckOutRequest orderCheckOutRequest) {

        return paymentServiceAPI.makePayment(getPaymentAPIRequest(orderCheckOutRequest));
    }

    private PaymentAPIRequest getPaymentAPIRequest(OrderCheckOutRequest orderCheckOutRequest) {

        return PaymentAPIRequest.builder()
                .userID(orderCheckOutRequest.getUserID())
                .amount(orderCheckOutRequest.getAmount())
                .build();
    }

    public PaymentAPIResponse defaultMakePayment(OrderCheckOutRequest orderCheckOutRequest) {

        log.info("Hystrix Fall back payment method called");

        return PaymentAPIResponse.builder()
                .code(555)
                .message("Payment failed")
                .build();
    }
}
```

`[OrderServiceImpl.java](http://orderserviceimpl.java)` 

```java
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;

    @Override
    public OrderCheckOutResponse checkoutOrder(OrderCheckOutRequest orderCheckOutRequest) {

        
        log.info("Dumping into db {}", orderCheckOutRequest);
        
        PaymentAPIResponse paymentAPIResponse = paymentService.makePayment(orderCheckOutRequest);
        
        var orderCheckOutResponse = OrderCheckOutResponse.builder();

        if (paymentAPIResponse.getCode() == 333) {
            orderCheckOutResponse
                    .code(paymentAPIResponse.getCode())
                    .message(paymentAPIResponse.getMessage());
        } else {
            orderCheckOutResponse
                    .code(555)
                    .message("Payment failed");
        }
        
        return orderCheckOutResponse.build();
    }
}
```

# Reference

---

[Spring Boot Microservices Level 2: Fault Tolerance and Resilience](https://www.youtube.com/playlist?list=PLqq-6Pq4lTTbXZY_elyGv7IkKrfkSrX5e)