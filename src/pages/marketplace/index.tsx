import React, { useState } from 'react';
import { Layout, Card, Row, Col, Typography, Space, Button, Input, Select, Tag, Rate, Badge, Menu } from 'antd';
import { ShoppingCartOutlined, HeartOutlined, FilterOutlined } from '@ant-design/icons';

const { Header, Content, Sider } = Layout;
const { Title, Text } = Typography;
const { Search } = Input;
const { Option } = Select;
const { Meta } = Card;

interface Product {
  id: string;
  name: string;
  category: string;
  price: number;
  originalPrice: number;
  rating: number;
  sales: number;
  image: string;
  tags: string[];
}

const MarketplacePage: React.FC = () => {
  const [selectedCategory, setSelectedCategory] = useState<string>('all');

  // 模拟的商品数据
  const mockProducts: Product[] = [
    {
      id: '1',
      name: 'DJI Mini 3 Pro',
      category: 'consumer',
      price: 4999,
      originalPrice: 5499,
      rating: 4.8,
      sales: 1000,
      image: '/images/drone1.jpg',
      tags: ['轻量便携', '4K视频', '长续航']
    },
    {
      id: '2',
      name: 'DJI Mavic 3',
      category: 'professional',
      price: 12999,
      originalPrice: 13999,
      rating: 4.9,
      sales: 500,
      image: '/images/drone2.jpg',
      tags: ['专业摄影', '8K视频', 'Hasselblad']
    },
    {
      id: '3',
      name: 'DJI Agras T40',
      category: 'industrial',
      price: 29999,
      originalPrice: 31999,
      rating: 4.7,
      sales: 200,
      image: '/images/drone3.jpg',
      tags: ['农业植保', '大载重', '智能作业']
    }
  ];

  const categories = [
    { key: 'all', label: '全部商品' },
    { key: 'consumer', label: '消费级无人机' },
    { key: 'professional', label: '专业级无人机' },
    { key: 'industrial', label: '工业级无人机' },
    { key: 'accessories', label: '配件与周边' }
  ];

  const handleSearch = (value: string) => {
    console.log('搜索:', value);
  };

  const handleCategoryChange = (key: string) => {
    setSelectedCategory(key);
  };

  return (
    <Layout>
      <Header style={{ background: '#fff', padding: '0 50px' }}>
        <Row justify="space-between" align="middle" style={{ height: '100%' }}>
          <Title level={2} style={{ margin: '16px 0' }}>无人机商城</Title>
          <Space size="large">
            <Search
              placeholder="搜索商品"
              allowClear
              enterButton
              style={{ width: 300 }}
              onSearch={handleSearch}
            />
            <Badge count={5}>
              <Button icon={<ShoppingCartOutlined />} size="large">
                购物车
              </Button>
            </Badge>
          </Space>
        </Row>
      </Header>
      <Layout>
        <Sider width={200} style={{ background: '#fff' }}>
          <Menu
            mode="inline"
            selectedKeys={[selectedCategory]}
            style={{ height: '100%' }}
            items={categories.map(category => ({
              key: category.key,
              label: category.label,
              onClick: () => handleCategoryChange(category.key)
            }))}
          />
        </Sider>
        <Content style={{ padding: '0 24px', minHeight: 280 }}>
          <div style={{ background: '#fff', padding: '24px' }}>
            <Row gutter={[16, 16]}>
              {mockProducts.map(product => (
                <Col span={8} key={product.id}>
                  <Badge.Ribbon text="热销" color="red">
                    <Card
                      hoverable
                      cover={<img alt={product.name} src={product.image} />}
                      actions={[
                        <Button key="buy" type="primary">立即购买</Button>,
                        <Button key="cart" icon={<ShoppingCartOutlined />}>加入购物车</Button>,
                        <HeartOutlined key="favorite" />
                      ]}
                    >
                      <Meta
                        title={<Text strong>{product.name}</Text>}
                        description={
                          <Space direction="vertical" size="small">
                            <Space size="large">
                              <Text type="danger" strong>¥{product.price}</Text>
                              <Text delete type="secondary">¥{product.originalPrice}</Text>
                            </Space>
                            <Space size="small">
                              {product.tags.map(tag => (
                                <Tag key={tag} color="blue">{tag}</Tag>
                              ))}
                            </Space>
                            <Space size="large">
                              <Rate disabled defaultValue={product.rating} />
                              <Text type="secondary">已售{product.sales}件</Text>
                            </Space>
                          </Space>
                        }
                      />
                    </Card>
                  </Badge.Ribbon>
                </Col>
              ))}
            </Row>
          </div>
        </Content>
      </Layout>
    </Layout>
  );
};

export default MarketplacePage; 