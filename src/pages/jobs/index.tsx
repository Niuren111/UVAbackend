import React, { useState } from 'react';
import { Layout, Input, Select, Card, Tag, Button, Row, Col, Typography, Space } from 'antd';
import { SearchOutlined, EnvironmentOutlined, MoneyCollectOutlined } from '@ant-design/icons';

const { Header, Content } = Layout;
const { Search } = Input;
const { Option } = Select;
const { Title, Text } = Typography;

interface JobPost {
  id: string;
  title: string;
  company: string;
  location: string;
  salary: string;
  experience: string;
  education: string;
  tags: string[];
  postDate: string;
}

const JobsPage: React.FC = () => {
  const [searchKeyword, setSearchKeyword] = useState('');
  const [selectedLocation, setSelectedLocation] = useState<string>('');
  const [selectedJobType, setSelectedJobType] = useState<string>('');

  // 模拟的职位数据
  const mockJobs: JobPost[] = [
    {
      id: '1',
      title: '无人机飞手',
      company: '智航科技有限公司',
      location: '北京',
      salary: '8k-15k',
      experience: '1-3年',
      education: '大专及以上',
      tags: ['无人机驾驶', '航拍', '实时操控'],
      postDate: '2024-03-16'
    },
    {
      id: '2',
      title: '无人机维修工程师',
      company: '天翼航空科技',
      location: '上海',
      salary: '10k-20k',
      experience: '3-5年',
      education: '本科及以上',
      tags: ['无人机维修', '故障诊断', '技术支持'],
      postDate: '2024-03-15'
    },
    // 更多职位数据...
  ];

  const locations = ['北京', '上海', '广州', '深圳', '杭州'];
  const jobTypes = ['全职', '兼职', '实习'];

  const handleSearch = (value: string) => {
    setSearchKeyword(value);
    // 实现搜索逻辑
  };

  const handleLocationChange = (value: string) => {
    setSelectedLocation(value);
    // 实现位置筛选
  };

  const handleJobTypeChange = (value: string) => {
    setSelectedJobType(value);
    // 实现工作类型筛选
  };

  return (
    <Layout>
      <Header style={{ background: '#fff', padding: '0 50px' }}>
        <Title level={2} style={{ margin: '16px 0' }}>无人机行业招聘</Title>
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <div style={{ background: '#fff', padding: '24px', minHeight: 280 }}>
          {/* 搜索和筛选区域 */}
          <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
            <Col span={12}>
              <Search
                placeholder="搜索职位名称、公司名称"
                allowClear
                enterButton={<SearchOutlined />}
                size="large"
                onSearch={handleSearch}
              />
            </Col>
            <Col span={6}>
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
            <Col span={6}>
              <Select
                style={{ width: '100%' }}
                size="large"
                placeholder="工作类型"
                onChange={handleJobTypeChange}
              >
                {jobTypes.map(type => (
                  <Option key={type} value={type}>{type}</Option>
                ))}
              </Select>
            </Col>
          </Row>

          {/* 职位列表 */}
          <Row gutter={[0, 16]}>
            {mockJobs.map(job => (
              <Col span={24} key={job.id}>
                <Card hoverable>
                  <Row justify="space-between" align="middle">
                    <Col span={16}>
                      <Space direction="vertical" size="small">
                        <Title level={4} style={{ margin: 0 }}>{job.title}</Title>
                        <Space size="large">
                          <Text>{job.company}</Text>
                          <Space>
                            <EnvironmentOutlined />
                            <Text>{job.location}</Text>
                          </Space>
                          <Space>
                            <MoneyCollectOutlined />
                            <Text>{job.salary}</Text>
                          </Space>
                        </Space>
                        <Space size="small">
                          {job.tags.map(tag => (
                            <Tag key={tag} color="blue">{tag}</Tag>
                          ))}
                        </Space>
                        <Space size="large">
                          <Text type="secondary">经验要求：{job.experience}</Text>
                          <Text type="secondary">学历要求：{job.education}</Text>
                          <Text type="secondary">发布时间：{job.postDate}</Text>
                        </Space>
                      </Space>
                    </Col>
                    <Col span={8} style={{ textAlign: 'right' }}>
                      <Button type="primary" size="large">
                        立即投递
                      </Button>
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

export default JobsPage; 