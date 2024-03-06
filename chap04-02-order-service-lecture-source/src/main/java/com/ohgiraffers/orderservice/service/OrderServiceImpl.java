package com.ohgiraffers.orderservice.service;

import com.ohgiraffers.orderservice.aggregate.Order;
import com.ohgiraffers.orderservice.aggregate.OrderMenu;
import com.ohgiraffers.orderservice.dto.OrderDTO;
import com.ohgiraffers.orderservice.dto.OrderMenuDTO;
import com.ohgiraffers.orderservice.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> getUserOrders(String userId) {

        List<Order> orderList = orderMapper.selectOrdersByUserId(userId);

        // ordre -> orderDTO
        List<OrderDTO> orderDTOList = orderToOrderDTO(orderList);       // modelMapper 썼던 곳


        return orderDTOList;
    }

    // modelMapper 대신 수동으로 (에러가 어디서 나는 지 볼 수 있어서 추천)
    private List<OrderDTO> orderToOrderDTO(List<Order> orderList) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setOrderTime(order.getOrderTime());

            List<OrderMenu> orderMenuList = order.getOrderMenus();
            List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
            for (OrderMenu orderMenu : orderMenuList) {
                OrderMenuDTO orderMenuDTO = new OrderMenuDTO();
                orderMenuDTO.setOrderCode(orderMenu.getOrderCode());
                orderMenuDTO.setMenuCode(orderMenu.getMenuCode());
                orderMenuDTO.setOrderAmount(orderMenu.getOrderAmount());

                orderMenuDTOList.add(orderMenuDTO);
            }

            orderDTO.setOrderMenus(orderMenuDTOList);
            orderDTO.setTotalOrderPrice(order.getTotalOrderPrice());
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
}
