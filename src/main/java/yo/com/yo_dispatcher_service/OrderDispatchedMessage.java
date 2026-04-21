package yo.com.yo_dispatcher_service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderDispatchedMessage(
        Long orderId
){}


