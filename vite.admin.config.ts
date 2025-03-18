import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'
import { fileURLToPath } from 'url'

// 获取当前文件的目录路径
const __filename = fileURLToPath(import.meta.url);
const __dirname = resolve(fileURLToPath(new URL('.', import.meta.url)));

export default defineConfig({
  plugins: [react()],
  base: '/UVAbackend/',  // 更改为后台管理专用的base路径
  build: {
    outDir: 'dist-admin', // 更改为不同的输出目录
    assetsDir: 'assets',
    sourcemap: false,
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'admin.html') // 使用管理后台专用的入口HTML文件
      }
    }
  },
  server: {
    port: 3001, // 更改端口避免冲突
    open: true
  },
  resolve: {
    alias: {
      '@': '/src'
    }
  }
}) 