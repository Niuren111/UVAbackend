import { execSync } from 'child_process';
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

// 获取当前文件的目录路径
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// 确保脚本抛出遇到的错误
function handleError(err) {
  console.error('部署过程发生错误:', err);
  process.exit(1);
}

// 开始部署
try {
  console.log('开始准备简单管理页面部署...');
  
  // 创建临时目录
  const distPath = path.resolve(__dirname, 'dist-simple');
  if (!fs.existsSync(distPath)) {
    fs.mkdirSync(distPath);
  }
  
  // 拷贝simple-admin.html到index.html
  console.log('复制文件...');
  fs.copyFileSync(
    path.resolve(__dirname, 'simple-admin.html'),
    path.resolve(distPath, 'index.html')
  );
  
  // 如果是第一次部署，需要初始化git
  if (!fs.existsSync(path.join(distPath, '.git'))) {
    console.log('初始化git仓库...');
    execSync('git init', { cwd: distPath, stdio: 'inherit' });
    // 设置远程仓库
    console.log('使用GitHub用户名：niuren111');
    const username = 'niuren111';
    const repoName = 'UVAbackend';
    execSync(`git remote add origin https://github.com/${username}/${repoName}.git`, { cwd: distPath, stdio: 'inherit' });
  }

  // 添加所有文件
  console.log('添加文件到Git...');
  execSync('git add -A', { cwd: distPath, stdio: 'inherit' });

  // 提交更改
  console.log('提交更改...');
  execSync('git commit -m "Deploy simple admin dashboard to GitHub Pages"', { cwd: distPath, stdio: 'inherit' });

  // 推送到gh-pages分支
  console.log('推送到GitHub...');
  execSync('git push -f origin master:gh-pages', { cwd: distPath, stdio: 'inherit' });

  console.log('部署成功！简单管理页面现在可以通过 https://niuren111.github.io/UVAbackend/ 访问');

} catch (err) {
  handleError(err);
} 