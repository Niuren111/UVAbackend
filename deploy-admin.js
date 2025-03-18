import { execSync } from 'child_process';
import path from 'path';
import fs from 'fs';
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
  // 先进行构建
  console.log('开始构建后台管理项目...');
  execSync('npm run build:admin', { stdio: 'inherit' });
  console.log('构建完成！');

  // 进入构建输出目录
  const distPath = path.resolve(__dirname, 'dist-admin');
  if (!fs.existsSync(distPath)) {
    throw new Error('构建输出目录不存在，请确认构建是否成功。');
  }

  // 如果是第一次部署，需要初始化git
  if (!fs.existsSync(path.join(distPath, '.git'))) {
    console.log('初始化git仓库...');
    execSync('git init', { cwd: distPath, stdio: 'inherit' });
    // 设置远程仓库，请将YOUR_GITHUB_USERNAME替换为实际值
    console.log('使用GitHub用户名：niuren111');
    const username = 'niuren111'; // 请修改为您的GitHub用户名
    const repoName = 'UVAbackend'; // 后台管理专用仓库名
    execSync(`git remote add origin https://github.com/${username}/${repoName}.git`, { cwd: distPath, stdio: 'inherit' });
  }

  // 添加所有文件
  console.log('添加文件到Git...');
  execSync('git add -A', { cwd: distPath, stdio: 'inherit' });

  // 提交更改
  console.log('提交更改...');
  execSync('git commit -m "Deploy admin dashboard to GitHub Pages"', { cwd: distPath, stdio: 'inherit' });

  // 推送到gh-pages分支
  console.log('推送到GitHub...');
  execSync('git push -f origin master:gh-pages', { cwd: distPath, stdio: 'inherit' });

  console.log('部署成功！后台管理页面现在可以通过 https://niuren111.github.io/UVAbackend/ 访问');

} catch (err) {
  handleError(err);
} 