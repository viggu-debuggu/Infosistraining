# SkillSphere Nexus - Automated Complete Launch Script
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host " Starting SkillSphere Nexus Full Stack Suite " -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan

$root = $PSScriptRoot
if (-not $root) { $root = Get-Location }

# 1. Start Backend (Local Profile - H2 In-Memory DB)
$env:SPRING_PROFILES_ACTIVE = "local"
Write-Host "[1/3] Launching Spring Boot Backend on http://localhost:8080..." -ForegroundColor Green
Start-Process -FilePath "cmd.exe" -ArgumentList "/c cd /d `"$root\backend`" && .\mvnw.cmd spring-boot:run" -WindowStyle Minimized

# 2. Start Frontend (Angular Dev Server)
Write-Host "[2/3] Launching Angular Frontend on http://localhost:4200..." -ForegroundColor Green
Start-Process -FilePath "cmd.exe" -ArgumentList "/c cd /d `"$root\frontend`" && npm start -- --host 0.0.0.0 --port 4200" -WindowStyle Minimized

# 3. Open Chrome
Write-Host "[3/3] Opening Google Chrome to http://localhost:4200..." -ForegroundColor Green
Start-Sleep -Seconds 3
Start-Process "http://localhost:4200"

Write-Host "SkillSphere Nexus is now running!" -ForegroundColor Cyan
Write-Host "Default Admin Credentials:" -ForegroundColor Yellow
Write-Host "  Email:    admin@skillsphere.com" -ForegroundColor Yellow
Write-Host "  Password: admin123456789" -ForegroundColor Yellow
