package com.squiggy.restaurant_svc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squiggy.api_spec.DTO.OrderStatus;
import com.squiggy.api_spec.DTO.event.OrderInitiationEvent;
import com.squiggy.api_spec.DTO.event.OrderStatusUpdateEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPubSub {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();
    public EventPubSub(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "orders-channel", groupId = "restaurant-svc")
    public void consume(String jsonString) throws JsonProcessingException {
        System.out.println(jsonString);
        try{
            OrderStatusUpdateEvent orderStatusUpdateEvent = objectMapper.readValue(jsonString, OrderStatusUpdateEvent.class);
            if(orderStatusUpdateEvent.getOrderStatus() == OrderStatus.CONFIRMED) {
                System.out.println("Order Confirmed, Start preparing");
            }
        }catch (JsonProcessingException e){
            // Order initiation
            OrderInitiationEvent event = objectMapper.readValue(jsonString, OrderInitiationEvent.class);
            produce(true, event.getOrderId(), event.getOrderStatus());
        }
    }

    public void produce(Boolean available, String orderId, OrderStatus orderStatus) throws JsonProcessingException {
        switch (orderStatus){
            case INITIATED -> {
                if(available){
                    OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent(orderId, OrderStatus.ACCEPTED);
                    kafkaTemplate.send("orders-channel", objectMapper.writeValueAsString(orderStatusUpdateEvent));
                }
                else {
                    OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent(orderId, OrderStatus.REJECTED);
                    kafkaTemplate.send("orders-channel", objectMapper.writeValueAsString(orderStatusUpdateEvent));
                }
            }
            case CONFIRMED -> {
                OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent(orderId, OrderStatus.IN_PROGRESS);
                kafkaTemplate.send("orders-channel", objectMapper.writeValueAsString(orderStatusUpdateEvent));
            }

            case READY_FOR_PICKUP -> {
                OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent(orderId, OrderStatus.READY_FOR_PICKUP);
                kafkaTemplate.send("orders-channel", objectMapper.writeValueAsString(orderStatusUpdateEvent));
            }

            case PICKED_UP -> {
                OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent(orderId, OrderStatus.PICKED_UP);
                kafkaTemplate.send("orders-channel", objectMapper.writeValueAsString(orderStatusUpdateEvent));
            }
        }

    }
}
