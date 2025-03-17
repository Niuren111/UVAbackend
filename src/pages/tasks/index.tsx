import React, { useState } from 'react';
import { Layout, Button, Card, Row, Col, Typography, Space, Tag, Modal, Form, Input, InputNumber, DatePicker, Select } from 'antd';
import { PlusOutlined, EnvironmentOutlined, ClockCircleOutlined, DollarOutlined } from '@ant-design/icons';

const { Header, Content } = Layout;
const { Title, Text } = Typography;
const { TextArea } = Input;
const { Option } = Select;

interface Task {
  id: string;
  title: string;
  description: string;
  startLocation: string;
  endLocation: string;
  deadline: string;
  budget: number;
  weight: number;
  status: string;
  publisher: string;
  publishTime: string;
}

const TasksPage: React.FC = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [form] = Form.useForm();

  // 模拟的任务数据
  const mockTasks: Task[] = [
    {
      id: '1',
      title: '农产品运输任务',
      description: '需要将20箱新鲜水果从郊区农场运送到市区超市',
      startLocation: '北京郊区农场',
      endLocation: '北京市朝阳区超市',
      deadline: '2024-03-20',
      budget: 2000,
      weight: 100,
      status: '待接单',
      publisher: '绿色农场',
      publishTime: '2024-03-16'
    },
    {
      id: '2',
      title: '快递包裹配送',
      description: '城市内多个快递包裹配送任务',
      startLocation: '北京市海淀区物流中心',
      endLocation: '北京市海淀区各社区',
      deadline: '2024-03-18',
      budget: 1500,
      weight: 50,
      status: '待接单',
      publisher: '快递物流公司',
      publishTime: '2024-03-16'
    }
  ];

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
  };

  const handleSubmit = (values: any) => {
    console.log('提交的任务信息:', values);
    setIsModalVisible(false);
    form.resetFields();
  };

  return (
    <Layout>
      <Header style={{ background: '#fff', padding: '0 50px' }}>
        <Row justify="space-between" align="middle" style={{ height: '100%' }}>
          <Title level={2} style={{ margin: '16px 0' }}>无人机运输任务</Title>
          <Button type="primary" icon={<PlusOutlined />} size="large" onClick={showModal}>
            发布任务
          </Button>
        </Row>
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <div style={{ background: '#fff', padding: '24px', minHeight: 280 }}>
          <Row gutter={[0, 16]}>
            {mockTasks.map(task => (
              <Col span={24} key={task.id}>
                <Card hoverable>
                  <Row justify="space-between">
                    <Col span={16}>
                      <Space direction="vertical" size="small">
                        <Title level={4} style={{ margin: 0 }}>{task.title}</Title>
                        <Text>{task.description}</Text>
                        <Space size="large">
                          <Space>
                            <EnvironmentOutlined />
                            <Text>起点：{task.startLocation}</Text>
                          </Space>
                          <Space>
                            <EnvironmentOutlined />
                            <Text>终点：{task.endLocation}</Text>
                          </Space>
                        </Space>
                        <Space size="large">
                          <Space>
                            <ClockCircleOutlined />
                            <Text>截止日期：{task.deadline}</Text>
                          </Space>
                          <Space>
                            <DollarOutlined />
                            <Text>预算：¥{task.budget}</Text>
                          </Space>
                        </Space>
                        <Space size="large">
                          <Text type="secondary">重量：{task.weight}kg</Text>
                          <Tag color="blue">{task.status}</Tag>
                        </Space>
                        <Text type="secondary">发布者：{task.publisher} | 发布时间：{task.publishTime}</Text>
                      </Space>
                    </Col>
                    <Col span={8} style={{ textAlign: 'right' }}>
                      <Button type="primary" size="large">
                        投标接单
                      </Button>
                    </Col>
                  </Row>
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      </Content>

      <Modal
        title="发布运输任务"
        open={isModalVisible}
        onCancel={handleCancel}
        footer={null}
        width={800}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
        >
          <Form.Item
            name="title"
            label="任务标题"
            rules={[{ required: true, message: '请输入任务标题' }]}
          >
            <Input placeholder="请输入任务标题" />
          </Form.Item>

          <Form.Item
            name="description"
            label="任务描述"
            rules={[{ required: true, message: '请输入任务描述' }]}
          >
            <TextArea rows={4} placeholder="请详细描述运输任务的要求" />
          </Form.Item>

          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="startLocation"
                label="起点位置"
                rules={[{ required: true, message: '请输入起点位置' }]}
              >
                <Input placeholder="请输入起点位置" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="endLocation"
                label="终点位置"
                rules={[{ required: true, message: '请输入终点位置' }]}
              >
                <Input placeholder="请输入终点位置" />
              </Form.Item>
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="weight"
                label="货物重量(kg)"
                rules={[{ required: true, message: '请输入货物重量' }]}
              >
                <InputNumber style={{ width: '100%' }} min={0} placeholder="请输入货物重量" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="budget"
                label="预算金额(元)"
                rules={[{ required: true, message: '请输入预算金额' }]}
              >
                <InputNumber style={{ width: '100%' }} min={0} placeholder="请输入预算金额" />
              </Form.Item>
            </Col>
          </Row>

          <Form.Item
            name="deadline"
            label="截止日期"
            rules={[{ required: true, message: '请选择截止日期' }]}
          >
            <DatePicker style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item>
            <Row justify="end" gutter={16}>
              <Col>
                <Button onClick={handleCancel}>取消</Button>
              </Col>
              <Col>
                <Button type="primary" htmlType="submit">
                  发布任务
                </Button>
              </Col>
            </Row>
          </Form.Item>
        </Form>
      </Modal>
    </Layout>
  );
};

export default TasksPage; 