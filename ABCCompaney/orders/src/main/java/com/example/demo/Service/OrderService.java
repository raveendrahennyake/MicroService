package com.example.demo.Service;

import com.example.demo.D.T.O.InventoryDTO;
import com.example.demo.D.T.O.OrderDTO;
import com.example.demo.Model.Orders;
import com.example.demo.Repository.OrderRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;
    private final WebClient webClient;

    public OrderService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderDTO saveOrder(OrderDTO OrderDTO) {
        Integer itemId=OrderDTO.getItemId();
        try {
            InventoryDTO inventoryResponce=webClient.get()
                    .uri(uriBuilder ->  "http://localhost:8081/api/v1/item//{itemId}")
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

        }
        catch (Exception e){
            System.out.println(e);
        }orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
        return OrderDTO;



    }

    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
        return OrderDTO;
    }

    public String deleteOrder(Integer orderId) {
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }

    public OrderDTO getOrderById(Integer orderId) {
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
