# echo current system info
function sysinfo() {
  echo -e "\n当前系统信息："
  echo -e "操作系统：\t$(uname -s)"
  echo -e "内核版本：\t$(uname -r)"
  echo -e "处理器：\t$(uname -p)"
  echo -e "内存：\t$(free -g | awk '{print $2}') GB"
  echo -e "硬盘：\t$(df -h / | awk '{print $2}') GB"
  echo -e "时间：\t$(date +"%Y-%m-%d %H:%M:%S")"
}

#Chinese Terminal
export LANG=en_US.UTF-8

# mac
function gitpullall() {
  original_dir=$(pwd)
  subdirs=$(find . -mindepth 1 -maxdepth 1 -type d -print0)

  while IFS= read -r -d '' dir; do
      cd "$dir" || continue # skip if fail to dir
      if [ -d ".git" ]; then # check if it is git dir
          echo "Pulling changes in $(pwd)"
          git pull
      else
          echo "Skipping $(pwd): Not a Git repository"
      fi
      cd "$original_dir" || return
  done <<< "$subdirs"
  cd "$original_dir" || return
}

# windows git bash
function gitpullall_gitbash() {
    original_dir=$(pwd)
    subdirs=$(ls -d */ 2>/dev/null)

    for dir in $subdirs; do
        cd "$dir" || continue
        if [ -d ".git" ]; then
            echo "Pulling changes in $(pwd)"
            git pull
        else
            echo "Skipping $(pwd): Not a Git repository"
        fi
        cd "$original_dir" || return
    done
    cd "$original_dir" || return
}
