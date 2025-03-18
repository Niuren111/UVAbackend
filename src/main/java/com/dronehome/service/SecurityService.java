package com.dronehome.service;

public interface SecurityService {
    
    /**
     * 检查当前用户是否为评论的所有者
     *
     * @param commentId 评论ID
     * @return 是否为评论所有者
     */
    boolean isCommentOwner(Long commentId);
    
    /**
     * 检查当前用户是否为商品的所有者
     *
     * @param productId 商品ID
     * @return 是否为商品所有者
     */
    boolean isProductOwner(Long productId);
    
    /**
     * 检查当前用户是否为订单的所有者
     *
     * @param orderId 订单ID
     * @return 是否为订单所有者
     */
    boolean isOrderOwner(Long orderId);
    
    /**
     * 检查当前用户是否为低空旅客服务的所有者
     *
     * @param lowAltitudePassengerId 低空旅客服务ID
     * @return 是否为低空旅客服务所有者
     */
    boolean isLowAltitudePassengerOwner(Long lowAltitudePassengerId);
} 