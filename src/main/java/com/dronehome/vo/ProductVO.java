package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("商品VO")
public class ProductVO {
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "商品名称", example = "大疆 Mavic 3 Pro 无人机")
    private String name;
    
    @ApiModelProperty(value = "商品简介", example = "大疆最新旗舰级无人机，搭载哈苏相机")
    private String description;
    
    @ApiModelProperty(value = "分类ID", example = "1")
    private Long categoryId;
    
    @ApiModelProperty(value = "分类名称", example = "消费级无人机")
    private String categoryName;
    
    @ApiModelProperty(value = "品牌ID", example = "1")
    private Long brandId;
    
    @ApiModelProperty(value = "品牌名称", example = "大疆")
    private String brandName;
    
    @ApiModelProperty(value = "商品价格", example = "13999.00")
    private BigDecimal price;
    
    @ApiModelProperty(value = "原价", example = "14999.00")
    private BigDecimal originalPrice;
    
    @ApiModelProperty(value = "主图", example = "https://example.com/images/mavic3pro.jpg")
    private String mainImage;
    
    @ApiModelProperty(value = "图片列表")
    private List<String> imageList;
    
    @ApiModelProperty(value = "商品详情", example = "<p>大疆 Mavic 3 Pro 是一款旗舰级无人机，搭载哈苏相机，支持4K高清视频拍摄，续航时间长达46分钟。</p>")
    private String detail;
    
    @ApiModelProperty(value = "规格参数", example = "{\"重量\":\"895g\",\"最大飞行时间\":\"46分钟\",\"最大飞行距离\":\"30km\",\"相机\":\"哈苏相机，4/3 CMOS\"}")
    private String specs;
    
    @ApiModelProperty(value = "库存", example = "100")
    private Integer stock;
    
    @ApiModelProperty(value = "销量", example = "50")
    private Integer sales;
    
    @ApiModelProperty(value = "评分", example = "4.8")
    private Double rating;
    
    @ApiModelProperty(value = "评论数量", example = "10")
    private Integer commentCount;
    
    @ApiModelProperty(value = "状态：0-下架，1-上架", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "卖家ID", example = "4")
    private Long sellerId;
    
    @ApiModelProperty(value = "卖家名称", example = "seller1")
    private String sellerName;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-21 09:00:00")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间", example = "2024-03-21 09:00:00")
    private LocalDateTime updateTime;
} 