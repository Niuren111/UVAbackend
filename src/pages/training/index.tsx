import React, { useState } from 'react';
import { Layout, Card, Row, Col, Typography, Space, Rate, Tag, Button, Input, Select } from 'antd';
import { EnvironmentOutlined, PhoneOutlined, ClockCircleOutlined, TeamOutlined } from '@ant-design/icons';

const { Header, Content } = Layout;
const { Title, Text, Paragraph } = Typography;
const { Search } = Input;
const { Option } = Select;

interface Course {
  id: string;
  name: string;
  duration: string;
  price: number;
  level: string;
}

interface Institution {
  id: string;
  name: string;
  location: string;
  phone: string;
  rating: number;
  students: number;
  description: string;
  tags: string[];
  courses: Course[];
}

const TrainingPage: React.FC = () => {
  const [searchKeyword, setSearchKeyword] = useState('');
  const [selectedLocation, setSelectedLocation] = useState<string>('');

  // 模拟的培训机构数据
  const mockInstitutions: Institution[] = [
    {
      id: '1',
      name: '天翔无人机培训学院',
      location: '北京市海淀区',
      phone: '010-12345678',
      rating: 4.5,
      students: 1000,
      description: '专业的无人机培训机构，提供全方位的无人机驾驶培训课程',
      tags: ['AOPA认证', '专业师资', '实践基地'],
      courses: [
        {
          id: 'c1',
          name: '无人机驾驶基础课程',
          duration: '2周',
          price: 3800,
          level: '入门'
        },
        {
          id: 'c2',
          name: '无人机航拍专业课程',
          duration: '4周',
          price: 6800,
          level: '进阶'
        }
      ]
    },
    {
      id: '2',
      name: '智飞无人机培训中心',
      location: '上海市浦东新区',
      phone: '021-87654321',
      rating: 4.8,
      students: 1500,
      description: '专注于工业级无人机操作培训，提供定制化培训方案',
      tags: ['工业应用', '就业保障', '高端设备'],
      courses: [
        {
          id: 'c3',
          name: '工业无人机操作课程',
          duration: '6周',
          price: 8800,
          level: '专业'
        },
        {
          id: 'c4',
          name: '无人机测绘课程',
          duration: '3周',
          price: 5800,
          level: '进阶'
        }
      ]
    }
  ];

  const locations = ['北京', '上海', '广州', '深圳', '杭州'];

  const handleSearch = (value: string) => {
    setSearchKeyword(value);
    // 实现搜索逻辑
  };

  const handleLocationChange = (value: string) => {
    setSelectedLocation(value);
    // 实现位置筛选
  };

  return (
    <Layout>
      <Header style={{ background: '#fff', padding: '0 50px' }}>
        <Title level={2} style={{ margin: '16px 0' }}>无人机培训机构</Title>
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <div style={{ background: '#fff', padding: '24px', minHeight: 280 }}>
          {/* 搜索和筛选区域 */}
          <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
            <Col span={16}>
              <Search
                placeholder="搜索培训机构或课程"
                allowClear
                enterButton="搜索"
                size="large"
                onSearch={handleSearch}
              />
            </Col>
            <Col span={8}>
              <Select
                style={{ width: '100%' }}
                size="large"
                placeholder="选择城市"
                onChange={handleLocationChange}
              >
                {locations.map(location => (
                  <Option key={location} value={location}>{location}</Option>
                ))}
              </Select>
            </Col>
          </Row>

          {/* 培训机构列表 */}
          <Row gutter={[16, 16]}>
            {mockInstitutions.map(institution => (
              <Col span={24} key={institution.id}>
                <Card hoverable>
                  <Row gutter={24}>
                    <Col span={16}>
                      <Space direction="vertical" size="small">
                        <Title level={4} style={{ margin: 0 }}>{institution.name}</Title>
                        <Space size="large">
                          <Space>
                            <EnvironmentOutlined />
                            <Text>{institution.location}</Text>
                          </Space>
                          <Space>
                            <PhoneOutlined />
                            <Text>{institution.phone}</Text>
                          </Space>
                          <Space>
                            <TeamOutlined />
                            <Text>已培训学员：{institution.students}人</Text>
                          </Space>
                        </Space>
                        <Space>
                          <Rate disabled defaultValue={institution.rating} />
                          <Text>{institution.rating}分</Text>
                        </Space>
                        <Paragraph>{institution.description}</Paragraph>
                        <Space size="small">
                          {institution.tags.map(tag => (
                            <Tag key={tag} color="blue">{tag}</Tag>
                          ))}
                        </Space>
                      </Space>
                    </Col>
                    <Col span={8}>
                      <Space direction="vertical" style={{ width: '100%' }}>
                        <Title level={5}>热门课程</Title>
                        {institution.courses.map(course => (
                          <Card key={course.id} size="small">
                            <Space direction="vertical" size="small">
                              <Text strong>{course.name}</Text>
                              <Space size="large">
                                <Space>
                                  <ClockCircleOutlined />
                                  <Text>{course.duration}</Text>
                                </Space>
                                <Tag color="orange">{course.level}</Tag>
                              </Space>
                              <Text type="danger">¥{course.price}</Text>
                            </Space>
                          </Card>
                        ))}
                        <Button type="primary" block>
                          咨询详情
                        </Button>
                      </Space>
                    </Col>
                  </Row>
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      </Content>
    </Layout>
  );
};

export default TrainingPage; 