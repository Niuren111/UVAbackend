package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@ApiModel("商品创建DTO")
public class ProductCreateDTO {
    
    @NotBlank(message = "商品名称不能为空")
    @ApiModelProperty(value = "商品名称", required = true, example = "大疆 Mavic 3 Pro 无人机")
    private String name;
    
    @ApiModelProperty(value = "商品描述", example = "大疆 Mavic 3 Pro 无人机，专业航拍利器")
    private String description;
    
    @NotNull(message = "商品价格不能为空")
    @Positive(message = "商品价格必须大于0")
    @ApiModelProperty(value = "商品价格", required = true, example = "15999.00")
    private BigDecimal price;
    
    @NotNull(message = "库存数量不能为空")
    @PositiveOrZero(message = "库存数量必须大于等于0")
    @ApiModelProperty(value = "库存数量", required = true, example = "100")
    private Integer stock;
    
    @NotBlank(message = "商品分类不能为空")
    @ApiModelProperty(value = "商品分类", required = true, example = "无人机")
    private String category;
    
    @NotBlank(message = "品牌不能为空")
    @ApiModelProperty(value = "品牌", required = true, example = "大疆")
    private String brand;
    
    @NotBlank(message = "型号不能为空")
    @ApiModelProperty(value = "型号", required = true, example = "Mavic 3 Pro")
    private String model;
    
    @ApiModelProperty(value = "商品图片，多个图片URL以逗号分隔", example = "https://example.com/image1.jpg,https://example.com/image2.jpg")
    private String images;
    
    @ApiModelProperty(value = "规格参数", example = "尺寸：折叠后221×96.3×90.3 mm，展开后347.5×283×107.7 mm")
    private String specifications;
    
    @ApiModelProperty(value = "技术参数", example = "最大飞行时间：43分钟，最大飞行距离：30公里")
    private String parameters;
} 