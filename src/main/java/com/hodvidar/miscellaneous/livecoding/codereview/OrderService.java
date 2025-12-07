package com.hodvidar.miscellaneous.livecoding.codereview;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private OrderDao orderDao = new OrderDao();
    private Connection connection;

    public void processOrders(List<OrderDto> dtos) {
        for (OrderDto dto : dtos) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE id = " + dto.getProductId());
                while (rs.next()) {
                    Order order = new Order();
                    order.setProductId(rs.getLong("id"));
                    order.setQuantity(dto.getQuantity());
                    order.setCustomerId(dto.getCustomerId());
                    orderDao.save(order);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors du traitement de la commande");
            }
        }
    }

    private boolean validate(OrderDto dto) {
        return dto != null && dto.getQuantity() > 0;
    }

    public String whatDoIReturn(File file) {
        try {
            file.length();
            return "GOOD";
        } catch (Exception e) {
            return "BAD";
        } finally {
            return "OK";
        }
    }

    public static Map<String, String> groupByFirstLetter(List<String> words) {
        Map<String, String> map = new HashMap<>();
        for (String w : words) {
            String key = w.substring(0, 1).toUpperCase();
            map.put(key, w);
        }
        return map;
    }

    public static void removeNegatives(List<Integer> numbers) {
        for (Integer n : numbers) {
            if (n < 0) {
                numbers.remove(n);
            }
        }
    }

    public static double average(int[] values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum / (values.length - 1);
    }

    // ---------------------------------------------------------------
    // -------------------- Classes internes -------------------------
    // ---------------------------------------------------------------

    public static class MySingleton {
        public static final MySingleton INSTANCE = new MySingleton();

        public MySingleton() {
        }

        public void doSomething() {

        }
    }

    /**
     * DAO très simplifié – uniquement à des fins d’illustration.
     */
    static class OrderDao {

        /** Simule la persistance d’une commande. */
        void save(Order order) {
            // Ici on ferait un INSERT via JDBC ou JPA.
            // Cette implémentation factice ne fait rien.
        }

        /** Retourne toutes les commandes stockées (simulation). */
        List<Order> findAll() {
            return new ArrayList<>();
        }
    }

    /**
     * DTO (Data Transfer Object) utilisé pour recevoir les données
     * d’une commande depuis la couche présentation ou l’API.
     */
    static class OrderDto {
        private Long productId;
        private Integer quantity;
        private Long customerId;

        // ----- getters / setters -------------------------------------------------
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }
    }

    /**
     * Entité métier représentant une commande.
     */
    static class Order {
        private Long productId;
        private Integer quantity;
        private Long customerId;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }
    }

    /**
     * Exception métier personnalisée.
     */
    static class BusinessException extends Exception {
        public BusinessException(String message) {
            super(message);
        }

        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}