import { execSync } from 'child_process';
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

// 获取当前文件的目录路径
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// 错误处理函数
function handleError(error) {
  console.error('部署出错:', error);
  process.exit(1);
}

try {
  console.log('准备部署修复后的管理后台...');

  // 获取当前工作目录
  const workingDir = process.cwd();
  
  // 创建部署目录
  const distDir = path.join(workingDir, 'dist-fixed');
  if (!fs.existsSync(distDir)) {
    fs.mkdirSync(distDir, { recursive: true });
  }

  // 复制文件到部署目录
  fs.copyFileSync(path.join(workingDir, 'fixed-admin.html'), path.join(distDir, 'index.html'));
  fs.copyFileSync(path.join(workingDir, 'admin-functions.js'), path.join(distDir, 'admin-functions.js'));

  // 切换到部署目录
  process.chdir(distDir);

  // 初始化 Git 仓库（如果是首次部署）
  if (!fs.existsSync(path.join(distDir, '.git'))) {
    execSync('git init');
    execSync('git remote add origin https://github.com/niuren111/UVAbackend.git');
  }

  // 添加所有文件到 Git
  execSync('git add .');

  // 提交更改
  execSync('git commit -m "Deploy fixed admin dashboard to GitHub Pages"');

  // 推送到 gh-pages 分支
  execSync('git push -f origin master:gh-pages');

  console.log('部署成功！修复后的管理后台可在以下地址访问:');
  console.log('https://niuren111.github.io/UVAbackend/');

  // 返回原目录
  process.chdir(workingDir);

} catch (error) {
  handleError(error);
} 