package com.example.demo.Control;

import com.example.demo.D.T.O.OrderDTO;
import com.example.demo.Service.OrderService;
import com.example.demo.common.OrderResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(value ="api/v1")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/getorders")
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/order/{orderId}")
    public OrderDTO getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }
    @PostMapping("/addorder")
    public OrderResponce saveOrder(@RequestBody OrderDTO userDTO) {
        return orderService.saveOrder(userDTO);
    }

    @PutMapping("/updateorder")
    public OrderDTO updateOrder(@RequestBody OrderDTO userDTO) {
        return orderService.updateOrder(userDTO);
    }

    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }
}
