import React, { useState, useEffect } from 'react';
import { Layout, Menu, Table, Space, Button, Tag, Modal, Form, Input, InputNumber, Select, message } from 'antd';
import {
  DashboardOutlined,
  UserOutlined,
  ShoppingOutlined,
  RocketOutlined,
  CommentOutlined,
  SettingOutlined
} from '@ant-design/icons';

const { Header, Content, Sider } = Layout;
const { Option } = Select;

interface LowAltitudePassenger {
  id: number;
  title: string;
  description: string;
  departureLocation: string;
  destinationLocation: string;
  routeInfo: string;
  passengerCapacity: number;
  bookedCount: number;
  price: number;
  status: number;
  createTime: string;
  remainingSeats: number;
}

const AdminPage: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [activeMenu, setActiveMenu] = useState('passengers');
  const [passengerList, setPassengerList] = useState<LowAltitudePassenger[]>([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingPassenger, setEditingPassenger] = useState<LowAltitudePassenger | null>(null);
  const [form] = Form.useForm();

  // 模拟数据
  useEffect(() => {
    // 这里应该是从API获取数据
    const mockData: LowAltitudePassenger[] = [
      {
        id: 1,
        title: '北京城区观光航线',
        description: '俯瞰北京城区美景，体验低空飞行的刺激与乐趣',
        departureLocation: '北京市海淀区起飞点',
        destinationLocation: '北京市海淀区降落点',
        routeInfo: '海淀区 -> 中关村 -> 北京大学 -> 颐和园 -> 海淀区',
        passengerCapacity: 4,
        bookedCount: 2,
        price: 599,
        status: 1,
        createTime: '2023-03-15 10:00:00',
        remainingSeats: 2
      },
      {
        id: 2,
        title: '长城航空观光',
        description: '俯瞰万里长城，感受中国历史文化的壮丽与雄伟',
        departureLocation: '北京市延庆区起飞点',
        destinationLocation: '北京市延庆区降落点',
        routeInfo: '延庆区 -> 八达岭长城 -> 延庆区',
        passengerCapacity: 4,
        bookedCount: 0,
        price: 899,
        status: 1,
        createTime: '2023-03-16 14:00:00',
        remainingSeats: 4
      }
    ];
    setPassengerList(mockData);
  }, []);

  // 打开编辑模态框
  const showEditModal = (record?: LowAltitudePassenger) => {
    setEditingPassenger(record || null);
    if (record) {
      form.setFieldsValue(record);
    } else {
      form.resetFields();
    }
    setIsModalVisible(true);
  };

  // 关闭模态框
  const handleCancel = () => {
    setIsModalVisible(false);
  };

  // 提交表单
  const handleSubmit = () => {
    form.validateFields().then(values => {
      if (editingPassenger) {
        // 更新
        const newData = passengerList.map(item => 
          item.id === editingPassenger.id ? { ...item, ...values } : item
        );
        setPassengerList(newData);
        message.success('更新成功');
      } else {
        // 新增
        const newPassenger: LowAltitudePassenger = {
          id: passengerList.length + 1,
          ...values,
          bookedCount: 0,
          createTime: new Date().toLocaleString(),
          remainingSeats: values.passengerCapacity
        };
        setPassengerList([...passengerList, newPassenger]);
        message.success('创建成功');
      }
      setIsModalVisible(false);
    });
  };

  // 删除记录
  const handleDelete = (id: number) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这条记录吗？',
      onOk: () => {
        setPassengerList(passengerList.filter(item => item.id !== id));
        message.success('删除成功');
      }
    });
  };

  // 更新状态
  const handleStatusChange = (record: LowAltitudePassenger, newStatus: number) => {
    const newData = passengerList.map(item => 
      item.id === record.id ? { ...item, status: newStatus } : item
    );
    setPassengerList(newData);
    message.success('状态更新成功');
  };

  // 低空旅客服务表格列
  const passengersColumns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 60
    },
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title'
    },
    {
      title: '出发地点',
      dataIndex: 'departureLocation',
      key: 'departureLocation'
    },
    {
      title: '目的地点',
      dataIndex: 'destinationLocation',
      key: 'destinationLocation'
    },
    {
      title: '容量',
      key: 'capacity',
      render: (text: any, record: LowAltitudePassenger) => (
        <span>{record.bookedCount}/{record.passengerCapacity}</span>
      )
    },
    {
      title: '价格(¥)',
      dataIndex: 'price',
      key: 'price'
    },
    {
      title: '状态',
      key: 'status',
      render: (text: any, record: LowAltitudePassenger) => {
        let color = 'default';
        let statusText = '未知';
        
        switch (record.status) {
          case 0:
            color = 'warning';
            statusText = '待上架';
            break;
          case 1:
            color = 'success';
            statusText = '已上架';
            break;
          case 2:
            color = 'default';
            statusText = '已下架';
            break;
        }
        
        return <Tag color={color}>{statusText}</Tag>;
      }
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      key: 'createTime'
    },
    {
      title: '操作',
      key: 'action',
      render: (text: any, record: LowAltitudePassenger) => (
        <Space size="middle">
          <Button type="link" onClick={() => showEditModal(record)}>编辑</Button>
          {record.status === 0 && (
            <Button type="link" onClick={() => handleStatusChange(record, 1)}>上架</Button>
          )}
          {record.status === 1 && (
            <Button type="link" onClick={() => handleStatusChange(record, 2)}>下架</Button>
          )}
          {record.status === 2 && (
            <Button type="link" onClick={() => handleStatusChange(record, 0)}>设为待上架</Button>
          )}
          <Button type="link" danger onClick={() => handleDelete(record.id)}>删除</Button>
        </Space>
      )
    }
  ];

  // 根据当前选中的菜单渲染内容
  const renderContent = () => {
    switch (activeMenu) {
      case 'passengers':
        return (
          <div>
            <div style={{ marginBottom: 16 }}>
              <Button type="primary" onClick={() => showEditModal()}>
                添加低空旅客服务
              </Button>
            </div>
            <Table 
              columns={passengersColumns} 
              dataSource={passengerList} 
              rowKey="id"
              pagination={{ pageSize: 10 }}
            />
          </div>
        );
      default:
        return (
          <div>
            <h2>功能开发中...</h2>
          </div>
        );
    }
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider collapsible collapsed={collapsed} onCollapse={setCollapsed}>
        <div style={{ height: 32, margin: 16, background: 'rgba(255, 255, 255, 0.3)' }} />
        <Menu
          theme="dark"
          defaultSelectedKeys={[activeMenu]}
          mode="inline"
          onClick={e => setActiveMenu(e.key)}
        >
          <Menu.Item key="dashboard" icon={<DashboardOutlined />}>
            仪表盘
          </Menu.Item>
          <Menu.Item key="passengers" icon={<RocketOutlined />}>
            低空旅客服务
          </Menu.Item>
          <Menu.Item key="products" icon={<ShoppingOutlined />}>
            商品管理
          </Menu.Item>
          <Menu.Item key="users" icon={<UserOutlined />}>
            用户管理
          </Menu.Item>
          <Menu.Item key="comments" icon={<CommentOutlined />}>
            评论管理
          </Menu.Item>
          <Menu.Item key="settings" icon={<SettingOutlined />}>
            系统设置
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout>
        <Header style={{ background: '#fff', padding: 0, paddingLeft: 16 }}>
          <h2>无人机行业平台管理后台</h2>
        </Header>
        <Content style={{ margin: '16px' }}>
          <div style={{ padding: 24, background: '#fff', minHeight: 360 }}>
            {renderContent()}
          </div>
        </Content>
      </Layout>

      {/* 编辑/创建模态框 */}
      <Modal
        title={editingPassenger ? '编辑低空旅客服务' : '添加低空旅客服务'}
        open={isModalVisible}
        onOk={handleSubmit}
        onCancel={handleCancel}
        width={800}
      >
        <Form
          form={form}
          layout="vertical"
        >
          <Form.Item name="title" label="标题" rules={[{ required: true, message: '请输入标题' }]}>
            <Input placeholder="请输入服务标题" />
          </Form.Item>
          <Form.Item name="description" label="描述">
            <Input.TextArea rows={4} placeholder="请输入服务描述" />
          </Form.Item>
          <Form.Item name="departureLocation" label="出发地点" rules={[{ required: true, message: '请输入出发地点' }]}>
            <Input placeholder="请输入出发地点" />
          </Form.Item>
          <Form.Item name="destinationLocation" label="目的地点" rules={[{ required: true, message: '请输入目的地点' }]}>
            <Input placeholder="请输入目的地点" />
          </Form.Item>
          <Form.Item name="routeInfo" label="路线信息">
            <Input placeholder="请输入路线信息" />
          </Form.Item>
          <Form.Item name="passengerCapacity" label="乘客容量" rules={[{ required: true, message: '请输入乘客容量' }]}>
            <InputNumber min={1} placeholder="请输入乘客容量" style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="price" label="价格" rules={[{ required: true, message: '请输入价格' }]}>
            <InputNumber min={0} placeholder="请输入价格" style={{ width: '100%' }} />
          </Form.Item>
          {!editingPassenger && (
            <Form.Item name="status" label="状态" initialValue={0}>
              <Select>
                <Option value={0}>待上架</Option>
                <Option value={1}>上架</Option>
                <Option value={2}>下架</Option>
              </Select>
            </Form.Item>
          )}
        </Form>
      </Modal>
    </Layout>
  );
};

export default AdminPage; 