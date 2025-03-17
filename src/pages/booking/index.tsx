import React, { useState } from 'react';
import { Layout, Card, Row, Col, Typography, Space, Button, Form, Input, DatePicker, InputNumber, Steps, message, Result } from 'antd';
import { EnvironmentOutlined, ClockCircleOutlined, UserOutlined, DollarOutlined } from '@ant-design/icons';

const { Header, Content } = Layout;
const { Title, Text } = Typography;
const { Step } = Steps;

interface Flight {
  id: string;
  route: string;
  startLocation: string;
  endLocation: string;
  departureTime: string;
  duration: string;
  price: number;
  capacity: number;
  remainingSeats: number;
}

const BookingPage: React.FC = () => {
  const [currentStep, setCurrentStep] = useState(0);
  const [selectedFlight, setSelectedFlight] = useState<Flight | null>(null);
  const [form] = Form.useForm();

  // 模拟的航线数据
  const mockFlights: Flight[] = [
    {
      id: '1',
      route: '北京城区观光航线',
      startLocation: '北京市海淀区起飞点',
      endLocation: '北京市海淀区降落点',
      departureTime: '2024-03-20 10:00',
      duration: '30分钟',
      price: 599,
      capacity: 4,
      remainingSeats: 2
    },
    {
      id: '2',
      route: '长城航空观光',
      startLocation: '北京市延庆区起飞点',
      endLocation: '北京市延庆区降落点',
      departureTime: '2024-03-20 14:00',
      duration: '45分钟',
      price: 899,
      capacity: 4,
      remainingSeats: 4
    }
  ];

  const handleFlightSelect = (flight: Flight) => {
    setSelectedFlight(flight);
    setCurrentStep(1);
  };

  const handleSubmit = (values: any) => {
    console.log('预订信息:', values);
    message.success('预订成功！');
    setCurrentStep(2);
  };

  const steps = [
    {
      title: '选择航线',
      content: (
        <Row gutter={[16, 16]}>
          {mockFlights.map(flight => (
            <Col span={24} key={flight.id}>
              <Card hoverable>
                <Row justify="space-between" align="middle">
                  <Col span={16}>
                    <Space direction="vertical" size="small">
                      <Title level={4} style={{ margin: 0 }}>{flight.route}</Title>
                      <Space size="large">
                        <Space>
                          <EnvironmentOutlined />
                          <Text>起点：{flight.startLocation}</Text>
                        </Space>
                        <Space>
                          <EnvironmentOutlined />
                          <Text>终点：{flight.endLocation}</Text>
                        </Space>
                      </Space>
                      <Space size="large">
                        <Space>
                          <ClockCircleOutlined />
                          <Text>起飞时间：{flight.departureTime}</Text>
                        </Space>
                        <Text>飞行时长：{flight.duration}</Text>
                      </Space>
                      <Space size="large">
                        <Space>
                          <UserOutlined />
                          <Text>剩余座位：{flight.remainingSeats}/{flight.capacity}</Text>
                        </Space>
                        <Space>
                          <DollarOutlined />
                          <Text type="danger">¥{flight.price}/人</Text>
                        </Space>
                      </Space>
                    </Space>
                  </Col>
                  <Col span={8} style={{ textAlign: 'right' }}>
                    <Button
                      type="primary"
                      size="large"
                      onClick={() => handleFlightSelect(flight)}
                      disabled={flight.remainingSeats === 0}
                    >
                      {flight.remainingSeats === 0 ? '已满座' : '立即预订'}
                    </Button>
                  </Col>
                </Row>
              </Card>
            </Col>
          ))}
        </Row>
      )
    },
    {
      title: '填写信息',
      content: (
        <Card>
          <Form
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
          >
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item
                  name="name"
                  label="乘客姓名"
                  rules={[{ required: true, message: '请输入乘客姓名' }]}
                >
                  <Input placeholder="请输入乘客姓名" />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item
                  name="phone"
                  label="联系电话"
                  rules={[{ required: true, message: '请输入联系电话' }]}
                >
                  <Input placeholder="请输入联系电话" />
                </Form.Item>
              </Col>
            </Row>
            <Row gutter={16}>
              <Col span={12}>
                <Form.Item
                  name="idCard"
                  label="身份证号"
                  rules={[{ required: true, message: '请输入身份证号' }]}
                >
                  <Input placeholder="请输入身份证号" />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item
                  name="passengers"
                  label="预订人数"
                  rules={[{ required: true, message: '请选择预订人数' }]}
                >
                  <InputNumber
                    min={1}
                    max={selectedFlight?.remainingSeats || 1}
                    style={{ width: '100%' }}
                  />
                </Form.Item>
              </Col>
            </Row>
            <Form.Item>
              <Space>
                <Button onClick={() => setCurrentStep(0)}>上一步</Button>
                <Button type="primary" htmlType="submit">
                  提交预订
                </Button>
              </Space>
            </Form.Item>
          </Form>
        </Card>
      )
    },
    {
      title: '预订成功',
      content: (
        <Card>
          <Result
            status="success"
            title="预订成功！"
            subTitle="您的航线已预订成功，请按时到达指定地点。"
            extra={[
              <Button type="primary" key="console" onClick={() => setCurrentStep(0)}>
                继续预订
              </Button>,
              <Button key="orders">查看订单</Button>,
            ]}
          />
        </Card>
      )
    }
  ];

  return (
    <Layout>
      <Header style={{ background: '#fff', padding: '0 50px' }}>
        <Title level={2} style={{ margin: '16px 0' }}>低空载客预订</Title>
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <div style={{ background: '#fff', padding: '24px', minHeight: 280 }}>
          <Steps current={currentStep} style={{ marginBottom: 24 }}>
            {steps.map(item => (
              <Step key={item.title} title={item.title} />
            ))}
          </Steps>
          <div>{steps[currentStep].content}</div>
        </div>
      </Content>
    </Layout>
  );
};

export default BookingPage; 