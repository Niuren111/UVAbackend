import React, { useState } from 'react';
import { Layout, Menu, Card, Row, Col, Typography, Space, Avatar, Tabs, List, Tag, Button, Descriptions } from 'antd';
import {
  UserOutlined,
  SettingOutlined,
  ShoppingOutlined,
  FileTextOutlined,
  HeartOutlined,
  SafetyCertificateOutlined
} from '@ant-design/icons';

const { Header, Content, Sider } = Layout;
const { Title, Text } = Typography;
const { TabPane } = Tabs;

interface Order {
  id: string;
  type: 'flight' | 'product' | 'training';
  title: string;
  status: string;
  time: string;
  amount: number;
}

interface Certificate {
  id: string;
  name: string;
  issueDate: string;
  expireDate: string;
  status: string;
}

const UserPage: React.FC = () => {
  const [selectedKey, setSelectedKey] = useState('profile');

  // 模拟的用户数据
  const userInfo = {
    name: '张三',
    avatar: '/avatar.jpg',
    phone: '13800138000',
    email: 'zhangsan@example.com',
    joinDate: '2023-01-01',
    level: '黄金会员'
  };

  // 模拟的订单数据
  const orders: Order[] = [
    {
      id: '1',
      type: 'flight',
      title: '北京城区观光航线',
      status: '已完成',
      time: '2024-03-15',
      amount: 599
    },
    {
      id: '2',
      type: 'product',
      title: 'DJI Mini 3 Pro',
      status: '待收货',
      time: '2024-03-16',
      amount: 4999
    },
    {
      id: '3',
      type: 'training',
      title: '无人机驾驶基础课程',
      status: '进行中',
      time: '2024-03-14',
      amount: 3800
    }
  ];

  // 模拟的证书数据
  const certificates: Certificate[] = [
    {
      id: '1',
      name: '无人机驾驶员执照',
      issueDate: '2023-06-01',
      expireDate: '2025-06-01',
      status: '有效'
    },
    {
      id: '2',
      name: '航拍资质证书',
      issueDate: '2023-08-01',
      expireDate: '2024-08-01',
      status: '有效'
    }
  ];

  const menuItems = [
    { key: 'profile', icon: <UserOutlined />, label: '个人资料' },
    { key: 'orders', icon: <ShoppingOutlined />, label: '我的订单' },
    { key: 'certificates', icon: <SafetyCertificateOutlined />, label: '我的证书' },
    { key: 'favorites', icon: <HeartOutlined />, label: '我的收藏' },
    { key: 'settings', icon: <SettingOutlined />, label: '账号设置' }
  ];

  const getStatusColor = (status: string) => {
    switch (status) {
      case '已完成':
        return 'green';
      case '待收货':
        return 'orange';
      case '进行中':
        return 'blue';
      default:
        return 'default';
    }
  };

  const renderContent = () => {
    switch (selectedKey) {
      case 'profile':
        return (
          <Card>
            <Row gutter={24}>
              <Col span={8} style={{ textAlign: 'center' }}>
                <Space direction="vertical" size="large">
                  <Avatar size={120} src={userInfo.avatar} icon={<UserOutlined />} />
                  <Title level={4}>{userInfo.name}</Title>
                  <Tag color="gold">{userInfo.level}</Tag>
                </Space>
              </Col>
              <Col span={16}>
                <Descriptions title="基本信息" column={1}>
                  <Descriptions.Item label="手机号码">{userInfo.phone}</Descriptions.Item>
                  <Descriptions.Item label="电子邮箱">{userInfo.email}</Descriptions.Item>
                  <Descriptions.Item label="注册时间">{userInfo.joinDate}</Descriptions.Item>
                </Descriptions>
              </Col>
            </Row>
          </Card>
        );

      case 'orders':
        return (
          <Card>
            <Tabs defaultActiveKey="all">
              <TabPane tab="全部订单" key="all">
                <List
                  itemLayout="horizontal"
                  dataSource={orders}
                  renderItem={order => (
                    <List.Item
                      actions={[
                        <Button type="link">查看详情</Button>,
                        <Button type="link">删除订单</Button>
                      ]}
                    >
                      <List.Item.Meta
                        title={order.title}
                        description={
                          <Space direction="vertical">
                            <Space>
                              <Text type="secondary">订单号：{order.id}</Text>
                              <Text type="secondary">下单时间：{order.time}</Text>
                            </Space>
                            <Space>
                              <Tag color={getStatusColor(order.status)}>{order.status}</Tag>
                              <Text type="danger">¥{order.amount}</Text>
                            </Space>
                          </Space>
                        }
                      />
                    </List.Item>
                  )}
                />
              </TabPane>
              <TabPane tab="待付款" key="unpaid" />
              <TabPane tab="待收货" key="undelivered" />
              <TabPane tab="已完成" key="completed" />
            </Tabs>
          </Card>
        );

      case 'certificates':
        return (
          <Card>
            <List
              itemLayout="horizontal"
              dataSource={certificates}
              renderItem={cert => (
                <List.Item
                  actions={[
                    <Button type="link">查看详情</Button>,
                    <Button type="link">下载证书</Button>
                  ]}
                >
                  <List.Item.Meta
                    title={cert.name}
                    description={
                      <Space direction="vertical">
                        <Space>
                          <Text type="secondary">发证日期：{cert.issueDate}</Text>
                          <Text type="secondary">有效期至：{cert.expireDate}</Text>
                        </Space>
                        <Tag color={cert.status === '有效' ? 'green' : 'red'}>
                          {cert.status}
                        </Tag>
                      </Space>
                    }
                  />
                </List.Item>
              )}
            />
          </Card>
        );

      default:
        return null;
    }
  };

  return (
    <Layout>
      <Header style={{ background: '#fff', padding: '0 50px' }}>
        <Title level={2} style={{ margin: '16px 0' }}>个人中心</Title>
      </Header>
      <Layout>
        <Sider width={200} style={{ background: '#fff' }}>
          <Menu
            mode="inline"
            selectedKeys={[selectedKey]}
            style={{ height: '100%', borderRight: 0 }}
            items={menuItems}
            onClick={({ key }) => setSelectedKey(key)}
          />
        </Sider>
        <Content style={{ padding: '24px', minHeight: 280 }}>
          {renderContent()}
        </Content>
      </Layout>
    </Layout>
  );
};

export default UserPage; 