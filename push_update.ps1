param (
    [string]$Message = "Update Car Service Center Admin POC App"
)

Write-Host "=============================================" -ForegroundColor Cyan
Write-Host " Car Service Center Admin POC - Git Sync Tool" -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan

$gitPath = "C:\Users\Harun\AppData\Local\Programs\Git\cmd\git.exe"
if (-not (Test-Path $gitPath)) {
    $gitPath = "git"
}

Write-Host "[1/3] Staging all modified and new files..." -ForegroundColor Yellow
& $gitPath add .

$status = & $gitPath status --porcelain
if (-not $status) {
    Write-Host "No changes detected. Working tree is clean." -ForegroundColor Green
    exit 0
}

Write-Host "[2/3] Committing updates..." -ForegroundColor Yellow
& $gitPath commit -m "$Message"

# Check if remote exists
$remote = & $gitPath remote -v
if ($remote) {
    Write-Host "[3/3] Pushing updates to GitHub..." -ForegroundColor Yellow
    & $gitPath push origin main
    Write-Host "Successfully pushed updates to GitHub!" -ForegroundColor Green
} else {
    Write-Host "[3/3] Note: Remote GitHub repository not configured yet." -ForegroundColor DarkYellow
    Write-Host "To link your GitHub repository, run:" -ForegroundColor White
    Write-Host '  git remote add origin https://github.com/<your-username>/<your-repo>.git' -ForegroundColor Cyan
    Write-Host '  git push -u origin main' -ForegroundColor Cyan
}

Write-Host "Done! All past versions are safely preserved in Git history." -ForegroundColor Green
