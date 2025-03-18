/**
 * 无人机行业平台 API交互层
 * 负责前端与后端的所有数据交互
 */

// 根据环境确定BASE_URL
const BASE_URL = window.location.hostname === 'localhost' 
  ? 'http://localhost:8080'  // 开发环境
  : 'https://api.dronehome.com';  // 生产环境

// 通用请求函数
async function request(url, options = {}) {
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
    throw error;
  }
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
};

// 导出API对象
window.API = API; 