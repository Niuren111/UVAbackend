// 管理后台功能修复脚本

// 模拟API响应数据
const mockData = {
  passengers: [],
  products: [],
  recruitments: [],
  transports: [],
  trainings: []
};

// 通用添加函数
function addItem(type, formId) {
  const form = document.getElementById(formId);
  if (!form) {
    alert('表单不存在');
    return false;
  }
  
  // 获取表单数据
  const formData = new FormData(form);
  const data = {};
  formData.forEach((value, key) => {
    data[key] = value;
  });
  
  // 添加ID和时间戳
  data.id = Date.now();
  data.createTime = new Date().toISOString();
  
  // 添加到对应的数据集合
  mockData[type].push(data);
  
  // 刷新表格
  refreshTable(type);
  
  // 关闭模态窗口
  closeModal(formId.replace('Form', 'Modal'));
  
  alert('添加成功');
  return false; // 阻止表单默认提交
}

// 刷新表格函数
function refreshTable(type) {
  const tableId = type + 'Table';
  const table = document.getElementById(tableId);
  if (!table) return;
  
  const tbody = table.querySelector('tbody');
  if (!tbody) return;
  
  // 清空表格
  tbody.innerHTML = '';
  
  // 根据类型生成表格行
  mockData[type].forEach(item => {
    const tr = document.createElement('tr');
    
    // 根据不同类型生成不同的表格内容
    switch(type) {
      case 'passengers':
        tr.innerHTML = `
          <td>${item.id}</td>
          <td>${item.title || ''}</td>
          <td>${item.departureLocation || ''}</td>
          <td>${item.destination || ''}</td>
          <td>${item.capacity || ''}</td>
          <td>${item.price || ''}</td>
          <td>${item.status || '未上架'}</td>
          <td>
            <button class="btn btn-sm btn-primary" onclick="editPassenger(${item.id})">编辑</button>
            <button class="btn btn-sm btn-success" onclick="publishPassenger(${item.id})">上架</button>
            <button class="btn btn-sm btn-danger" onclick="deleteItem('passengers', ${item.id})">删除</button>
          </td>
        `;
        break;
      case 'products':
        tr.innerHTML = `
          <td>${item.id}</td>
          <td>${item.name || ''}</td>
          <td>${item.category || ''}</td>
          <td>${item.price || ''}</td>
          <td>${item.stock || ''}</td>
          <td>${item.status || '未上架'}</td>
          <td>
            <button class="btn btn-sm btn-primary" onclick="editProduct(${item.id})">编辑</button>
            <button class="btn btn-sm btn-success" onclick="publishProduct(${item.id})">上架</button>
            <button class="btn btn-sm btn-danger" onclick="deleteItem('products', ${item.id})">删除</button>
          </td>
        `;
        break;
      case 'recruitments':
        tr.innerHTML = `
          <td>${item.id}</td>
          <td>${item.title || ''}</td>
          <td>${item.company || ''}</td>
          <td>${item.location || ''}</td>
          <td>${item.salary || ''}</td>
          <td>${item.createTime ? new Date(item.createTime).toLocaleDateString() : ''}</td>
          <td>
            <button class="btn btn-sm btn-primary" onclick="editRecruitment(${item.id})">编辑</button>
            <button class="btn btn-sm btn-danger" onclick="deleteItem('recruitments', ${item.id})">删除</button>
          </td>
        `;
        break;
      case 'transports':
        tr.innerHTML = `
          <td>${item.id}</td>
          <td>${item.taskName || ''}</td>
          <td>${item.origin || ''}</td>
          <td>${item.destination || ''}</td>
          <td>${item.weight || ''}</td>
          <td>${item.status || '待处理'}</td>
          <td>
            <button class="btn btn-sm btn-primary" onclick="editTransport(${item.id})">编辑</button>
            <button class="btn btn-sm btn-success" onclick="updateTransportStatus(${item.id}, '已完成')">完成</button>
            <button class="btn btn-sm btn-danger" onclick="deleteItem('transports', ${item.id})">删除</button>
          </td>
        `;
        break;
      case 'trainings':
        tr.innerHTML = `
          <td>${item.id}</td>
          <td>${item.courseName || ''}</td>
          <td>${item.instructor || ''}</td>
          <td>${item.duration || ''}</td>
          <td>${item.price || ''}</td>
          <td>${item.status || '未开始'}</td>
          <td>
            <button class="btn btn-sm btn-primary" onclick="editTraining(${item.id})">编辑</button>
            <button class="btn btn-sm btn-success" onclick="updateTrainingStatus(${item.id}, '进行中')">开始</button>
            <button class="btn btn-sm btn-danger" onclick="deleteItem('trainings', ${item.id})">删除</button>
          </td>
        `;
        break;
    }
    
    tbody.appendChild(tr);
  });
}

// 删除项目函数
function deleteItem(type, id) {
  if (confirm('确认删除此项吗？')) {
    const index = mockData[type].findIndex(item => item.id === id);
    if (index !== -1) {
      mockData[type].splice(index, 1);
      refreshTable(type);
      alert('删除成功');
    }
  }
}

// 打开模态窗口
function openModal(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.style.display = 'block';
  }
}

// 关闭模态窗口
function closeModal(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.style.display = 'none';
  }
}

// 状态更新函数
function updateStatus(type, id, status) {
  const index = mockData[type].findIndex(item => item.id === id);
  if (index !== -1) {
    mockData[type][index].status = status;
    refreshTable(type);
    alert('状态已更新');
  }
}

// 特定功能函数
function publishPassenger(id) {
  updateStatus('passengers', id, '已上架');
}

function publishProduct(id) {
  updateStatus('products', id, '已上架');
}

function updateTransportStatus(id, status) {
  updateStatus('transports', id, status);
}

function updateTrainingStatus(id, status) {
  updateStatus('trainings', id, status);
}

// 初始化函数 - 在页面加载完成后调用
function initAdminFunctions() {
  // 绑定添加功能
  document.addEventListener('submit', function(e) {
    // 处理各种表单提交
    if (e.target.id === 'passengerForm') {
      e.preventDefault();
      addItem('passengers', 'passengerForm');
    } else if (e.target.id === 'productForm') {
      e.preventDefault();
      addItem('products', 'productForm');
    } else if (e.target.id === 'recruitmentForm') {
      e.preventDefault();
      addItem('recruitments', 'recruitmentForm');
    } else if (e.target.id === 'transportForm') {
      e.preventDefault();
      addItem('transports', 'transportForm');
    } else if (e.target.id === 'trainingForm') {
      e.preventDefault();
      addItem('trainings', 'trainingForm');
    }
  });
  
  // 为各个页面添加"添加"按钮的事件监听器
  const addButtons = document.querySelectorAll('.add-button');
  addButtons.forEach(button => {
    button.addEventListener('click', function() {
      const modalId = this.getAttribute('data-target');
      openModal(modalId);
    });
  });
  
  // 为各个页面添加"关闭"按钮的事件监听器
  const closeButtons = document.querySelectorAll('.close-modal');
  closeButtons.forEach(button => {
    button.addEventListener('click', function() {
      const modalId = this.getAttribute('data-target');
      closeModal(modalId);
    });
  });
  
  console.log('管理后台功能已初始化');
}

// 当DOM加载完成后初始化
document.addEventListener('DOMContentLoaded', initAdminFunctions); 