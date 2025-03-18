import React from 'react'
import { Routes, Route } from 'react-router-dom'
import HomePage from './pages/home'
import JobsPage from './pages/jobs'
import TasksPage from './pages/tasks'
import TrainingPage from './pages/training'
import BookingPage from './pages/booking'
import MarketplacePage from './pages/marketplace'
import UserPage from './pages/user'
import AdminPage from './pages/admin'

const App: React.FC = () => {
  // 检测当前环境
  const isAdminEnvironment = window.location.hostname.includes('UVAbackend') || 
                            window.location.pathname.includes('UVAbackend');
  
  // 根据环境选择基础路径
  const basePath = isAdminEnvironment ? '/UVAbackend' : '/UAV';
  
  return (
    <Routes>
      {isAdminEnvironment ? (
        // 管理后台路由
        <>
          <Route path={`${basePath}/`} element={<AdminPage />} />
          <Route path="*" element={<AdminPage />} />
        </>
      ) : (
        // 前台路由
        <>
          <Route path={`${basePath}/`} element={<HomePage />} />
          <Route path={`${basePath}/jobs`} element={<JobsPage />} />
          <Route path={`${basePath}/tasks`} element={<TasksPage />} />
          <Route path={`${basePath}/training`} element={<TrainingPage />} />
          <Route path={`${basePath}/booking`} element={<BookingPage />} />
          <Route path={`${basePath}/marketplace`} element={<MarketplacePage />} />
          <Route path={`${basePath}/user`} element={<UserPage />} />
          <Route path={`${basePath}/admin`} element={<AdminPage />} />
          <Route path="*" element={<HomePage />} />
        </>
      )}
    </Routes>
  )
}

export default App 