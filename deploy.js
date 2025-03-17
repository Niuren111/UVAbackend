const ghpages = require('gh-pages');
const path = require('path');

// 创建一个简单的部署函数
function deploy() {
  // 创建一个临时目录
  const tempDir = path.join(__dirname, 'temp');
  
  // 复制 index.html 到临时目录
  require('fs').mkdirSync(tempDir, { recursive: true });
  require('fs').copyFileSync(
    path.join(__dirname, 'index.html'),
    path.join(tempDir, 'index.html')
  );
  
  // 部署到 GitHub Pages
  ghpages.publish(tempDir, {
    branch: 'gh-pages',
    repo: 'https://github.com/Niuren111/UAV.git',
    message: 'Auto-generated commit'
  }, function(err) {
    if (err) {
      console.error('部署失败:', err);
    } else {
      console.log('部署成功!');
    }
    
    // 清理临时目录
    require('fs').rmSync(tempDir, { recursive: true, force: true });
  });
}

// 执行部署
deploy(); 