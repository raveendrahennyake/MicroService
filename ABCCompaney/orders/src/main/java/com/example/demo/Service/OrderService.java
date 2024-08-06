package com.example.demo.Service;
import com.example.demo.D.T.O.InventoryDTO;
import com.example.demo.D.T.O.OrderDTO;
import com.example.demo.Model.Orders;
import com.example.demo.Repository.OrderRepo;
import com.example.demo.common.ErrorResponce;
import com.example.demo.common.OrderResponce;
import com.example.demo.common.SucessOrderResponce;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;
    private final WebClient webClient;

    public OrderService(WebClient.Builder webClientBuilder,OrderRepo orderRepo,ModelMapper modelMapper) {
       this.webClient=webClientBuilder.baseUrl("http://localhost:8081/api/v1").build();
        this.orderRepo=orderRepo;
        this.modelMapper=modelMapper;
    }
    public List<OrderDTO> getAllOrders() {
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }
    public OrderResponce saveOrder(OrderDTO OrderDTO) {
        Integer itemId=OrderDTO.getItemId();
        try {
            InventoryDTO inventoryResponce=webClient.get()
                    .uri(uriBuilder ->uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();
            assert inventoryResponce != null;
            if (inventoryResponce.getQuantity()>0){
                orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
                return new SucessOrderResponce(OrderDTO) ;

            }else{
                return new ErrorResponce("this item not availabel ");

            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
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
