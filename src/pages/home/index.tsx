import React from 'react';
import { Layout, Menu } from 'antd';
import { Link } from 'react-router-dom';
import {
  HomeOutlined,
  UserOutlined,
  ShoppingCartOutlined,
  FileTextOutlined,
  BookOutlined,
  ShopOutlined,
  TeamOutlined,
} from '@ant-design/icons';

const { Header, Content } = Layout;

const HomePage: React.FC = () => {
  const menuItems = [
    { key: '/', label: <Link to="/">首页</Link>, icon: <HomeOutlined /> },
    { key: '/jobs', label: <Link to="/jobs">无人机招聘</Link>, icon: <TeamOutlined /> },
    { key: '/tasks', label: <Link to="/tasks">运输任务</Link>, icon: <FileTextOutlined /> },
    { key: '/training', label: <Link to="/training">培训机构</Link>, icon: <BookOutlined /> },
    { key: '/booking', label: <Link to="/booking">低空载客</Link>, icon: <ShoppingCartOutlined /> },
    { key: '/marketplace', label: <Link to="/marketplace">无人机商城</Link>, icon: <ShopOutlined /> },
    { key: '/user', label: <Link to="/user">个人中心</Link>, icon: <UserOutlined /> },
  ];

  return (
    <Layout>
      <Header>
        <div className="logo" />
        <Menu
          theme="light"
          mode="horizontal"
          defaultSelectedKeys={['/']}
          items={menuItems}
        />
      </Header>
      <Content style={{ padding: '50px' }}>
        <h1>欢迎来到无人机产业平台</h1>
        <p>这里是无人机行业的一站式服务平台</p>
      </Content>
    </Layout>
  );
};

export default HomePage; 