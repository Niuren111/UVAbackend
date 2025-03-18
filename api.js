/**
 * 无人机行业平台 API交互层
 * 负责前端与后端的所有数据交互
 */

// 根据环境确定BASE_URL
const BASE_URL = window.location.hostname.includes('github.io') 
  ? 'https://api.dronehome.com'  // 生产环境
  : 'http://localhost:8080';  // 开发环境

// 检查API服务是否可用，如果不可用则使用模拟数据
let USE_MOCK_DATA = true;

// 模拟数据
const MOCK_DATA = {
  dashboard: {
    userCount: 128,
    orderCount: 85,
    productCount: 36,
    passengerCount: 12,
    recentOrders: [
      {id: '2023080001', userName: '张三', productName: '北京城区观光航线', amount: 599, status: '已完成', createTime: '2023-08-01 14:30'},
      {id: '2023080002', userName: '李四', productName: '长城航空观光', amount: 899, status: '已支付', createTime: '2023-08-02 09:15'},
      {id: '2023080003', userName: '王五', productName: '专业无人机模型', amount: 2999, status: '待发货', createTime: '2023-08-03 16:42'}
    ]
  },
  passengers: {
    records: [
      {id: 1, title: '北京城区观光航线', departureLocation: '北京市海淀区起飞点', destinationLocation: '北京市海淀区降落点', bookedCount: 2, passengerCapacity: 4, price: 599, status: 1},
      {id: 2, title: '长城航空观光', departureLocation: '北京市延庆区起飞点', destinationLocation: '北京市延庆区降落点', bookedCount: 0, passengerCapacity: 4, price: 899, status: 1},
      {id: 3, title: '故宫博物院上空观光', departureLocation: '北京市东城区起飞点', destinationLocation: '北京市东城区降落点', bookedCount: 1, passengerCapacity: 6, price: 1299, status: 0}
    ]
  },
  products: {
    records: [
      {id: 1, name: '专业航拍无人机', category: '无人机设备', price: 3999, stock: 25, sales: 58, status: 1},
      {id: 2, name: '入门级航拍无人机', category: '无人机设备', price: 999, stock: 42, sales: 126, status: 1},
      {id: 3, name: '无人机电池配件', category: '配件', price: 299, stock: 86, sales: 210, status: 1}
    ]
  },
  users: {
    records: [
      {id: 1, username: '张三', phone: '138****1234', registerTime: '2023-01-15', lastLoginTime: '2023-08-05', status: 1},
      {id: 2, username: '李四', phone: '139****5678', registerTime: '2023-02-20', lastLoginTime: '2023-08-04', status: 1},
      {id: 3, username: '王五', phone: '137****9012', registerTime: '2023-03-10', lastLoginTime: '2023-08-01', status: 1}
    ]
  },
  orders: {
    records: [
      {id: '2023080001', username: '张三', productName: '北京城区观光航线', amount: 599, payMethod: '微信支付', status: 3, createTime: '2023-08-01 14:30'},
      {id: '2023080002', username: '李四', productName: '长城航空观光', amount: 899, payMethod: '支付宝', status: 2, createTime: '2023-08-02 09:15'},
      {id: '2023080003', username: '王五', productName: '专业无人机模型', amount: 2999, payMethod: '银行卡', status: 1, createTime: '2023-08-03 16:42'}
    ]
  },
  settings: {
    siteName: '无人机行业平台',
    siteDescription: '无人机行业专业服务平台，提供低空旅客服务、无人机产品销售、培训机构等服务。',
    contactEmail: 'contact@example.com',
    contactPhone: '400-123-4567'
  },
  recruitment: {
    records: [
      {id: 1, title: '无人机飞行员', department: '运营部', location: '北京', salary: '8000-12000', publishDate: '2023-07-15', status: 1},
      {id: 2, title: '无人机维修工程师', department: '技术部', location: '上海', salary: '10000-15000', publishDate: '2023-07-20', status: 1},
      {id: 3, title: '市场推广专员', department: '市场部', location: '广州', salary: '7000-10000', publishDate: '2023-08-01', status: 1}
    ]
  },
  transport: {
    records: [
      {id: 1, taskNo: 'T20230801001', cargoType: '医疗物资', origin: '北京市朝阳区医疗中心', destination: '北京市海淀区医院', weight: 5.2, estimatedDeliveryTime: '2023-08-01 14:30', status: 2},
      {id: 2, taskNo: 'T20230802001', cargoType: '快递包裹', origin: '北京市海淀区物流中心', destination: '北京市西城区社区', weight: 3.8, estimatedDeliveryTime: '2023-08-02 16:00', status: 1},
      {id: 3, taskNo: 'T20230803001', cargoType: '农产品', origin: '北京市昌平区农场', destination: '北京市朝阳区超市', weight: 8.5, estimatedDeliveryTime: '2023-08-03 09:00', status: 0}
    ]
  },
  training: {
    records: [
      {id: 1, title: '无人机基础飞行培训', type: '初级培训', location: '北京市海淀区培训中心', startDate: '2023-08-10', endDate: '2023-08-15', price: 1999, enrolledCount: 8, capacity: 20, status: 1},
      {id: 2, title: '无人机航拍技术进阶', type: '中级培训', location: '上海市浦东新区培训基地', startDate: '2023-08-20', endDate: '2023-08-25', price: 2999, enrolledCount: 5, capacity: 15, status: 1},
      {id: 3, title: '无人机竞速赛培训', type: '高级培训', location: '广州市天河区训练场', startDate: '2023-09-01', endDate: '2023-09-10', price: 3999, enrolledCount: 3, capacity: 10, status: 1}
    ]
  }
};

// 通用请求函数
async function request(url, options = {}) {
  if (USE_MOCK_DATA) {
    return handleMockRequest(url);
  }

  // 默认选项
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include', // 包含cookie
  };

  // 合并选项
  const finalOptions = {
    ...defaultOptions,
    ...options,
    headers: {
      ...defaultOptions.headers,
      ...options.headers,
    },
  };

  // 处理请求体
  if (finalOptions.body && typeof finalOptions.body === 'object') {
    finalOptions.body = JSON.stringify(finalOptions.body);
  }

  try {
    // 发送请求
    const response = await fetch(`${BASE_URL}${url}`, finalOptions);
    
    // 解析响应
    const contentType = response.headers.get('content-type');
    let data;
    
    if (contentType && contentType.includes('application/json')) {
      data = await response.json();
    } else {
      data = await response.text();
    }
    
    // 处理错误响应
    if (!response.ok) {
      throw new Error(data.message || '请求失败');
    }
    
    return data;
  } catch (error) {
    console.error('API请求失败:', error);
    
    // 如果API服务不可用，切换到模拟数据
    if (!window.apiErrorShown) {
      console.warn('无法连接到API服务，切换到模拟数据模式');
      window.apiErrorShown = true;
      USE_MOCK_DATA = true;
      return handleMockRequest(url);
    }
    
    throw error;
  }
}

// 处理模拟请求
function handleMockRequest(url) {
  return new Promise((resolve) => {
    // 模拟网络延迟
    setTimeout(() => {
      // 解析URL，提取路径和参数
      if (url.includes('/api/statistics/dashboard')) {
        resolve({ data: MOCK_DATA.dashboard });
      } else if (url.includes('/api/low-altitude-passengers/page')) {
        resolve({ data: MOCK_DATA.passengers });
      } else if (url.includes('/api/low-altitude-passengers/') && !url.includes('/status/')) {
        const id = parseInt(url.split('/').pop());
        const passenger = MOCK_DATA.passengers.records.find(p => p.id === id);
        resolve({ data: passenger });
      } else if (url.includes('/api/products/page')) {
        resolve({ data: MOCK_DATA.products });
      } else if (url.includes('/api/products/') && !url.includes('/status/')) {
        const id = parseInt(url.split('/').pop());
        const product = MOCK_DATA.products.records.find(p => p.id === id);
        resolve({ data: product });
      } else if (url.includes('/api/users/page')) {
        resolve({ data: MOCK_DATA.users });
      } else if (url.includes('/api/users/') && !url.includes('/status/')) {
        const id = parseInt(url.split('/').pop());
        const user = MOCK_DATA.users.records.find(u => u.id === id);
        resolve({ data: user });
      } else if (url.includes('/api/orders/page')) {
        resolve({ data: MOCK_DATA.orders });
      } else if (url.includes('/api/orders/') && !url.includes('/status/')) {
        const id = url.split('/').pop();
        const order = MOCK_DATA.orders.records.find(o => o.id === id);
        resolve({ data: order });
      } else if (url === '/api/settings') {
        resolve({ data: MOCK_DATA.settings });
      } else if (url.includes('/api/recruitment/page')) {
        resolve({ data: MOCK_DATA.recruitment });
      } else if (url.includes('/api/recruitment/') && !url.includes('/status/')) {
        const id = parseInt(url.split('/').pop());
        const job = MOCK_DATA.recruitment.records.find(j => j.id === id);
        resolve({ data: job });
      } else if (url.includes('/api/transport/page')) {
        resolve({ data: MOCK_DATA.transport });
      } else if (url.includes('/api/transport/') && !url.includes('/status/')) {
        const id = parseInt(url.split('/').pop());
        const task = MOCK_DATA.transport.records.find(t => t.id === id);
        resolve({ data: task });
      } else if (url.includes('/api/training/page')) {
        resolve({ data: MOCK_DATA.training });
      } else if (url.includes('/api/training/') && !url.includes('/status/')) {
        const id = parseInt(url.split('/').pop());
        const course = MOCK_DATA.training.records.find(c => c.id === id);
        resolve({ data: course });
      } else {
        resolve({ message: '操作成功', success: true });
      }
    }, 300);
  });
}

// 封装API模块
const API = {
  // 认证相关
  auth: {
    // 登录
    login: (username, password) => {
      return request('/api/auth/login', {
        method: 'POST',
        body: { username, password },
      });
    },
    
    // 登出
    logout: () => {
      return request('/api/auth/logout', {
        method: 'POST',
      });
    },
    
    // 获取当前用户信息
    getCurrentUser: () => {
      return request('/api/auth/current-user');
    },
  },
  
  // 低空旅客服务相关
  passengers: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/low-altitude-passengers/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/low-altitude-passengers/${id}`);
    },
    
    // 创建
    create: (data) => {
      return request('/api/low-altitude-passengers', {
        method: 'POST',
        body: data,
      });
    },
    
    // 更新
    update: (id, data) => {
      return request(`/api/low-altitude-passengers/${id}`, {
        method: 'PUT',
        body: data,
      });
    },
    
    // 删除
    delete: (id) => {
      return request(`/api/low-altitude-passengers/${id}`, {
        method: 'DELETE',
      });
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/low-altitude-passengers/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
  },
  
  // 商品相关
  products: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/products/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/products/${id}`);
    },
    
    // 创建
    create: (data) => {
      return request('/api/products', {
        method: 'POST',
        body: data,
      });
    },
    
    // 更新
    update: (id, data) => {
      return request(`/api/products/${id}`, {
        method: 'PUT',
        body: data,
      });
    },
    
    // 删除
    delete: (id) => {
      return request(`/api/products/${id}`, {
        method: 'DELETE',
      });
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/products/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
  },
  
  // 用户相关
  users: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/users/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/users/${id}`);
    },
    
    // 更新
    update: (id, data) => {
      return request(`/api/users/${id}`, {
        method: 'PUT',
        body: data,
      });
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/users/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
  },
  
  // 订单相关
  orders: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/orders/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/orders/${id}`);
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/orders/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
  },
  
  // 系统设置相关
  settings: {
    // 获取设置
    get: () => {
      return request('/api/settings');
    },
    
    // 更新设置
    update: (data) => {
      return request('/api/settings', {
        method: 'PUT',
        body: data,
      });
    },
  },
  
  // 统计数据相关
  statistics: {
    // 获取仪表盘数据
    getDashboardData: () => {
      return request('/api/statistics/dashboard');
    },
  },
  
  // 招聘管理相关
  recruitment: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/recruitment/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/recruitment/${id}`);
    },
    
    // 创建
    create: (data) => {
      return request('/api/recruitment', {
        method: 'POST',
        body: data,
      });
    },
    
    // 更新
    update: (id, data) => {
      return request(`/api/recruitment/${id}`, {
        method: 'PUT',
        body: data,
      });
    },
    
    // 删除
    delete: (id) => {
      return request(`/api/recruitment/${id}`, {
        method: 'DELETE',
      });
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/recruitment/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
  },
  
  // 运输管理相关
  transport: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/transport/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/transport/${id}`);
    },
    
    // 创建
    create: (data) => {
      return request('/api/transport', {
        method: 'POST',
        body: data,
      });
    },
    
    // 更新
    update: (id, data) => {
      return request(`/api/transport/${id}`, {
        method: 'PUT',
        body: data,
      });
    },
    
    // 删除
    delete: (id) => {
      return request(`/api/transport/${id}`, {
        method: 'DELETE',
      });
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/transport/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
    
    // 开始运输
    start: (id) => {
      return request(`/api/transport/${id}/start`, {
        method: 'PUT',
      });
    },
    
    // 完成运输
    complete: (id) => {
      return request(`/api/transport/${id}/complete`, {
        method: 'PUT',
      });
    },
    
    // 取消运输
    cancel: (id) => {
      return request(`/api/transport/${id}/cancel`, {
        method: 'PUT',
      });
    },
  },
  
  // 培训管理相关
  training: {
    // 获取列表
    getAll: (params) => {
      const queryString = new URLSearchParams(params).toString();
      return request(`/api/training/page?${queryString}`);
    },
    
    // 获取详情
    getById: (id) => {
      return request(`/api/training/${id}`);
    },
    
    // 创建
    create: (data) => {
      return request('/api/training', {
        method: 'POST',
        body: data,
      });
    },
    
    // 更新
    update: (id, data) => {
      return request(`/api/training/${id}`, {
        method: 'PUT',
        body: data,
      });
    },
    
    // 删除
    delete: (id) => {
      return request(`/api/training/${id}`, {
        method: 'DELETE',
      });
    },
    
    // 更新状态
    updateStatus: (id, status) => {
      return request(`/api/training/${id}/status/${status}`, {
        method: 'PUT',
      });
    },
  },
};

// 导出API对象
window.API = API; 