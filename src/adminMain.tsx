import React from 'react'
import ReactDOM from 'react-dom/client'
import { ConfigProvider } from 'antd'
import zhCN from 'antd/lib/locale/zh_CN'
import AdminPage from './pages/admin'
import './index.css'

// 直接渲染管理页面，不使用路由
ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ConfigProvider locale={zhCN}>
      <AdminPage />
    </ConfigProvider>
  </React.StrictMode>,
) 