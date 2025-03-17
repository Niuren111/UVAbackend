import React from 'react'
import { Routes, Route } from 'react-router-dom'
import HomePage from './pages/home'
import JobsPage from './pages/jobs'
import TasksPage from './pages/tasks'
import TrainingPage from './pages/training'
import BookingPage from './pages/booking'
import MarketplacePage from './pages/marketplace'
import UserPage from './pages/user'

const App: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/jobs" element={<JobsPage />} />
      <Route path="/tasks" element={<TasksPage />} />
      <Route path="/training" element={<TrainingPage />} />
      <Route path="/booking" element={<BookingPage />} />
      <Route path="/marketplace" element={<MarketplacePage />} />
      <Route path="/user" element={<UserPage />} />
    </Routes>
  )
}

export default App 