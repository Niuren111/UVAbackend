# 实现前后端数据交互的步骤指南

## 步骤1: 在后台管理页面中引入API文件

在`simple-admin.html`文件的`</body>`标签之前添加以下代码：

```html
<!-- 引入API交互层 -->
<script src="/src/main/resources/static/js/api.js"></script>
```

## 步骤2: 修改后台管理页面的JavaScript代码

将现有的静态数据加载部分替换为从API获取数据的动态代码。在`simple-admin.html`文件中找到现有的JavaScript代码，并替换为以下内容：

```javascript
// 页面切换功能
document.addEventListener('DOMContentLoaded', async function() {
  // 菜单切换功能保持不变
  const menuItems = document.querySelectorAll('.menu-item');
  
  menuItems.forEach(item => {
    item.addEventListener('click', function() {
      // 移除所有菜单项的active类
      menuItems.forEach(mi => mi.classList.remove('active'));
      // 给当前点击的菜单项添加active类
      this.classList.add('active');
      
      // 获取要显示的页面ID
      const pageId = this.getAttribute('data-page');
      
      // 隐藏所有页面
      document.querySelectorAll('.page').forEach(page => {
        page.classList.remove('active');
      });
      
      // 显示目标页面
      document.getElementById(pageId).classList.add('active');
      
      // 根据页面ID加载相应数据
      loadPageData(pageId);
    });
  });
  
  // 加载仪表盘页面数据
  await loadPageData('dashboard');
  
  // 添加按钮功能
  setupButtonEvents();
});

// 加载页面数据
async function loadPageData(pageId) {
  try {
    switch(pageId) {
      case 'dashboard':
        await loadDashboardData();
        break;
      case 'passengers':
        await loadPassengersData();
        break;
      case 'products':
        await loadProductsData();
        break;
      case 'users':
        await loadUsersData();
        break;
      case 'orders':
        await loadOrdersData();
        break;
      case 'settings':
        await loadSettingsData();
        break;
    }
  } catch (error) {
    console.error(`加载${pageId}页面数据失败:`, error);
    alert(`加载数据失败: ${error.message}`);
  }
}

// 加载仪表盘数据
async function loadDashboardData() {
  try {
    // 从API获取仪表盘数据
    const data = await API.statistics.getDashboardData();
    
    // 更新卡片数据
    document.querySelector('.card:nth-child(1) .card-value').textContent = data.userCount;
    document.querySelector('.card:nth-child(2) .card-value').textContent = data.orderCount;
    document.querySelector('.card:nth-child(3) .card-value').textContent = data.productCount;
    document.querySelector('.card:nth-child(4) .card-value').textContent = data.passengerCount;
    
    // 清空并重新填充最近订单表格
    const tbody = document.querySelector('#dashboard .table tbody');
    tbody.innerHTML = '';
    
    data.recentOrders.forEach(order => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${order.id}</td>
        <td>${order.userName}</td>
        <td>${order.productName}</td>
        <td>¥${order.amount}</td>
        <td>${order.status}</td>
        <td>${order.createTime}</td>
      `;
      tbody.appendChild(tr);
    });
  } catch (error) {
    console.error('加载仪表盘数据失败:', error);
    throw error;
  }
}

// 加载低空旅客服务数据
async function loadPassengersData() {
  try {
    // 从API获取低空旅客服务数据
    const data = await API.passengers.getAll({ page: 1, size: 10 });
    
    // 清空并重新填充表格
    const tbody = document.querySelector('#passengers .table tbody');
    tbody.innerHTML = '';
    
    data.records.forEach(passenger => {
      const tr = document.createElement('tr');
      
      // 根据状态确定按钮
      let statusButtons = '';
      if (passenger.status === 0) {
        statusButtons = '<button class="button">上架</button>';
      } else if (passenger.status === 1) {
        statusButtons = '<button class="button button-default">下架</button>';
      } else {
        statusButtons = '<button class="button">设为待上架</button>';
      }
      
      // 设置状态文本
      let statusText = '未知';
      switch (passenger.status) {
        case 0: statusText = '待上架'; break;
        case 1: statusText = '已上架'; break;
        case 2: statusText = '已下架'; break;
      }
      
      tr.innerHTML = `
        <td>${passenger.id}</td>
        <td>${passenger.title}</td>
        <td>${passenger.departureLocation}</td>
        <td>${passenger.destinationLocation}</td>
        <td>${passenger.bookedCount}/${passenger.passengerCapacity}</td>
        <td>¥${passenger.price}</td>
        <td>${statusText}</td>
        <td>
          <button class="button button-default" data-id="${passenger.id}" data-action="edit">编辑</button>
          <button class="button button-danger" data-id="${passenger.id}" data-action="delete">删除</button>
          ${statusButtons}
        </td>
      `;
      tbody.appendChild(tr);
    });
    
    // 添加按钮事件
    addPassengerButtonEvents();
  } catch (error) {
    console.error('加载低空旅客服务数据失败:', error);
    throw error;
  }
}

// 类似地实现其他页面的数据加载函数
// loadProductsData(), loadUsersData(), loadOrdersData(), loadSettingsData()

// 为低空旅客服务按钮添加事件
function addPassengerButtonEvents() {
  document.querySelectorAll('#passengers .table button').forEach(button => {
    button.addEventListener('click', async function() {
      const id = this.getAttribute('data-id');
      const action = this.getAttribute('data-action');
      
      try {
        switch(action) {
          case 'edit':
            // 编辑操作
            const passenger = await API.passengers.getById(id);
            // 显示编辑表单
            showEditPassengerForm(passenger);
            break;
            
          case 'delete':
            // 删除操作
            if (confirm('确定要删除该低空旅客服务吗？')) {
              await API.passengers.delete(id);
              alert('删除成功');
              loadPassengersData();
            }
            break;
            
          default:
            // 状态变更操作
            let newStatus;
            if (this.textContent === '上架') {
              newStatus = 1;
            } else if (this.textContent === '下架') {
              newStatus = 2;
            } else {
              newStatus = 0;
            }
            
            await API.passengers.updateStatus(id, newStatus);
            alert('状态更新成功');
            loadPassengersData();
        }
      } catch (error) {
        console.error('操作失败:', error);
        alert(`操作失败: ${error.message}`);
      }
    });
  });
}

// 设置其他页面的按钮事件
function setupButtonEvents() {
  // 添加低空旅客服务按钮
  document.getElementById('add-passenger').addEventListener('click', function() {
    showAddPassengerForm();
  });
  
  // 添加商品按钮
  document.getElementById('add-product').addEventListener('click', function() {
    showAddProductForm();
  });
  
  // 保存设置按钮
  document.getElementById('save-settings').addEventListener('click', async function() {
    try {
      const settings = {
        siteName: document.getElementById('site-name').value,
        siteDescription: document.getElementById('site-description').value,
        contactEmail: document.getElementById('contact-email').value,
        contactPhone: document.getElementById('contact-phone').value
      };
      
      await API.settings.update(settings);
      alert('设置已保存！');
    } catch (error) {
      console.error('保存设置失败:', error);
      alert(`保存失败: ${error.message}`);
    }
  });
}

// 显示添加低空旅客服务表单
function showAddPassengerForm() {
  // 实现添加表单的显示逻辑
  alert('添加低空旅客服务功能正在开发中...');
}

// 显示编辑低空旅客服务表单
function showEditPassengerForm(passenger) {
  // 实现编辑表单的显示逻辑
  alert('编辑低空旅客服务功能正在开发中...');
}

// 显示添加商品表单
function showAddProductForm() {
  // 实现添加商品表单的显示逻辑
  alert('添加商品功能正在开发中...');
}
```

## 步骤3: 配置后端服务

前端已经准备好与后端交互，但我们需要确保后端有对应的API接口。以下是后端需要实现的API接口列表：

1. **统计数据API**
   - `GET /api/statistics/dashboard` - 获取仪表盘数据

2. **低空旅客服务API**
   - `GET /api/low-altitude-passengers/page` - 分页获取低空旅客服务列表
   - `GET /api/low-altitude-passengers/{id}` - 获取单个低空旅客服务详情
   - `POST /api/low-altitude-passengers` - 创建低空旅客服务
   - `PUT /api/low-altitude-passengers/{id}` - 更新低空旅客服务
   - `DELETE /api/low-altitude-passengers/{id}` - 删除低空旅客服务
   - `PUT /api/low-altitude-passengers/{id}/status/{status}` - 更新低空旅客服务状态

3. **商品API**
   - `GET /api/products/page` - 分页获取商品列表
   - `GET /api/products/{id}` - 获取单个商品详情
   - `POST /api/products` - 创建商品
   - `PUT /api/products/{id}` - 更新商品
   - `DELETE /api/products/{id}` - 删除商品
   - `PUT /api/products/{id}/status/{status}` - 更新商品状态

4. **用户API**
   - `GET /api/users/page` - 分页获取用户列表
   - `GET /api/users/{id}` - 获取单个用户详情
   - `PUT /api/users/{id}` - 更新用户信息
   - `PUT /api/users/{id}/status/{status}` - 更新用户状态

5. **订单API**
   - `GET /api/orders/page` - 分页获取订单列表
   - `GET /api/orders/{id}` - 获取单个订单详情
   - `PUT /api/orders/{id}/status/{status}` - 更新订单状态

6. **系统设置API**
   - `GET /api/settings` - 获取系统设置
   - `PUT /api/settings` - 更新系统设置

7. **认证API**
   - `POST /api/auth/login` - 用户登录
   - `POST /api/auth/logout` - 用户登出
   - `GET /api/auth/current-user` - 获取当前登录用户信息

## 步骤4: 后端Controller实现示例

以下是低空旅客服务Controller的实现示例（已存在）：

```java
@RestController
@RequestMapping("/api/low-altitude-passengers")
@Api(tags = "低空旅客管理接口")
public class LowAltitudePassengerController {

    @Autowired
    private LowAltitudePassengerService lowAltitudePassengerService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ApiOperation("创建低空旅客服务")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public Result<LowAltitudePassengerVO> createLowAltitudePassenger(
            @RequestBody @Valid LowAltitudePassengerCreateDTO createDTO,
            HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request);
        LowAltitudePassengerVO vo = lowAltitudePassengerService.createLowAltitudePassenger(createDTO, userId);
        return Result.success(vo, "创建成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取低空旅客服务详情")
    public Result<LowAltitudePassengerVO> getLowAltitudePassengerDetail(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id) {
        LowAltitudePassengerVO vo = lowAltitudePassengerService.getLowAltitudePassengerDetail(id);
        return Result.success(vo);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询低空旅客服务")
    public Result<Page<LowAltitudePassengerVO>> pageLowAltitudePassengers(
            @Valid LowAltitudePassengerQueryDTO queryDTO) {
        Page<LowAltitudePassengerVO> page = lowAltitudePassengerService.pageLowAltitudePassengers(queryDTO);
        return Result.success(page);
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation("更新低空旅客服务状态")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isLowAltitudePassengerOwner(#id)")
    public Result<Boolean> updateStatus(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id,
            @ApiParam(value = "状态：0-待上架，1-已上架，2-已下架", required = true) @PathVariable Integer status) {
        boolean result = lowAltitudePassengerService.updateStatus(id, status);
        return Result.success(result, "状态更新成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除低空旅客服务")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isLowAltitudePassengerOwner(#id)")
    public Result<Boolean> deleteLowAltitudePassenger(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id) {
        boolean result = lowAltitudePassengerService.deleteLowAltitudePassenger(id);
        return Result.success(result, "删除成功");
    }
}
```

## 步骤5: 配置CORS（跨域资源共享）

由于前端和后端可能部署在不同的域名下，需要配置CORS以允许跨域请求：

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // 在生产环境中应该限制为特定域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

## 步骤6: 测试前后端交互

完成以上步骤后，您可以通过以下方式测试前后端交互：

1. 启动后端服务
2. 访问前端页面
3. 打开浏览器开发者工具，查看网络请求和控制台日志
4. 尝试各种操作（查看列表、添加、编辑、删除等），确认前后端数据交互正常

## 注意事项

1. 前端API请求中可能会出现跨域问题，确保后端正确配置了CORS
2. 后端API返回的数据结构需要与前端期望的结构一致
3. 需要处理各种错误情况，如网络错误、权限错误等
4. 在生产环境中，应该使用HTTPS和更严格的安全措施 